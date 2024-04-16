package moyeora.myapp.service.impl;

import java.util.HashMap;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.SubscribePayload;
import moyeora.myapp.dto.payment.RegularPaymentDTO;
import moyeora.myapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RegularPaymentService implements PaymentService {

  final String restapi_key;
  final String private_key;

  public RegularPaymentService(
    @Value("${bootpay.restapi.key") String restapi_key,
      @Value("${bootpay.private.key") String private_key) {
    this.restapi_key = restapi_key;
    this.private_key = private_key;
  }
  public void purchase(RegularPaymentDTO regularPaymentDTO) {
    Bootpay bootpay = new Bootpay(restapi_key,private_key);
    SubscribePayload payload = new SubscribePayload();
    if(regularPaymentDTO.getName().equals("VIP")) {
      payload.price = 5000;
    }
    if(regularPaymentDTO.getName().equals("VVIP")) {
      payload.price = 10000;
    }
    payload.billingKey= regularPaymentDTO.getBillingKey();
    payload.orderName = regularPaymentDTO.getName();
    payload.orderId = "moyeora" + (System.currentTimeMillis() / 1000);
    try {
      HashMap res = bootpay.requestSubscribe(payload);
      JSONObject json =  new JSONObject(res);
      System.out.println(json);
      if(res.get("error_code") == null) {

      }
    } catch ( Exception e) {
      e.printStackTrace();
    }
  }

}
