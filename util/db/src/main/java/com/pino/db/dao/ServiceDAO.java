package com.pino.db.dao;

import com.pino.db.dao.jpa.ServiceRepository;
import com.pino.db.model.entity.ServiceEntity;
import com.pino.enums.ServiceTypes;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceDAO extends BaseDAO<ServiceEntity, Integer> {

    private final ServiceRepository serviceRepository;

    public ServiceDAO(ServiceRepository serviceRepository) {
        super(serviceRepository);
        this.serviceRepository = serviceRepository;
    }

    public ServiceEntity findByName(ServiceTypes serviceTypes) {
        return serviceRepository.findByName(serviceTypes);
    }
}
