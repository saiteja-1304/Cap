package com.serviceharbor.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String name;
    private String email;
    private String password;
    private String confirm_password;
    private String city;

}
