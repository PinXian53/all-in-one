package com.pino.db.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribeDataDTO {
    private String userId;
    private String displayName;
    private String parameter;
}