package com.example.clickapp.entity;

import com.example.clickapp.entity.template.AbsIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WorkSpaceUser extends AbsIdEntity {

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    private WorkSpace workSpace;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    WorkSpaceRole workSpaceRole;

    private Timestamp lastActive;

    private Timestamp dateInvited;

    private Timestamp dateJoined;


}
