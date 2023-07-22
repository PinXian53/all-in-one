package com.pino.db.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "line_bot_log", schema = "public")
public class LineBotLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "event")
    private String event;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "message_text")
    private String messageText;

    @Column(name = "create_date_time")
    private OffsetDateTime createDateTime;
}
