package moyeora.myapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.SubscribePayload;
import lombok.NoArgsConstructor;
import moyeora.myapp.dao.BillingKeyDao;
import moyeora.myapp.dao.PurchaseDao;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import moyeora.myapp.service.PaymentService;
import moyeora.myapp.vo.BillingKey;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
@NoArgsConstructor
public class RegularPaymentService implements PaymentService {

  private PurchaseDao purchaseDao;
  private UserDao userDao;
  private SchoolDao schoolDao;
  private BillingKeyDao billingKeyDao;
  String restapi_key;
  String private_key;

  public RegularPaymentService(
          @Value("${bootpay.restapi.key}") String restapi_key,
          @Value("${bootpay.private.key}") String private_key,
          PurchaseDao purchaseDao,
          UserDao userDao,
          SchoolDao schoolDao, BillingKeyDao billingKeyDao) {
    System.out.println(restapi_key + "@@@@@@@@");
    this.restapi_key = restapi_key;
    this.private_key = private_key;
    this.purchaseDao = purchaseDao;
    this.userDao = userDao;
    this.schoolDao = schoolDao;
    this.billingKeyDao = billingKeyDao;
  }


  @Transactional
  public void purchase(RegularPaymentRequestDTO regularPaymentRequestDTO) throws Exception {
    Bootpay bootpay = new Bootpay(restapi_key, private_key);
    bootpay.getAccessToken();
    SubscribePayload payload = new SubscribePayload();
    if (regularPaymentRequestDTO.getContent().equals("vip")) {
      payload.price = 5000;
    } else if (regularPaymentRequestDTO.getContent().equals("vvip")) {
      payload.price = 10000;
    } else {
      throw new Exception();
    }
    Calendar c = Calendar.getInstance();
    c.add(Calendar.MONTH, 1);
    if (c.get(Calendar.DAY_OF_MONTH) > 28) {
      c.set(Calendar.DAY_OF_MONTH, 28);
    }
    Date nextBillingDate = new Date(c.getTimeInMillis());
    regularPaymentRequestDTO.setNextBillingDate(nextBillingDate);

    payload.billingKey = regularPaymentRequestDTO.getBillingKey();
    payload.orderName = regularPaymentRequestDTO.getContent();
    payload.orderId = "moyeora" + (System.currentTimeMillis() / 1000);
    System.out.println(payload);
    try {
      HashMap<String, Object> res = bootpay.requestSubscribe(payload);
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(res);
      JSONObject jsonObject = new JSONObject(json);
      if (res.get("error_code") == null) {
        regularPaymentRequestDTO.setReceiptNo(jsonObject.getString("receipt_id"));
        regularPaymentRequestDTO.setPrice(jsonObject.getInt("price"));
        purchaseDao.add(regularPaymentRequestDTO);
        userDao.updateGrade(
          regularPaymentRequestDTO.getUserNo(),
          regularPaymentRequestDTO.getContent().equals("vip") ? 1 : 2);
        if (regularPaymentRequestDTO.getContent().equals("vip")) {
          schoolDao.updateLimitedMan(regularPaymentRequestDTO.getUserNo(), 50);
        }
        if (regularPaymentRequestDTO.getContent().equals("vvip")) {
          schoolDao.updateLimitedMan(regularPaymentRequestDTO.getUserNo(), 100);
        }
      } else {
        billingKeyDao.errorCountAdd(regularPaymentRequestDTO.getUserNo());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void billingKeySave(RegularPaymentRequestDTO regularPaymentRequestDTO) {
    billingKeyDao.add(regularPaymentRequestDTO);
  }

  public List<BillingKey> billingKeyFindByDate(Date date) {
    return billingKeyDao.findByDate(date);
  }

  public void billingKeyUpdateDate(Date date, int userNo) {
    billingKeyDao.updateNextBillingDate(date, userNo);
  }

  @Override
  public List<BillingKey> findErrorCount() {
    return billingKeyDao.findErrorCount();
  }

  public void stopSubscribe(int userNo) {
    schoolDao.updateLimitedMan(userNo, 30);
    billingKeyDao.deleteKey(userNo);
  }

  public void deleteByError() {
    billingKeyDao.deleteByError();
  }

  public void deleteBillingKeyByUserNo(int userNo) {
    billingKeyDao.deleteByUserNo(userNo);
  }

  public int findBillingKeyByUserNo(int userNo) {
    return billingKeyDao.findByUserNo(userNo);
  }

}

