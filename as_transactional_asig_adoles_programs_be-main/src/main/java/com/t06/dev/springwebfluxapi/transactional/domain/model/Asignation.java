package com.t06.dev.springwebfluxapi.transactional.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table ("adolescent_program_assignment")
@Getter
@Setter
@NoArgsConstructor
public class Asignation {

    @Id
    private Integer id;
    private Integer id_adolescent;
    private Integer id_program;
    private LocalDate assignment_date;


    public Asignation(Integer id_adolescent, Integer id_program, LocalDate assignment_date) {
        this.id_adolescent = id_adolescent;
        this.id_program = id_program;
        this.assignment_date = assignment_date;
    }

    public Asignation(Integer id, Integer id_adolescent, Integer id_program, LocalDate assignment_date) {
        this.id = id;
        this.id_adolescent = id_adolescent;
        this.id_program = id_program;
        this.assignment_date = assignment_date;
    }

}
