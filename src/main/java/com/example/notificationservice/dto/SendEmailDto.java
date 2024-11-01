package com.example.notificationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendEmailDto {
    private String name;
    private String email;
    private String roleName;
}
