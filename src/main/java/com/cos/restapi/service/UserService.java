package com.cos.restapi.service;

import com.cos.restapi.config.UserNotFoundException;
import com.cos.restapi.domain.User;
import com.cos.restapi.domain.UserRepository;
import com.cos.restapi.web.dto.UserJoinRequestDto;
import com.cos.restapi.web.dto.UserResponseDto;
import com.cos.restapi.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Long joinUser(UserJoinRequestDto userJoinRequestDto) {
        return userRepository.save(userJoinRequestDto.toEntity()).getId();
    }


    public Long updateUser(Long id, UserUpdateRequestDto updateReqDto) {
        User user = userRepository.findById2(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        user.update(updateReqDto.getPassword(), updateReqDto.getPhone());
        return userRepository.save(user).getId();
    }


//    public UserResponseDto findUser(Long id) {
//        User entity = userRepository.findById(id);
//        if (entity == null) {
//            throw new UserNotFoundException("해당 유저를 찾을 수 없습니다.");
//        }
//        return new UserResponseDto(entity);
//    }

    public UserResponseDto findUser2(Long id) {
        User entity = userRepository.findById2(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        return new UserResponseDto(entity);
    }


//    public List<UserResponseDto> findAllUsers() {
//        List<User> userList = userRepository.findAll();
//        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
//
//        for (User user : userList) {
//            userResponseDtoList.add(new UserResponseDto(user));
//        }
//        return userResponseDtoList;
//    }

    public List<UserResponseDto> findAllUsers2() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById2(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을 수 없습니다."));
        userRepository.deleteById(id);
    }
}
