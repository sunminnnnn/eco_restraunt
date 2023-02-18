package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;

public interface UserService {

  UserDto createUser(UserDto userDto);
  UserDto getUserByUserId(String userId); //UserId로 검색을 하겠다.
  String deleteUser(String userId);
  UserDto updateUser(String userId); //UserId로 업데이트를 하겠다.
  Iterable<UserEntity> getUserByAll(); //모든 유저 정보를 가져오겠다. db에서 바로 갖고오기 때문에 entity VS 가공해서 가져오면 dto

};