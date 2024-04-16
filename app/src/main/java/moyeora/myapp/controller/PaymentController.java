package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dto.payment.RegularPaymentDTO;
import moyeora.myapp.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage.Body;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {
  private final PaymentService paymentService;

  @GetMapping("form")
  public void form() {

  }

  @PostMapping()
  @ResponseBody
  public ResponseEntity<Void> purchase(
    @RequestBody RegularPaymentDTO regularPaymentDTO) {
    regularPaymentDTO.setUserNo(1);
    paymentService.purchase(regularPaymentDTO);
    return ResponseEntity.status(201).build();
  }
}
