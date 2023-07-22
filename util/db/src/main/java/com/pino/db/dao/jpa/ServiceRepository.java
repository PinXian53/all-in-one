package com.pino.db.dao.jpa;

import com.pino.db.model.entity.ServiceEntity;
import com.pino.enums.ServiceTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceEntity, Integer> {

    ServiceEntity findByName(ServiceTypes serviceTypes);
}
