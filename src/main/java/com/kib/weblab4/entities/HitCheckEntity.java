package com.kib.weblab4.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
@Table(name="hit_check_entity")
public class HitCheckEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Float x;

    private Float y;

    private Integer r;

    private Instant hitCheckDate;

    private boolean hitCheckResult;

    private Integer userId;
}
