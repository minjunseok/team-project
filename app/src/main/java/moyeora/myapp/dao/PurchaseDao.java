package moyeora.myapp.dao;


import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Date;
import java.time.LocalDate;

@Mapper
public interface PurchaseDao {
  void add(RegularPaymentRequestDTO regularPaymentRequestDTO);
  Date findExpiredDate(int userNo);
}
