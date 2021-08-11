package com.firman.demo.crud.service;

import com.firman.demo.crud.dto.UserDTO;
import com.firman.demo.crud.entity.User;
import com.firman.demo.crud.enums.ResponseEnum;
import com.firman.demo.crud.exception.BusinessException;
import com.firman.demo.crud.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService<User> {

    public UserDTO saveUser(UserDTO userDTO){
        return new UserDTO(save(User.builder()
            .fullName(userDTO.getFullName())
            .password(UUID.randomUUID().toString())
            .address(userDTO.getAddress())
            .username(userDTO.getUsername())
            .email(userDTO.getEmail())
            .phoneNumber(userDTO.getPhoneNumber())
            .salary(userDTO.getSalary())
            .status("ACTIVE")
            .createdBy("System")
            .createdDate(Instant.now())
            .lastModifiedBy("System")
            .lastModifiedDate(Instant.now())
            .build()));
    }

    public void updateUser(Long id, UserDTO userDTO){
        update(id, Optional.ofNullable(find(id)).orElseThrow(()->new BusinessException(ResponseEnum.USER_NOT_FOUND))
            .withAddress(userDTO.getAddress())
            .withEmail(userDTO.getEmail())
            .withFullName(userDTO.getFullName())
            .withUsername(userDTO.getUsername())
        );
    }

    public void deleteUser(Long id){
        delete(Optional.ofNullable(find(id)).orElseThrow(()->new BusinessException(ResponseEnum.USER_NOT_FOUND)).getId());
    }

    public UserDTO findUserById(Long id) {
        return new UserDTO(Optional.ofNullable(find(id)).orElseThrow(()->new BusinessException(ResponseEnum.USER_NOT_FOUND)));
    }
}
