package com.pino.db.dao;

import com.pino.db.dao.jpa.SubscriptionRepository;
import com.pino.db.model.dto.SubscribeDataDTO;
import com.pino.db.model.entity.SubscriptionEntity;
import com.pino.enums.ServiceTypes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscriptionDAO extends BaseDAO<SubscriptionEntity, Integer> {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionDAO(SubscriptionRepository subscriptionRepository) {
        super(subscriptionRepository);
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<SubscribeDataDTO> findSubscribeData(ServiceTypes serviceType) {
        return subscriptionRepository.findSubscribeData(serviceType);
    }
}
