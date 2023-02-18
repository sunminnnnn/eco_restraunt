package com.example.userservice.dto;

import com.example.userservice.vo.ResponseOrder;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class UserDto {
  private String email;
  private String pwd;
  private String name;
  private String userId;
  private Date createdAt;
  private String address;
  private String phone;
  private String age;
  private String gender;

  private String encryptedPwd;

  private List<ResponseOrder> orders;
}
