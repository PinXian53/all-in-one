package com.pino.restapi.service;

import com.pino.db.dao.UserDAO;
import com.pino.db.model.entity.UserEntity;
import com.pino.line.LineBotService;
import com.pino.line.RoleConst;
import com.pino.line.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDAO userRepository;
    private final LineBotService lineBotService;

    public boolean isAdmin(String userId) {
        var userEntity = userRepository.findByUserId(userId);
        return userEntity != null && RoleConst.ADMIN.equals(userEntity.getRole());
    }

    public void ifNotExistAddUser(String userId) {
        if (!userRepository.existsByUserId(userId)) {
            var userProfile = lineBotService.getUserProfile(userId);
            var userEntity = new UserEntity();
            userEntity.setUserId(userProfile.getUserId());
            userEntity.setDisplayName(userProfile.getDisplayName());
            userEntity.setPictureUrl(userProfile.getPictureUrl().toString());
            userRepository.save(userEntity);
        }
    }
}
