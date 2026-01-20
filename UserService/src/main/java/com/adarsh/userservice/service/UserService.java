package com.adarsh.userservice.service;

import com.adarsh.userservice.entities.UserInfo;
import com.adarsh.userservice.entities.UserInfoDto;
import com.adarsh.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@RequiredArgsConstructor
public class UserService
{
    @Autowired
    private final UserRepository userRepository;

    public UserInfoDto createOrUpdateUser(UserInfoDto userInfoDto){

        UnaryOperator<UserInfo> updatingUser = user -> {
            updateUser(user,userInfoDto);
            return userRepository.save(user);
        };

        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userInfoDto.transformToUserInfo());
        };

        UserInfo userInfo = userRepository.findByUserId(userInfoDto.getUserId())
                .map(updatingUser)
                .orElseGet(createUser);

        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfoDto.getLastName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .profilePic(userInfo.getProfilePic())
                .monthlyLimit(userInfo.getMonthlyLimit())
                .build();
    }

    private void updateUser(UserInfo user, UserInfoDto userInfoDto) {
        user.setFirstName(userInfoDto.getFirstName());
        user.setLastName(userInfoDto.getLastName());
        user.setEmail(userInfoDto.getEmail());
        user.setPhoneNumber(userInfoDto.getPhoneNumber());
        user.setProfilePic(userInfoDto.getProfilePic());
        user.setMonthlyLimit(userInfoDto.getMonthlyLimit());
    }

    public UserInfoDto getUser(UserInfoDto userInfoDto) throws Exception{
        Optional<UserInfo> userInfoOpt = userRepository.findByUserId(userInfoDto.getUserId());
        if(userInfoOpt.isEmpty()){
            throw new Exception("User not found");
        }
        UserInfo userInfo = userInfoOpt.get();
        return UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .email(userInfo.getEmail())
                .phoneNumber(userInfo.getPhoneNumber())
                .profilePic(userInfo.getProfilePic())
                .monthlyLimit(userInfo.getMonthlyLimit())
                .build();

    }

}