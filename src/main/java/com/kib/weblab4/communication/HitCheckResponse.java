package com.kib.weblab4.communication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class HitCheckResponse {

    private Float x;
    private Float y;
    private Integer r;
    @JsonProperty("hit_check_date")
    private Instant hitCheckDate;
    private Boolean result;

}
