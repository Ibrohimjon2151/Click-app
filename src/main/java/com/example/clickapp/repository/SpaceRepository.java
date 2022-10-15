package com.example.clickapp.repository;

import com.example.clickapp.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public  interface SpaceRepository extends JpaRepository<Space , UUID> {

    Optional<Space> findByWorkSpaceId(Long workSpace_id);
}
