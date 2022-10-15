package com.example.clickapp.payload;

import com.example.clickapp.entity.Attachment;
import com.example.clickapp.entity.User;
import com.example.clickapp.entity.WorkSpace;
import com.example.clickapp.entity.enums.AccessType;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.UUID;

@Data
public class SpaceDto {

    @NotNull
    private String name;

    @NotNull
    private String color;

    @ManyToOne
    private Long workSpaceId;

    private UUID avatarId;

    @OneToOne
    private UUID attachmentId;


    private AccessType accessType;

}
