package com.kib.weblab4.communication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class HitCheckRequest {

    @NotNull
    @Min(value = -3, message = "Min value for X is -3")
    @Max(value = 5, message = "Max value for X is 5")
    private Float x;
    @NotNull
    @Min(value = -5, message = "Min value for Y is -5")
    @Max(value = 5, message = "Max value for Y is 5")
    private Float y;
    @NotNull
    @Min(value = -3, message = "Min value for R is -3")
    @Max(value = 5, message = "Max value for R is 5")
    private Integer r;
    @JsonProperty("timezone_offset")
    @NotNull
    private Integer timezoneOffset;

}
