package com.pino.db.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "subscription", schema = "public")
public class SubscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "user_oid")
    private Integer userOid;

    @Column(name = "service_oid")
    private Integer serviceOid;

    @Column(name = "parameter")
    private String parameter;
}
