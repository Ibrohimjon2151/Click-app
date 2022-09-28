package com.example.clickapp.payload;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkSpaceDto {

    @NotNull
    private String name;

    private String color;

    private UUID attachmentId;
}
