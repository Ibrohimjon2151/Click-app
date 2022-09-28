package com.example.clickapp.entity;

import com.example.clickapp.entity.template.AbsIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(uniqueConstraints = {@UniqueConstraint (columnNames = {"name","owner_id"})})
public class WorkSpace extends AbsIdEntity {

    @Column(nullable = false)
    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    User owner;


    private String initialLetter;

    @ManyToOne
    Attachment avatar;

    @PrePersist
    @PreUpdate
    public void  setInitialLetter() {
        this.initialLetter = name.substring(0,1);
    }





}
