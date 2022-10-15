package com.example.clickapp.entity;

import com.example.clickapp.entity.enums.AccessType;
import com.example.clickapp.entity.template.AbsUUIDEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Space extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    private WorkSpace workSpace;

    private String initialLetter;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment avatar;

    @OneToOne
    private Attachment icon;

    @OneToOne
    private User owner;

    @Enumerated(EnumType.STRING)
    private AccessType accessType;


    @PrePersist
    @PreUpdate
    public void setInitialLetter() {
        this.initialLetter = name.substring(0, 1);
    }
}
