package com.pino.restapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContentDTO {

    private String content;

    public static ContentDTO of(String content) {
        return new ContentDTO(content);
    }
}
