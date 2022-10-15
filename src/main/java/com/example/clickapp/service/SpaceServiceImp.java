package com.example.clickapp.service;

import com.example.clickapp.entity.Attachment;
import com.example.clickapp.entity.Space;
import com.example.clickapp.entity.User;
import com.example.clickapp.entity.WorkSpace;
import com.example.clickapp.entity.enums.AccessType;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.SpaceDto;
import com.example.clickapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SpaceServiceImp implements SpaceService {

    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkSpaceRoleRepository workSpaceRoleRepository;

    /**
     *  ADD NEW SPACE
     * @param spaceDto
     * @param user
     * @return
     */
    @Override
    public ApiResponse addSpace(SpaceDto spaceDto, User user) {
        Space space = new Space();
        if (addFromSpaceDtoToSpace(spaceDto, space)) return new ApiResponse("NOT FOUND WORKSPACE", false);

        space.setOwner(user);

        space.setAccessType(spaceDto.getAccessType());

        spaceRepository.save(space);

        return new ApiResponse("SAVED", true);
    }

    /**
     * EDITED SPACE
     * @param id
     * @param spaceDto
     * @return
     */
    @Override
    public ApiResponse editSpace(UUID id, SpaceDto spaceDto) {
        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty()) new ApiResponse("SPACE NOT FOUND", false);

        Space space = optionalSpace.get();

        if (addFromSpaceDtoToSpace(spaceDto, space)) return new ApiResponse("NOT FOUND WORKSPACE", false);

        space.setAccessType(spaceDto.getAccessType());
        spaceRepository.save(space);
        return new ApiResponse("EDITED", true);
    }

    /**
     * SERVISE MY METHOD
     * @param spaceDto
     * @param space
     * @return
     */
    private boolean addFromSpaceDtoToSpace(SpaceDto spaceDto, Space space) {
        space.setName(spaceDto.getName());
        space.setColor(spaceDto.getColor());


        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(spaceDto.getWorkSpaceId());
        if (optionalWorkSpace.isEmpty()) {
            return true;
        } else {
            space.setWorkSpace(optionalWorkSpace.get());
        }

        if (spaceDto.getWorkSpaceId() == null) {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(spaceDto.getAttachmentId());

            optionalAttachment.ifPresent(space::setIcon);
        }
        return false;
    }

    /**
     * GET SPACE FROM WORKSPACE
     * @param workSpaceId
     * @return
     */
    @Override
    public ApiResponse getAllSpaceFromWorkSpace(Long workSpaceId) {
        Optional<Space> optionalSpace = spaceRepository.findByWorkSpaceId(workSpaceId);
        if (optionalSpace.isEmpty()) new ApiResponse("NOT FOUND WORKsPACE",false);

        return new ApiResponse("FOUND",true , optionalSpace.get());
    }

}
