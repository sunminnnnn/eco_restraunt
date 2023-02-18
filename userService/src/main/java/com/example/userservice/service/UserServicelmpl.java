package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.vo.ResponseOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class UserServicelmpl implements  UserService {

  UserRepository userRepository;
  Environment env;
  RestTemplate restTemplate;
  BCryptPasswordEncoder passwordEncoder;
  OrderServiceClient orderServiceClient;

  @Autowired
  public UserServicelmpl(
      UserRepository userRepository,
      Environment env,
      BCryptPasswordEncoder passwordEncoder,
      RestTemplate restTemplate,
      OrderServiceClient orderServiceClient) {
    this.userRepository = userRepository;
    this.env = env;
    this.restTemplate = restTemplate;
    this.passwordEncoder = passwordEncoder;
    this.orderServiceClient = orderServiceClient;
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    userDto.setUserId(UUID.randomUUID().toString());

    ModelMapper mapper = new ModelMapper();
    //설정 정보가 딱 맞아야 변경 가능
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    //DB 저장을 위해서는 UserEntity 필요
    UserEntity userEntity = mapper.map(userDto, UserEntity.class);
    userEntity.setEncryptedPwd("encrypted_password");

    userRepository.save(userEntity);

    return null;
  }

  public UserDto getUserByUserId(String userId){
    UserEntity userEntity = userRepository.findByUserId(userId);
//UsernameNotFoundException ; import org.springframework.security.core.userdetails.UsernameNotFoundException;

    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    /*Using Rest template
//    String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//    ResponseEntity<List<ResponseOrder>> orderListResponse =
//        restTemplate.exchange(orderUrl, HttpMethod.GET, null,
//            new ParameterizedTypeReference<List<ResponseOrder>>() {
//            });

//    List<ResponseOrder> orderList = orderListResponse.getBody();*/

    //Using a feignClient
//    List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);

    List<ResponseOrder> orderList = new ArrayList<>();

    userDto.setOrders(orderList);

    return userDto;
  }

  @Override
  public String deleteUser(String userId) {
    UserEntity user = userRepository.findByUserId(userId);
    if (user == null || !user.getUserId().equals(userId)) {
      return "회원 삭제 실패";
    }
    userRepository.deleteById(user.getId());
    return "회원 삭제를 완료하였습니다.";
  }

  @Override
  public UserDto updateUser(String userId) {
    UserEntity userEntity = userRepository.findByUserId(userId);
    UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

    userDto.setEmail(userDto.getEmail());
    userRepository.save(userEntity);
    return null;

  }


  @Override
  public Iterable<UserEntity> getUserByAll() {
    return userRepository.findAll();
  }

}
