package com.adarsh.service;

import com.adarsh.entities.UserInfo;
import com.adarsh.eventProducer.UserInfoEvent;
import com.adarsh.eventProducer.UserInfoProducer;
import com.adarsh.model.UserInfoDto;
import com.adarsh.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserInfoProducer userInfoProducer;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUsername(username);
        if( user == null ){
            throw new UsernameNotFoundException("could not found user..!");
        }
        return new CustomUserDetails(user);
    }

    public UserInfo checkIfUserAlreadyExists(UserInfoDto userInfoDto){
        return userRepository.findByUsername(userInfoDto.getUsername());
    }

    public String getUserByUsername(String userName){
        return Optional.of(userRepository.findByUsername(userName)).map(UserInfo::getUserId).orElse(null);
    }

    public Boolean signUpUser(UserInfoDto userInfoDto){
        //Define a function to check if UserEmail,password is correct
        //ValidationUtil.validateUser()
        userInfoDto.setPassword(passwordEncoder.encode(userInfoDto.getPassword()));
        if(Objects.nonNull(checkIfUserAlreadyExists(userInfoDto))){
            return false;
        }
        String userId = UUID.randomUUID().toString();
        UserInfo registerUser = UserInfo.builder()
                .userId(userId)
                .username(userInfoDto.getUsername())
                .password(userInfoDto.getPassword())
                .build();
        userRepository.save(registerUser);
        UserInfoEvent event = userInfoEventToPublish(userInfoDto,userId);
        userInfoProducer.sendEventToKafka(event);
        return true;
    }
    private UserInfoEvent userInfoEventToPublish( UserInfoDto userInfoDto , String userId ){
        return UserInfoEvent.builder()
                .firstName(userInfoDto.getFirstName())
                .lastName(userInfoDto.getLastName())
                .email(userInfoDto.getEmail())
                .phoneNumber(userInfoDto.getPhoneNumber())
                .userId(userId)
                .build();
    }
}
