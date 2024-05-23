package moyeora.myapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.request.SubscribePayload;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.*;
import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import moyeora.myapp.service.PaymentService;
import moyeora.myapp.vo.BillingKey;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegularPaymentService implements PaymentService {

  private final PurchaseDao purchaseDao;
  private final UserDao userDao;
  private final SchoolDao schoolDao;
  private final BillingKeyDao billingKeyDao;
  private final SchoolUserDao schoolUserDao;

  @Value("${bootpay.restapi.key}")
  String restapi_key;

  @Value("${bootpay.private.key}")
  String private_key;


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
    if(purchaseDao.findExpiredDate(regularPaymentRequestDTO.getUserNo())!=null) {
      java.sql.Date currentDate = purchaseDao.findExpiredDate(regularPaymentRequestDTO.getUserNo());
      LocalDate localDate = currentDate.toLocalDate();
      LocalDate newLocalDate = localDate.plusMonths(1);
      java.sql.Date newDate = java.sql.Date.valueOf(newLocalDate);
      regularPaymentRequestDTO.setNextBillingDate(newDate);
    } else {
      java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

// LocalDate로 변환
      LocalDate localDate = currentDate.toLocalDate();
      LocalDate newLocalDate = localDate.plusMonths(1);
      java.sql.Date newDate = java.sql.Date.valueOf(newLocalDate);
      regularPaymentRequestDTO.setNextBillingDate(newDate);
    }
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
    for(int a : schoolUserDao.findMasterByUserNo(userNo)) {
      schoolUserDao.forcedDrop(a);
    }
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

