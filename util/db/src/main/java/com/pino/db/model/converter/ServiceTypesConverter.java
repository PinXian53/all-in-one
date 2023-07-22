package com.pino.db.model.converter;

import com.pino.enums.ServiceTypes;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ServiceTypesConverter implements AttributeConverter<ServiceTypes, String> {

    @Override
    public String convertToDatabaseColumn(ServiceTypes attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public ServiceTypes convertToEntityAttribute(String dbData) {
        return ServiceTypes.fromValue(dbData);
    }
}
