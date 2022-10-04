package com.example.clickapp.payload;

import com.example.clickapp.entity.enums.AddType;
import com.example.clickapp.entity.enums.WorkspaceRoleName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class MemberDto {

    private UUID id; // member id user

    private String fullName;

    private String email;

    private String roleName;

    private Timestamp lastActive;

    private Long roleId;

    private AddType addType;


}
