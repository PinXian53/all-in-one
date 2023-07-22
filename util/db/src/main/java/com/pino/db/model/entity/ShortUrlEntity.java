package com.pino.db.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "short_url", schema = "public")
public class ShortUrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "key")
    private String key;

    @Column(name = "url")
    private String url;

    @Column(name = "expired_date_time")
    private OffsetDateTime expiredDateTime;

    @Column(name = "click_count")
    private Integer clickCount;
}
