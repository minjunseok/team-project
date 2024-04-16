package moyeora.myapp.service;

import moyeora.myapp.dto.payment.RegularPaymentDTO;
import org.springframework.stereotype.Service;


public interface PaymentService {
  void purchase(RegularPaymentDTO regularPaymentDTO);
}
