package com.firman.demo.crud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.firman.demo.crud.entity.User;
import com.firman.demo.crud.validator.PhoneNumberConstraint;
import com.firman.demo.crud.validator.SafeStringConstraint;
import lombok.*;

import javax.validation.constraints.Email;
import java.math.BigDecimal;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    private Long id;

    @SafeStringConstraint
    private String fullName;

    @SafeStringConstraint
    private String username;

    @Email
    private String email;

    @PhoneNumberConstraint
    private String phoneNumber;
    private BigDecimal salary;

    @SafeStringConstraint
    private String address;
    private String status;

    public UserDTO(User user){
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.address = user.getAddress();
        this.status = user.getStatus();
        this.phoneNumber = user.getPhoneNumber();
        this.salary = user.getSalary();
    }

}
