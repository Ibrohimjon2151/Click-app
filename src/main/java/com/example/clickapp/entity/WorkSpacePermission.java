package com.example.clickapp.entity;

import com.example.clickapp.entity.enums.WorkSpacePermissionName;
import com.example.clickapp.entity.enums.WorkspaceRoleName;
import com.example.clickapp.entity.template.AbsIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkSpacePermission extends AbsIdEntity {

    @ManyToOne
    private WorkSpaceRole workSpaceRole;


    @Enumerated(EnumType.STRING)
    private WorkSpacePermissionName workSpacePermissionName;
}
