package moyeora.myapp.service;

import moyeora.myapp.dto.payment.RegularPaymentDTO;

public interface PaymentService {
  void purchase(RegularPaymentDTO regularPaymentDTO);
}
