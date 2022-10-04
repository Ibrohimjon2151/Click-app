package com.example.clickapp.payload;

import com.example.clickapp.entity.enums.AddType;
import com.example.clickapp.entity.enums.WorkSpacePermissionName;
import com.example.clickapp.entity.enums.WorkspaceRoleName;
import lombok.Data;


@Data
public class WorkSpaceRoleDto {

    private Long id;

    private String name;

    private WorkspaceRoleName extendRole;

    private WorkSpacePermissionName workSpacePermissionName;

    private AddType addType;
}
