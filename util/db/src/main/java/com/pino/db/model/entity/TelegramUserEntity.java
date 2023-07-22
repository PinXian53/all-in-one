package com.pino.db.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "telegram_user", schema = "public")
public class TelegramUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "sub")
    private Boolean sub;

}
