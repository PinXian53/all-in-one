package com.pino.db.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "lang_live_streamer", schema = "public")
public class LangLiveStreamerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid")
    private Integer oid;

    @Column(name = "name")
    private String name;

    @Column(name = "id")
    private String id;

    /**
     * 狀態 (0:未開播、1:直播中)
     */
    @Column(name = "status")
    private Integer status;

    @Column(name = "last_live_start_date_time")
    private OffsetDateTime lastLiveStartDateTime;

    @Column(name = "last_live_end_date_time")
    private OffsetDateTime lastLiveEndDateTime;

    @Column(name = "ig")
    private String ig;

    @Column(name = "ig_id")
    private String igId;

}
