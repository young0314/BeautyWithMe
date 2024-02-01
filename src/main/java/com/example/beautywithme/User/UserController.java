package com.example.beautywithme.User;

import com.example.beautywithme.Dto.LoginDto;
import com.example.beautywithme.Dto.SignupDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;
import java.util.Optional;

@Slf4j
@RestController //Controller와의 차이점? 아마? RestController가 ResponseEntity감싸서 반환
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;

    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto) {
        try {
            if (userService.checkLoginIdDuplicate(signupDto.getEmail())) {
                return new ResponseEntity<>("이메일이 중복됩니다. ", HttpStatus.BAD_REQUEST);
            }
            // 닉네임 중복 체크
            if (userService.checkNicknameDuplicate(signupDto.getNickname())) {
                return new ResponseEntity<>("닉네임이 중복됩니다. ", HttpStatus.BAD_REQUEST);
            }
            // password와 passwordCheck가 같은지 체크
            if (!signupDto.getPassword().equals(signupDto.getPasswordCheck())) {
                return new ResponseEntity<>("비밀번호가 일치하지 않습니다. ", HttpStatus.BAD_REQUEST);
            }
            userService.signup(signupDto);

            // 회원가입이 성공한 경우 이메일을 사용하여 userKey를 조회하고 설정
            Optional<User> userOptional = userRepository.findByEmail(signupDto.getEmail());
            userOptional.ifPresent(user -> signupDto.setUserKey(user.getUserKey()));
            String responseMessage = "User signup successfully: " + signupDto;
            System.out.println(responseMessage);
            ObjectMapper objectMapper = new ObjectMapper();
            String responseJson = objectMapper.writeValueAsString(signupDto);
            return new ResponseEntity<>(responseJson, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Failed to signup: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Failed to serialize response: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) throws IllegalAccessException {
        boolean loginSuccess = userService.login(loginDto.getEmail(), loginDto.getPassword());
        if(loginSuccess==true){
            return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Failed : " , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
