package com.example.clickapp.payload.RegistorDto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.apache.catalina.authenticator.SavedRequest;

@Data
public class RegisterDto {
    @NotNull
    private String fullName;
    @NotNull
    private String email;
    @NotNull
    private String password;

}
