package com.green.jejuplus.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.green.jejuplus.dto.payment.PaymentDTO;
import com.green.jejuplus.dto.user.KakaoProfile;
import com.green.jejuplus.dto.user.OAuthToken;
import com.green.jejuplus.dto.user.SignInFormDto;
import com.green.jejuplus.dto.user.SignUpFormDto;
import com.green.jejuplus.dto.user.UserDeleteDto;
import com.green.jejuplus.dto.user.UserUpdateDto;
import com.green.jejuplus.handler.exception.CustomException;
import com.green.jejuplus.repository.model.Air;
import com.green.jejuplus.repository.model.Promotion;
import com.green.jejuplus.repository.model.PromotionImg;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.service.air.AirService;
import com.green.jejuplus.service.payment.PaymentService;
import com.green.jejuplus.service.user.EmailService;
import com.green.jejuplus.service.user.UserService;
import com.green.jejuplus.util.Define;
import com.green.jejuplus.util.RandomCodeGenerator;
import com.green.jejuplus.util.RandomPasswordGenerator;

@Controller
@RequestMapping("/user")
public class UserController {

	@Value("{tenco.key}")
	private String tencoKey;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired // DI 처리
	private HttpSession session;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	// 사용자 등록 폼을 표시하는 메서드
	@GetMapping("/register")
	public String showRegistrationForm() {
		return "user/register";
	}

	// 사용자 등록 요청을 처리하는 메서드
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("fullname") String fullname, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("firstUsername") String firstUsername, @RequestParam("firstPassword") String firstPassword,
			@RequestParam("code") String code, HttpSession session, SignUpFormDto signUpFormDto) {
		String savedCode = (String) session.getAttribute("randomCode");

		// 1. 유효성 검사
		if (signUpFormDto.getUsername() == null || signUpFormDto.getUsername().isEmpty()) {
			throw new CustomException("아이디를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if (signUpFormDto.getPassword() == null || signUpFormDto.getPassword().isEmpty()) {
			throw new CustomException("비밀번호를 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if (signUpFormDto.getEmail() == null || signUpFormDto.getEmail().isEmpty()) {
			throw new CustomException("email을 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if (signUpFormDto.getFullname() == null || signUpFormDto.getFullname().isEmpty()) {
			throw new CustomException("이름을 입력하세요.", HttpStatus.BAD_REQUEST);
		}
		if (signUpFormDto.getPhoneNumber() == null || signUpFormDto.getPhoneNumber().isEmpty()) {
			throw new CustomException("전화번호를 입력해주세요.", HttpStatus.BAD_REQUEST);
		}

		System.out.println("firstUsername : " + firstUsername);
		System.out.println("username : " + username);
		System.out.println("firstPassword : " + firstPassword);
		System.out.println("password : " + password);
		// 중복체크후 검사 !(a.equals(b));
		if (!(firstUsername.equals(username))) {

			throw new CustomException("입력하신 정보가 변경되어있습니다.", HttpStatus.BAD_REQUEST);
		}
		if (!(firstPassword.equals(password))) {
			throw new CustomException("입력하신 비밀번호가 변경되어있습니다.", HttpStatus.BAD_REQUEST);
		}

		// 아이디 중복 확인
		if (!userService.isUsernameUnique(username)) {
			System.out.println("어디까지오냐1");
			return ResponseEntity.badRequest().body("아이디가 이미 사용 중 이거나 한글로 작성되었습니다.");
		}

		// 이메일 중복 확인
		if (!userService.isEmailUnique(email)) {
			System.out.println("어디까지오냐2");
			return ResponseEntity.badRequest().body("이메일이 이미 사용 중입니다.");

		}

		int maxUsernameLength = 15;
		int minUsernameLength = 6;
		int maxPasswordLength = 20;
		int minPasswordLength = 8;

		if (username.length() > maxUsernameLength || username.length() < minUsernameLength
				|| password.length() > maxPasswordLength || password.length() < minPasswordLength) {
			System.out.println("어디까지오냐3");
			return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
		}

		if (savedCode != null && savedCode.equals(code)) {
			// 인증 코드가 일치하는 경우, 사용자 정보를 데이터베이스에 저장
			signUpFormDto.setEmail(email);
			signUpFormDto.setUsername(username);
			signUpFormDto.setPassword(password);
			signUpFormDto.setFullname(fullname);
			signUpFormDto.setPhoneNumber(phoneNumber);
			userService.registerUser(signUpFormDto);
			System.out.println("어디까지오냐4");
			return ResponseEntity.ok("Registration successful");
		} else {
			return ResponseEntity.badRequest().body("Verification failed");
		}
	}

	// 인증 코드를 전송하는 메서드
	@PostMapping("/send-verification-code")
	public ResponseEntity<String> sendVerificationCode(@RequestParam("email") String email, HttpSession session) {

		System.out.println("인증코드 컨트롤러 이메일 확인 : " + email);
		String randomCode = RandomCodeGenerator.generateRandomCode();
		session.setAttribute("randomCode", randomCode);

		// 이메일로 코드 전송
		emailService.sendVerificationCode(email, randomCode);

		return ResponseEntity.ok("Verification code sent");
	}

	// 인증 코드를 확인하는 메서드
	@PostMapping("/verify-code")
	public ResponseEntity<String> verifyCode(@RequestParam("code") String code, HttpSession session) {
		String savedCode = (String) session.getAttribute("randomCode");

		if (savedCode != null && savedCode.equals(code)) {
			// 인증 코드가 일치하는 경우
			return ResponseEntity.ok("Verification successful");
		} else {
			return ResponseEntity.ok("Verification failed");
		}
	}

	// 아이디
	@GetMapping("/check-username")
	public ResponseEntity<String> checkUsername(@RequestParam("username") String username) {
		if (userService.isUsernameUnique(username)) {
			return ResponseEntity.ok("Username available");
		} else {
			return ResponseEntity.ok("이미 사용중인 아이디입니다.");
		}
	}

	// 이메일
	@GetMapping("/check-email")
	public ResponseEntity<String> checkEmail(@RequestParam("email") String email) {
		System.out.println("컨트롤러에 이메일 제대로옴? :" + email);
		if (userService.isEmailUnique(email)) {
			return new ResponseEntity<>("success", HttpStatus.OK);
		} else {
			System.out.println("여기로 오는거 맞지?");
			return new ResponseEntity<>("duplicate", HttpStatus.OK);
		}
	}

	// 비밀번호
	@GetMapping("/check-password")
	public ResponseEntity<String> checkPassword(@RequestParam("password") String password) {
		int maxPasswordLength = 20;
		int minPasswordLength = 8;

		if (password.length() > maxPasswordLength || password.length() < minPasswordLength) {
			return new ResponseEntity<>("duplicate", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok("Password valid");
	}

	@PostMapping("/check-update-password/{userId}")
	@ResponseBody
	public ResponseEntity<Map<String, String>> checkAndUpdatePassword(@RequestBody Map<String, String> request,
			@PathVariable("userId") int userId) {
		User user = userService.getUserPassword(userId);

		if (user != null) {
			String currentPassword = request.get("currentPassword");
			String newPassword = request.get("newPassword");

			if (passwordEncoder.matches(currentPassword, user.getPassword())) {
				// 현재 비밀번호가 일치할 경우 비밀번호를 업데이트
				user.setPassword(newPassword);
				userService.updateUserPassword(user); // 변경된 정보를 저장
				Map<String, String> response = new HashMap<>();
				response.put("status", "PasswordUpdated");
				System.out.println("컨트롤러 리스폰: " + response);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				Map<String, String> response = new HashMap<>();
				response.put("status", "PasswordMismatch");
				System.out.println("컨트롤러 비번틀릴때 리스폰: " + response);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		} else {
			Map<String, String> response = new HashMap<>();
			response.put("status", "UserNotFound");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	// 로그인 페이지 요청
	// http://localhost:80/user/sign-in
	@GetMapping({ "/sign-in", "" })
	public String signIn() {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		if (user != null) {
			throw new CustomException("이미 로그인 중 입니다.", HttpStatus.BAD_REQUEST);
		}
		return "user/signIn";
	}

	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto signInFormDto) {

		// 1. 유효성 검사
		if (signInFormDto.getUsername() == null || signInFormDto.getUsername().isEmpty()) {
			throw new CustomException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if (signInFormDto.getPassword() == null || signInFormDto.getPassword().isEmpty()) {
			throw new CustomException("password를 입력하시오", HttpStatus.BAD_REQUEST);
		}

		// 2. 서비스 -> 인증된 사용자 여부 확인
		// principal <-- 접근 주체
		User principal = userService.signIn(signInFormDto);
		principal.setPassword(null);
		// 3. 쿠키 + 세션
		session.setAttribute(Define.PRINCIPAL, principal);

		return "redirect:/main";
	}

	/**
	 * 로그아웃 처리
	 * 
	 * @return 리다이렉트 - 로그인 페이지 이동
	 */
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/user/sign-in";
	}

	// http://localhost:80/user/kakao/callback?code="ZESasa"
	@GetMapping("kakao/callback")
	// @ResponseBody // 이녀석은 data 반환 명시
	public String kakaoCallback(@RequestParam String code) { // 토큰이 여기 code로 닮김
		System.out.println("메서드 동작");

		// POST 방식 - exchange() 메서드 활용
		// Header 헤더 구성
		// body 구성

		RestTemplate rt = new RestTemplate();
		// 헤더 구성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8"); // 문서 토큰란에헤더 형식 키,value 값 가져와서
																						// 복붙

		// body 구성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "1c9a0248b81dbbc743e8918bc64a86e5"); // value 값에 아까 발급받아서 복사해둔 RESTAPI 넣으란거임
		params.add("redirect_uri", "http://localhost:80/user/kakao/callback"); // value 값에 아까 발급받아서 복사해둔 RESTURI 넣으란거임
		params.add("code", code);

		// HttpEntity 결합 (헤더 + 바디)
		HttpEntity<MultiValueMap<String, String>> reqMes = new HttpEntity<>(params, headers);

		// Http 요청
		ResponseEntity<OAuthToken> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				reqMes, OAuthToken.class); // 토큰 받기에 있던 적혀있던 복사한 URL 넣어주는 곳, POST로 받겠다고 되있었음

		// 1. DTO 파싱
		System.out.println("액세트 토근 확인 " + response.getBody().toString());
		// 액세스 토큰
		// 엑세스 토근 --> 카카오 서버 (정보)

		// 문서 확인 - 정보 요청 주소
		System.out.println("--------------------------------");
		RestTemplate ret2 = new RestTemplate();

		// 헤더 생성
		HttpHeaders headers2 = new HttpHeaders(); // response.getBody().getAccessToken() --> 액세스 토큰
		headers2.add("Authorization", "Bearer " + response.getBody().getAccessToken()); // Bearer 뒤에 공백 한칸도 같이 복사해옴
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// 바디 생성 - 생략 , GET방식은 바디 없어도 됨
		HttpEntity<MultiValueMap<String, String>> kokaoInfo = new HttpEntity<>(headers2); // 바디 없이 헤더만 추가

		// HTTP 요청
		ResponseEntity<KakaoProfile> response2 = ret2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kokaoInfo, KakaoProfile.class);
		System.out.println("----------------------");
		System.out.println(response2.getBody().getKakaoAccount().getEmail());

		System.out.println("------------카카오 서버에 정보 받기 완료---------------");

		// 1. 회원 가입 여부 확인 - 최초 사용자 라면
		// - 최초 사용자 라면 우리 회원 가입 맞는 형식을 만들어서 회원 가입 처리
		// DB -> user_tb --> username, password, fullname
		// password <-- 직접 만들어서 넣어야 합니다.
		// 소셜 로그인 사용자는 모든 패스워드가 동일 합니다.

		// username -> 동일한 값이 저장되지 않도록 처리

		KakaoProfile kakaoProfile = response2.getBody();

		SignUpFormDto signUpFormDto = SignUpFormDto.builder()
				.username(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
				.fullname(kakaoProfile.getProperties().getNickname()).password(tencoKey)
				.email(kakaoProfile.getKakaoAccount().getEmail()).build();

		User olderUser = userService.searchUsername(signUpFormDto.getUsername());

		if (olderUser == null) {
			// 사용자가 최초 소셜 로그인 사용자면 자동 회원 가입처리
			User newUser = new User();
			userService.registerUserKakao(signUpFormDto);
			newUser.setUsername(signUpFormDto.getUsername());
			newUser.setFullname(signUpFormDto.getFullname());
			session.setAttribute(Define.PRINCIPAL, newUser);
			System.out.println("signUpFormDto : " + signUpFormDto);
			return "redirect:/main";

		}
		olderUser.setPassword(null);
		// null 아닌 경우 처리
		// 그게 아니라면 바로 세션에 데이터 등록 로그인 처리
		session.setAttribute(Define.PRINCIPAL, olderUser); // 로그인 처리
		
		System.out.println("signUpFormDto : " + signUpFormDto);
		
		return "redirect:/main";

	}

	// 아이디찾기
	@GetMapping("/find-username")
	public String findUsername() {
		return "/user/findUsername";
	}

	@PostMapping("/find-username")
	@ResponseBody
	public ResponseEntity<String> findUsername(@RequestBody String email) {
	    // 이메일 주소를 사용하여 사용자를 검색하고 아이디를 가져옵니다.
		System.out.println("asdf");
		System.out.println("Email before: " + email);
		User user = userService.findUserByEmail(email);
		System.out.println("Email after: " + email);


	    if (user != null) {
	        // 사용자를 찾았을 경우 아이디를 반환합니다.
	    	System.out.println("컨트롤러 유저 :" + user);
	        String username = user.getUsername();
	        emailService.sendUsername(email, username);
	        return new ResponseEntity<>(username, HttpStatus.OK);
	    } else {
	        // 사용자를 찾지 못한 경우 오류 메시지를 반환합니다.
	        return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
	    }
	}


	// 비밀번호 찾기
	@GetMapping("/find-password")
	public String userpassword() {
		return "/user/findPassword";
	}

	@PostMapping("/check-id-email")
	@ResponseBody
	public ResponseEntity<String> checkIdAndEmail(@RequestBody Map<String, String> data) {
	    String username = data.get("username");
	    String email = data.get("email");

	    User user = userService.findByUsernameEmail(username, email);
	    System.out.println("컨트롤러 아이디: " + username);
	    System.out.println("컨트롤러 이메일: " + email);
	    System.out.println("컨트롤러 유저: " + user);

	    if (user != null && user.getEmail().equals(email)) {
	        // 아이디와 이메일이 일치할 경우
	        System.out.println("여기오면혼난다");
	        return new ResponseEntity<>("success", HttpStatus.OK);
	    } else {
	        // 아이디와 이메일이 일치하지 않을 경우
	        System.out.println("여기옴?");
	        return new ResponseEntity<>("Not Matched", HttpStatus.BAD_REQUEST);
	    }
	}
	


	@PostMapping("/find-password")
	@ResponseBody
	public ResponseEntity<String> findPassword(@RequestBody String email) {
		// 이메일 주소를 사용하여 사용자를 검색
		User user = userService.findUserByEmail(email);
		System.out.println("컨트롤러 유저:" + user);
		System.out.println("컨트롤러 이메일:" + email);

		if (user != null) {
			// 인증 코드 생성 (이 예시에서는 랜덤한 문자열로 생성)
			String randomCode = RandomCodeGenerator.generateRandomCode();

			// 사용자에게 이메일로 인증 코드 전송
			session.setAttribute("randomCode", randomCode);

			// 이메일로 코드 전송
			emailService.sendVerificationCode(email, randomCode);

			return new ResponseEntity<>(randomCode, HttpStatus.OK);
		} else {
			// 사용자를 찾지 못한 경우 오류 메시지를 반환
			return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/reset-password")
	@ResponseBody
	public ResponseEntity<String> resetPassword(@RequestParam String email) {
		// 1. 이메일 주소를 사용하여 사용자를 검색합니다.
		System.out.println("컨트롤러 임시비밀번호발급 이메일 확인 :" + email);
		User user = userService.findUserByEmail(email);

		if (user != null) {
			// 2. 사용자를 찾았다면, 새로운 임시 비밀번호를 생성합니다.
			String temporaryPassword = RandomPasswordGenerator.generateRandomPassword();

			// 3. 사용자의 비밀번호를 임시 비밀번호로 업데이트합니다.
			user.setPassword(temporaryPassword);

			// 사용자 데이터베이스에 변경 사항을 저장합니다.
			userService.updateUserPassword(user);

			// 이메일 전송
			try {
				sendEmail(user.getEmail(), "임시 비밀번호 발급", "임시 비밀번호: " + temporaryPassword);
			} catch (MessagingException e) {
				return new ResponseEntity<>("이메일 전송 중 오류 발생", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			return new ResponseEntity<>("임시 비밀번호 발급 및 이메일 전송이 완료되었습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
		}
	}

	private void sendEmail(String to, String subject, String text) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text, true);

		mailSender.send(message);
	}

	// 수정
	@GetMapping("/userUpdate/{userId}")
	public String updateForm(@PathVariable("userId") int userId, Model model) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		System.out.println("로그인확인:" + user);

		if (user == null) {
			return "redirect:/user/sign-in";
		}

		UserUpdateDto userDetailDto = userService.findUser(userId);
		if (userDetailDto == null) {
			model.addAttribute("userDetailDto", null);
		} else {
			model.addAttribute("userDetailDto", userDetailDto);
		}
		return "/user/update";
	}

	@PostMapping("/userUpdate/{userId}")
	public String updateFormProc(@PathVariable("userId") int userId, UserUpdateDto userUpdateDto, Model model) {

		int result = userService.userDetailUpdate(userId, userUpdateDto);

		if (result > 0) {
			model.addAttribute("message", "success");
		} else {
			model.addAttribute("message", "failure");
		}

		return "redirect:/user/userUpdate/" + userId;
	}

	@PostMapping("/update-email/{userId}")
	public ResponseEntity<String> updateEmail(@RequestParam("email") String email, @PathVariable("userId") int userId) {
		// Assuming you have a userService or repository to update the email in the
		// database
		boolean emailUpdated = userService.updateEmail(email, userId);
		System.out.println(" 컨트롤러 : " + email);
		System.out.println(" 컨트롤러 : " + userId);
		if (emailUpdated) {
			return new ResponseEntity<>("Email updated successfully", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Email update failed", HttpStatus.BAD_REQUEST);
		}
	}

	// 탈퇴
	@GetMapping("/userDelete")
	public String userDelete() {
		return "/user/delete";
	}

	@PostMapping("/userDelete")
	public String userDeleteProc(UserDeleteDto userDeleteDto, Model model) {
		User user = (User) session.getAttribute(Define.PRINCIPAL);

		// 1. 유효성 검사
		if (userDeleteDto.getUsername() == null || userDeleteDto.getUsername().isEmpty()) {
			throw new CustomException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if (userDeleteDto.getPassword() == null || userDeleteDto.getPassword().isEmpty()) {
			throw new CustomException("password를 입력하시오", HttpStatus.BAD_REQUEST);
		}

		String username = userDeleteDto.getUsername();
		System.out.println("컨트롤러 첫 유저네임:" + username);
		String sessionUsername = user.getUsername();
		System.out.println("컨트롤러 첫 세션유저네임:" + sessionUsername);
		if (sessionUsername.equals(username)) {
			String password = userDeleteDto.getPassword();
			System.out.println("컨트롤러입력받은 패스워드" + password);

			// 2. 데이터베이스에서 해당 사용자의 저장된 해시된 비밀번호를 가져옵니다
			UserDeleteDto userFindPassword = userService.findUserDelete(username);

			String storedPasswordHash = userFindPassword.getPassword();

			System.out.println("컨트롤러 사용자디비에서 가져온 패스워드" + storedPasswordHash);
			// 3. 입력된 해시화된 비밀번호와 저장된 해시화된 비밀번호를 비교
			if (passwordEncoder.matches(password, storedPasswordHash)) {
				System.out.println("탈퇴서비스 여기 안옴?");
				userService.userDelete(username, password);

				// 모델에 메시지 추가
				// alert 창을 표시하는 페이지로 리다이렉트
				model.addAttribute("message", "사용자가 삭제되었습니다.");
				// 세션 로그아웃 처리
				session.invalidate();
				return "redirect:/user/delete-confirmation";
			} else {
				throw new CustomException("아이디 비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
			}
		} else {
			throw new CustomException("사용자가 다릅니다.", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/delete-confirmation")
	public String deleteConfirmation() {
		return "/user/deleteConfirmation";
	}

	// 광고 상세보기
	@GetMapping("/promotionDetail/{promotionId}")
	public String promotionDetail(@PathVariable("promotionId") int promotionId, Model model) {

		Promotion promotion = userService.findByPromotionDetail(promotionId);

		List<PromotionImg> images = userService.findImagesByPromotionId(promotionId);

		model.addAttribute("promotion", promotion);
		model.addAttribute("images", images);
		return "/user/promotionDetail";
	}

	/**
	 * 11-09 강중현 추가
	 */
	@Autowired
	public PaymentService paymentService;
	@Autowired
	public AirService airService;
	
	@GetMapping("/orderList/")
	public String orderList(Model model, PaymentDTO paymentDTO) {
		
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		
		List<Air> orderList = airService.readOrderList(user.getUserId());
		model.addAttribute("user", user);
		model.addAttribute("orderList", orderList);
		System.out.println("airList : 999. "+orderList);
		
		return "user/orderList";
	}

}
