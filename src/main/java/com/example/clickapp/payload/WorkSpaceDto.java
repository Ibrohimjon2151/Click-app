package com.example.clickapp.payload;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkSpaceDto {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID attachmentId;

    private String initialLetter;

    private UUID ownerId;
}
