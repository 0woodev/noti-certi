package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
