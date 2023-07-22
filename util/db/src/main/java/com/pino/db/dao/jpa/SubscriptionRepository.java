package com.pino.db.dao.jpa;

import com.pino.db.model.dto.SubscribeDataDTO;
import com.pino.db.model.entity.SubscriptionEntity;
import com.pino.enums.ServiceTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {

    @Query("select new com.pino.db.model.dto.SubscribeDataDTO( "
        + "  user.userId, user.displayName, subscription.parameter"
        + " ) "
        + " from SubscriptionEntity subscription "
        + " left join UserEntity user on subscription.userOid = user.oid "
        + " left join ServiceEntity service on subscription.serviceOid = service.oid "
        + " where service.name = :serviceType ")
    List<SubscribeDataDTO> findSubscribeData(@Param("serviceType")ServiceTypes serviceType);

}
