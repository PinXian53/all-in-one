package com.pino.db.dao;

import com.pino.db.dao.jpa.UserRepository;
import com.pino.db.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends BaseDAO<UserEntity, Integer> {
    private final UserRepository userRepository;

    public UserDAO(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    public UserEntity findByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }

    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
