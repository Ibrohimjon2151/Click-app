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
@NoArgsConstructor
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"work_space_id" , "name"}))
public class WorkSpaceRole extends AbsIdEntity {

    @ManyToOne
    private WorkSpace workSpace;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName workspaceRoleExtendName;

    @ManyToOne
    private Space space;

    public WorkSpaceRole(WorkSpace workSpace, String name, WorkspaceRoleName workspaceRoleName) {
        this.workSpace = workSpace;
        this.name = name;
        this.workspaceRoleExtendName = workspaceRoleName;
    }
}
