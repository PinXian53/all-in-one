package com.pino.db.dao.jpa;

import com.pino.db.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUserId(String userId);

    boolean existsByUserId(String userId);
}
