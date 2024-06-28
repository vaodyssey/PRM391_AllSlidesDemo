package com.prm391.allslidesdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoggedInUser {
    private String email;
    private String password;
}
