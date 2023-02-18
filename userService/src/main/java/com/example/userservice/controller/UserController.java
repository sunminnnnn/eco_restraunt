package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.jpa.UserEntity;
import com.example.userservice.jpa.UserRepository;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.RequestUser;
import com.example.userservice.vo.ResponseUser;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-service")
public class UserController {
  private Environment env;
  private UserService userService;
  private UserRepository userRepository;

  @Autowired
  public UserController(Environment env, UserService userService, UserRepository userRepository) {
    this.env = env;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping("/health_check")
  public String status(HttpServletRequest request) {
    return String.format("It's Working in User Service on Port %s", request.getServerPort());
  }


//  public String status() {
//    return String.format("%s", env.getProperty("configWelcome.message"));
//  }

  @GetMapping("/welcome")
  public String welcome() {
    return env.getProperty("greeting.message");
  }

  @GetMapping(value="/users")
  public ResponseEntity<List<ResponseUser>> getUsers() {
    Iterable<UserEntity> userList = userService.getUserByAll();

    List<ResponseUser> result = new ArrayList<>();
    userList.forEach(v -> {
      result.add(new ModelMapper().map(v, ResponseUser.class));
    });
    return ResponseEntity.status(HttpStatus.OK).body(result);
  }

  @GetMapping(value="/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
    UserDto userDto = userService.getUserByUserId(userId);
    ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);

    return ResponseEntity.status(HttpStatus.OK).body(returnValue);
  }


  @PostMapping("/users")
  public String createUser(@RequestBody RequestUser user) {
    ModelMapper mapper = new ModelMapper();
    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

    UserDto userDto = mapper.map(user, UserDto.class);
    userService.createUser(userDto);

    ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);

    return "회원가입이 완료되었습니다.";
  }

  @DeleteMapping(value = "users/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable String userId) {
    String message = userService.deleteUser(userId);
    return ResponseEntity.ok(message);
  }

  @PutMapping(value = "users/{userId}")
  public ResponseEntity<String> updateUser(@PathVariable String userId) {
    userService.updateUser(userId);
    String successMessage = "회원 정보가 변경되었습니다.";
    ResponseEntity<String> response = new ResponseEntity<String>(successMessage, HttpStatus.OK);
    return response;
  }


}
