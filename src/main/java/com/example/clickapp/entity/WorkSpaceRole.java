package com.example.clickapp.entity;

import com.example.clickapp.entity.enums.WorkspaceRoleName;
import com.example.clickapp.entity.template.AbsIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WorkSpaceRole extends AbsIdEntity {

    @ManyToOne
    private WorkSpace workSpace;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName workspaceRoleName;

    @Column(nullable = false)
    private Timestamp dateInvited;

    private Timestamp dateJoined;
}
