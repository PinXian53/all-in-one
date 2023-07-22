package com.pino.db.model.converter;


import com.pino.enums.ImageTypeTags;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ImageTypeTagsConverter implements AttributeConverter<ImageTypeTags, String> {

    @Override
    public String convertToDatabaseColumn(ImageTypeTags attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public ImageTypeTags convertToEntityAttribute(String dbData) {
        return ImageTypeTags.fromValue(dbData);
    }
}
