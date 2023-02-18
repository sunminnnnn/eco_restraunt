package com.example.userservice.jpa;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 50, unique = false)
  private String Email;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, unique = false)
  private String userId;

  @Column(nullable = false, unique = false)
  private String encryptedPwd;

  //address, phone, gender, age
  @Column(nullable = false, unique = false)
  private String address;

  @Column(nullable = false, unique = false)
  private String phone;

  @Column(nullable = true, unique = false)
  private String gender;

  @Column(nullable = false, unique = false)
  private String age;



}
