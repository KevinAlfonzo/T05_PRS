package com.soa.canete.teen_soa_canete.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransactionalRequest {
    private String description;
    @JsonProperty("estado")
    private String status;
    @JsonProperty("id_adolescente")
    private Integer teenId;
    @JsonProperty("id_funcionary")
    private Integer funcionaryId;
    private LocalDate start_date;
    private LocalDate register_date;
}
