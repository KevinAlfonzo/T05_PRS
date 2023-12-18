package com.t06.dev.springwebfluxapi.transactional.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AsignationRequestDto implements Serializable {
    private static final long serialVersionUID = 8222253670338491507L;

    @Column("id_adolescent")
    private Integer id_adolescent;
    @Column("id_program")
    private Integer id_program;
    private LocalDate assignment_date;
}
