package com.example.clickapp.service;

import com.example.clickapp.entity.Attachment;
import com.example.clickapp.entity.User;
import com.example.clickapp.entity.WorkSpace;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.WorkSpaceDto;
import com.example.clickapp.repository.AttachmentRepository;
import com.example.clickapp.repository.WorkSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    public ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user) {
        if (workSpaceRepository.existsByNameAndOwner_Id(workSpaceDto.getName(), user.getId())) {
            return new ApiResponse("Bunday nomli WorkSpace mavjud", false);
        }

        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workSpaceDto.getName());
        workSpace.setColor(workSpaceDto.getColor());
        if (workSpaceDto.getAttachmentId() == null) {
            workSpace.setAvatar(null);
        } else {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(workSpaceDto.getAttachmentId());
            if (optionalAttachment.isPresent()) {
                workSpace.setAvatar(optionalAttachment.get());
            } else {
                return new ApiResponse("Avatar topilmadi", false);
            }
        }
        workSpaceRepository.save(workSpace);
        return new ApiResponse("WorkSpace yaratildi" , true);
    }

    @Override
    public ApiResponse editWorkSpace(Long id, WorkSpaceDto workSpaceDto) {
        return null;
    }

    @Override
    public ApiResponse ediOwnerWorkSpace(Long workSpaceId, UUID newOwnerId) {
        return null;
    }

    @Override
    public ApiResponse deleteWorkSpace(Long id) {
        return null;
    }
}
