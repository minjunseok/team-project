package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import moyeora.myapp.dto.payment.RegularPaymentResponseDTO;
import moyeora.myapp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("payment")
public class RegularPaymentController {
  private final PaymentService paymentService;

  @GetMapping("form")
  public void form() {

  }

  @PostMapping("purchase")
  @ResponseBody
  public ResponseEntity<RegularPaymentResponseDTO> purchase (
    @RequestBody RegularPaymentRequestDTO regularPaymentRequestDTO) throws Exception {
    regularPaymentRequestDTO.setUserNo(1);

    if(paymentService.findBillingKeyByUserNo(3) > 0) {
      return ResponseEntity.status(400).build();
    }

    paymentService.purchase(regularPaymentRequestDTO);
    RegularPaymentResponseDTO regularPaymentResponseDTO = new RegularPaymentResponseDTO();
    try {
      paymentService.billingKeySave(regularPaymentRequestDTO);
    } catch (Exception e) {
      //환불 적용
      regularPaymentResponseDTO.setMessage("결제에 실패하셨습니다");
      return ResponseEntity.status(400).body(regularPaymentResponseDTO);
    }
    regularPaymentResponseDTO.setMessage("결제에 성공하셨습니다");
    regularPaymentResponseDTO.setNextBillingDate(regularPaymentRequestDTO.getNextBillingDate());
    return ResponseEntity.status(201).body(regularPaymentResponseDTO);
  }

  @PostMapping("stop")
  @ResponseBody
  public ResponseEntity<RegularPaymentResponseDTO> stop() {
    paymentService.stopSubscribe(1);
    RegularPaymentResponseDTO regularPaymentResponseDTO = new RegularPaymentResponseDTO();
    regularPaymentResponseDTO.setMessage("해지가 완료되었습니다");
    return ResponseEntity.status(200).body(regularPaymentResponseDTO);
  }
}