package com.example.clickapp.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import javax.persistence.Table;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD ,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurenntUser {
}
