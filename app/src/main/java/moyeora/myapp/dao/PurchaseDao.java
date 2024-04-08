package moyeora.myapp.dao;


import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PurchaseDao {
  void add(RegularPaymentRequestDTO regularPaymentRequestDTO);
}
