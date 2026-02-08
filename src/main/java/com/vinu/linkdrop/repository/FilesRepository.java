package com.vinu.linkdrop.repository;

import com.vinu.linkdrop.entity.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilesRepository extends JpaRepository<UserFile,Long> {
}
