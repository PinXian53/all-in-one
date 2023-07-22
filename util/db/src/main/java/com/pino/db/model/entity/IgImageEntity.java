package com.pino.db.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ig_image", schema = "public")
public class IgImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "file_name")
    private String fileName;
}
