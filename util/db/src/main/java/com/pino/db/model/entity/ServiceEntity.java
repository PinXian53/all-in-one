package com.pino.db.model.entity;

import com.pino.enums.ServiceTypes;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "service", schema = "public")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "name")
    private ServiceTypes name;
}
