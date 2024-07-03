package com.luciano.ead.course.controller.dto;

import com.luciano.ead.course.enuns.UserStatus;
import com.luciano.ead.course.enuns.UserType;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID userId;
    private String name;
    private String email;
    private String fullName;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageURL;
}
