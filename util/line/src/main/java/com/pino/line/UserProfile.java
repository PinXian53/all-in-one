package com.pino.line;

import lombok.Data;

import java.net.URI;

@Data
public class UserProfile {
    private String userId;
    private String displayName;
    private URI pictureUrl;
}
