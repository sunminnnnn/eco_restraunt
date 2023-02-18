package com.example.userservice.vo;
import java.util.Date;
import lombok.Data;

//상품에 대한 정보
@Data
public class ResponseOrder {
  private String productId;
  private Integer Qty;    //수량
  private Integer unitPrice;
  private Integer totalPrice;
  private Date createAt;

  private String orderId; //주문 아이디

}
