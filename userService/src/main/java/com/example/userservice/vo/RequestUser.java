package com.example.userservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUser {

  @NotNull(message = "Email can not be null")
  @Size(min = 2, message = "Email not be less than two characters")
  @Email
  private String email;

  @NotNull(message = "password can not be null")
  @Size(min = 8, message = "password must be equal or grater than 8 characters and less than 16 characters")
  private String pwd;

  @NotNull(message = "Name can not be null")
  @Size(min = 2, message = "Name not be less than two characters")
  private String name;

  @NotNull(message = "address can not be null")
  @Size(min = 2, message = "address not be less than two characters")
  private String address;

  @NotNull(message = "phone can not be null")
  @Size(min = 2, message = "phone not be less than two characters")
  private String phone;

  @NotNull(message = "age can not be null")
  @Size(min = 2, message = "age not be less than two characters")
  private String age;

}
