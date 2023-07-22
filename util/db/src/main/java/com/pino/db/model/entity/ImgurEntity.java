package com.pino.db.model.entity;

import com.pino.enums.ImageTypeTags;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "imgur", schema = "public")
public class ImgurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "type_tag")
    private ImageTypeTags typeTag;

    @Column(name = "person_tag")
    private String personTag;
}
