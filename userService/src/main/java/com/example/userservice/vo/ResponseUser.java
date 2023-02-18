package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.sql.Timestamp;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ResponseUser {
  private String id;
  private String email;
  private String name;
  private String userId;
  private String address;
  private String phone;
  private String gender;
  private String age;

  //주문데이터 값 반환
  private List<ResponseOrder> orders;
}
