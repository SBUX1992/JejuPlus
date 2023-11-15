package com.green.jejuplus.controller.air;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.jejuplus.dto.air.AirDTO;
import com.green.jejuplus.dto.air.CustomerDTO;
import com.green.jejuplus.dto.payment.PaymentDTO;
import com.green.jejuplus.repository.interfaces.PaymentRepository;
import com.green.jejuplus.repository.model.Payment;
import com.green.jejuplus.repository.model.User;
import com.green.jejuplus.service.air.AirService;
import com.green.jejuplus.service.air.OpenApiAirService;
import com.green.jejuplus.service.payment.PaymentService;
import com.green.jejuplus.util.Define;

@Controller
@RequestMapping("/air")
public class AirController {

	@Autowired
	private OpenApiAirService openApiAirService;

	@Autowired
	private HttpSession session;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private AirService airService;
	
	@Autowired
	private PaymentRepository paymentRepository;

	// main page (강중현)
	@GetMapping("/index")
	public String index() throws Exception {
		// 유저 정보 받아옴
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		System.out.println("받은 user" + user);

		return "air/index";
	}

	@PostMapping("/index")
	public String indexProc(AirDTO airDTO) throws Exception {

		// 출발지와 도착지가 '선택'으로 설정된 경우에 페이지 이동 금지
		if (airDTO.getDepAirportNm() == null || airDTO.getArrAirportNm() == null
				|| airDTO.getDepAirportNm().equals("선택") || airDTO.getArrAirportNm().equals("선택")) {
			return "redirect:/air/index";
		}
		// 출발지와 도착지가 동일한 경우 페이지 이동 안됨
		if (airDTO.getDepAirportNm().equals(airDTO.getArrAirportNm())) {
			return "redirect:/air/index";
		}

		// 날짜 형식을 "yyyymmdd"로 변경
		String depPlandTime = airDTO.getDepPlandTime().replaceAll("-", "");
		String arrPlandTime = airDTO.getArrPlandTime().replaceAll("-", "");
		airDTO.setDepPlandTime(depPlandTime);
		airDTO.setArrPlandTime(arrPlandTime);

		// 세션에 airDTO 데이터 담음
		session.setAttribute("airDTO", airDTO);
		System.out.println("index 데이터 넘김 확인 " + airDTO);
		return "redirect:/air/booking";
	}

	// 예약 및 결제 페이지
	@GetMapping("/booking")
	public String booking(Model model) throws Exception {

		// 유저 정보 받아옴
		User user = (User) session.getAttribute(Define.PRINCIPAL);

		// session에 담긴 데이터를 받음
		AirDTO airDTO = (AirDTO) session.getAttribute("airDTO");

		String depAirportId = airDTO.getDepAirportNm();
		String arrAirportId = airDTO.getArrAirportNm();
		String depPlandTime = airDTO.getDepPlandTime();
		String arrPlandTime = airDTO.getArrPlandTime();

		// 가는 편
		// openApiAirService.OpenApiAir() 메서드를 호출하여 API 응답 데이터 호출
		String n1_apiResponse = openApiAirService.n1_OpenApiAir(depAirportId, arrAirportId, depPlandTime, arrPlandTime);

		// ObjectMapper를 사용하여 JSON 데이터를 Map으로 파싱
		ObjectMapper n1_objectMapper = new ObjectMapper();

		// 필요한 데이터 추출
		Map<String, Object> n1_responseMap = n1_objectMapper.readValue(n1_apiResponse, Map.class);
		Map<String, Object> n1_responseBody = (Map<String, Object>) n1_responseMap.get("response");
		Map<String, Object> n1_body = (Map<String, Object>) n1_responseBody.get("body");
		Map<String, Object> n1_items = (Map<String, Object>) n1_body.get("items");
		List<Map<String, Object>> n1_itemList = (List<Map<String, Object>>) n1_items.get("item");
		// 모델에 데이터를 추가
		for (int i = 0; i < n1_itemList.size(); i++) {
			Map<String, Object> n1_item = n1_itemList.get(i);

			// 나머지 데이터 가져오기
			String airlineNm = (String) n1_item.get("airlineNm");
			String depAirportNm = (String) n1_item.get("depAirportNm");
			String arrAirportNm = (String) n1_item.get("arrAirportNm");

			// 출발 도착 시간 차이 계산
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			Date depTimeDate = dateFormat.parse(n1_item.get("depPlandTime").toString());
			Date arrTimeDate = dateFormat.parse(n1_item.get("arrPlandTime").toString());
			long timeDifference = arrTimeDate.getTime() - depTimeDate.getTime();
			long minutesDifference = timeDifference / (1000 * 60);

			// 가장 뒤의 1010만 추출
//			String depPlandTimeLastFour = n1_item.get("depPlandTime").toString().substring(8);
//			String arrPlandTimeLastFour = n1_item.get("arrPlandTime").toString().substring(8);

			// economyCharge 값 확인
			String economyCharge = null;
			if (n1_item.containsKey("economyCharge")) {
				economyCharge = n1_item.get("economyCharge").toString();

				// ',' 추가
				DecimalFormat decimalFormat = new DecimalFormat("#,###");
				economyCharge = decimalFormat.format(Double.parseDouble(economyCharge));
			}

			// 시간 put 으로 삽입
			n1_item.put("economyCharge", economyCharge);
			n1_item.put("flightTimeMinutes", minutesDifference);
//			n1_item.put("depPlandTimeLastFour",
//					depPlandTimeLastFour.substring(0, 2) + ":" + depPlandTimeLastFour.substring(2));
//			n1_item.put("arrPlandTimeLastFour",
//					arrPlandTimeLastFour.substring(0, 2) + ":" + depPlandTimeLastFour.substring(2));

			// 모델에 데이터를 추가
//	        model.addAttribute("n1_depPlandTime" + i, depPlandTimeLastFour);
//	        model.addAttribute("n1_arrPlandTime" + i, arrPlandTimeLastFour);
//	        model.addAttribute("n1_airlineNm" + i, airlineNm);
//	        model.addAttribute("n1_depAirportNm" + i, depAirportNm);
//	        model.addAttribute("n1_arrAirportNm" + i, arrAirportNm);
//	        model.addAttribute("n1_flightTimeMinutes" + i, minutesDifference);
		}

		// 오는 편
		// openApiAirService.OpenApiAir() 메서드를 호출하여 API 응답 데이터 호출
		String n2_apiResponse = openApiAirService.n2_OpenApiAir(depAirportId, arrAirportId, depPlandTime, arrPlandTime);

		// ObjectMapper를 사용하여 JSON 데이터를 Map으로 파싱
		ObjectMapper n2_objectMapper = new ObjectMapper();

		// 필요한 데이터 추출
		Map<String, Object> n2_responseMap = n2_objectMapper.readValue(n2_apiResponse, Map.class);
		Map<String, Object> n2_responseBody = (Map<String, Object>) n2_responseMap.get("response");
		Map<String, Object> n2_body = (Map<String, Object>) n2_responseBody.get("body");
		Map<String, Object> n2_items = (Map<String, Object>) n2_body.get("items");
		List<Map<String, Object>> n2_itemList = (List<Map<String, Object>>) n2_items.get("item");

		// 모델에 데이터를 추가
		for (int i = 0; i < n2_itemList.size(); i++) {
			Map<String, Object> n2_item = n2_itemList.get(i);

			// 나머지 데이터 가져오기
			String airlineNm = (String) n2_item.get("airlineNm");
			String depAirportNm = (String) n2_item.get("depAirportNm");
			String arrAirportNm = (String) n2_item.get("arrAirportNm");

			// 출발 도착 시간 차이 계산
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			Date depTimeDate = dateFormat.parse(n2_item.get("depPlandTime").toString());
			Date arrTimeDate = dateFormat.parse(n2_item.get("arrPlandTime").toString());
			long timeDifference = arrTimeDate.getTime() - depTimeDate.getTime();
			long minutesDifference = timeDifference / (1000 * 60);

			// 가장 뒤의 1010만 추출
//			String depPlandTimeLastFour = n2_item.get("depPlandTime").toString().substring(8);
//			String arrPlandTimeLastFour = n2_item.get("arrPlandTime").toString().substring(8);

			// economyCharge 값 확인
			String economyCharge = null;
			if (n2_item.containsKey("economyCharge")) {
				economyCharge = n2_item.get("economyCharge").toString();

				// ',' 추가
				DecimalFormat decimalFormat = new DecimalFormat("#,###");
				economyCharge = decimalFormat.format(Double.parseDouble(economyCharge));
			}

			// 시간 put 으로 삽입
			n2_item.put("economyCharge", economyCharge);
			n2_item.put("flightTimeMinutes", minutesDifference);
//			n2_item.put("depPlandTimeLastFour",
//					depPlandTimeLastFour.substring(0, 2) + ":" + depPlandTimeLastFour.substring(2));
//			n2_item.put("arrPlandTimeLastFour",
//					arrPlandTimeLastFour.substring(0, 2) + ":" + depPlandTimeLastFour.substring(2));

			// 모델에 데이터를 추가
//	        model.addAttribute("n2_depPlandTime" + i, depPlandTimeLastFour);
//	        model.addAttribute("n2_arrPlandTime" + i, arrPlandTimeLastFour);
//	        model.addAttribute("n2_airlineNm" + i, airlineNm);
//	        model.addAttribute("n2_depAirportNm" + i, depAirportNm);
//	        model.addAttribute("n2_arrAirportNm" + i, arrAirportNm);
//	        model.addAttribute("n2_flightTimeMinutes" + i, minutesDifference);
		}

		model.addAttribute("n1_itemList", n1_itemList);
		model.addAttribute("n2_itemList", n2_itemList);
		System.out.println("n1_itemList : " + n1_itemList);
		System.out.println("n2_itemList : " + n2_itemList);
		model.addAttribute("userId", user.getUserId());

		return "air/booking";
	}

	// 예약 및 결제 페이지
	@PostMapping("/booking")
	@ResponseBody
	public String bookingProc(Model model, CustomerDTO customerDTO, PaymentDTO paymentDTO, AirDTO airDTO,
			@RequestParam(name = "airlineName", required = false) String airlineName,
			@RequestParam(name = "n1DepAirport", required = false) String n1DepAirport,
			@RequestParam(name = "n1ArrAirport", required = false) String n1ArrAirport,
			@RequestParam(name = "n2DepAirport", required = false) String n2DepAirport,
			@RequestParam(name = "n2ArrAirport", required = false) String n2ArrAirport,
			@RequestParam(name = "n1DepPlandTime", required = false) String n1DepPlandTime,
			@RequestParam(name = "n1ArrPlandTime", required = false) String n1ArrPlandTime,
			@RequestParam(name = "n2DepPlandTime", required = false) String n2DepPlandTime,
			@RequestParam(name = "n2ArrPlandTime", required = false) String n2ArrPlandTime,
			@RequestParam(name = "depPrice", required = false) String depPrice,
			@RequestParam(name = "arrPrice", required = false) String arrPrice
			) throws Exception {

		// 유저 정보 받아옴
		User user = (User) session.getAttribute(Define.PRINCIPAL);
		System.out.println("받은 user : " + user);

		String pgTid = request.getParameter("pg_tid"); // "pg_tid" 값을 직접 가져옴
		int userId = user.getUserId();

//		System.out.println("userId 5. : " + userId);
		paymentDTO.setUserId(userId);
		paymentDTO.setPgTid(pgTid);

		System.out.println("받은 pg_tid : " + pgTid);
		System.out.println("받은 userId : " + userId);

		paymentService.insertPayment(paymentDTO, userId);

		System.out.println("paymentService paymentDTO : 1. " + paymentDTO);

		session.setAttribute("paymentDTO", paymentDTO);
		System.out.println("paymentService paymentDTO : 2. " + paymentDTO);
//		System.out.println("받은 airlineName : " + airlineName);
//		System.out.println("받은 n1DepAirport : " + n1DepAirport);
//		System.out.println("받은 n1ArrAirport : " + n1ArrAirport);
//		System.out.println("받은 n2DepAirport : " + n2DepAirport);
//		System.out.println("받은 n2ArrAirport : " + n2ArrAirport);
//		System.out.println("받은 n1DepPlandTime : " + n1DepPlandTime);
//		System.out.println("받은 n1ArrPlandTime : " + n1ArrPlandTime);
//		System.out.println("받은 n2DepPlandTime : " + n2DepPlandTime);
//		System.out.println("받은 n2ArrPlandTime : " + n2ArrPlandTime);
		System.out.println("받은 depPrice : " + depPrice);
		System.out.println("받은 arrPrice : " + arrPrice);

		
		// PaymentDTO를 세션에서 가져온 후 paymentId 값을 확인하는 코드
		
		Payment savedPayment = paymentRepository.findById(paymentDTO.getPaymentId());
		int paymentId = savedPayment.getPaymentId();
		
		System.out.println("savedPayment : "+savedPayment);
		System.out.println("paymentId 값 확인 : 1111." + paymentId);

		airDTO.setUserId(userId);
		airDTO.setPaymentId(paymentId);
		airDTO.setAirlineNm(airlineName);
		airDTO.setDepAirportNm(n1DepAirport);
		airDTO.setArrAirportNm(n1ArrAirport);
		airDTO.setDepPlandTime(n1DepPlandTime);
		airDTO.setArrPlandTime(n1ArrPlandTime);

		airDTO.setDepAirportNm2(n2DepAirport);
		airDTO.setArrAirportNm2(n2ArrAirport);
		airDTO.setDepPlandTime2(n2DepPlandTime);
		airDTO.setArrPlandTime2(n2ArrPlandTime);

		System.out.println("paymentId : 555. " + paymentId);
		airService.insertAir(airDTO, userId, paymentDTO);
		System.out.println("airService airDTO : " + airDTO);

		// 세션에 데이터 담음

		session.setAttribute("airDTO", airDTO);
		session.setAttribute("customerDTO", customerDTO);
//		System.out.println("booking에서 customerDTO 데이터 넘김 확인 " + customerDTO);

		// Object 로 리턴해야 중간에 메세지 컨번터가 JSON 문자열로 변환해서 던져 준다.
		String jsonResult = "1";

		return jsonResult;
	}

	// 결제 완료 페이지
	@GetMapping("/bookingcomplete")
	public String bookingcomplete(Model model, PaymentDTO paymentDTO) {

		// 유저 정보 받아옴
		User user = (User) session.getAttribute(Define.PRINCIPAL);

		// PaymentService를 사용하여 paymentId 값을 가지고 옴
		Payment payList = paymentService.payNumber(paymentDTO.getPaymentId());
		AirDTO airDTO = (AirDTO)session.getAttribute("airDTO"); 
		
		// payList를 모델에 추가
		model.addAttribute("user", user);
		model.addAttribute("airDTO", airDTO);
		model.addAttribute("payList", payList);
		
//		System.out.println("paylist : 777. " + payList);
//		System.out.println("paymentDTO : 888. "+paymentDTO);
//		System.out.println("airDTO : 999. "+airDTO);

		return "air/bookingcomplete";
	}

}
