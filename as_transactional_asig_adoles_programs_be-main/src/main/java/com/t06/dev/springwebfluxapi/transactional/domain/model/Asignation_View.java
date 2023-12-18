package com.t06.dev.springwebfluxapi.transactional.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("program_assignments_view")
@Getter
@Setter
@NoArgsConstructor
public class Asignation_View {

    @Id
    private Integer id_assignments;
    private String adolescent_dni;
    private String names;
    private String program_name;
    private Integer program_duration;
    private String assignment_date;
    private String program_status;

    public Asignation_View(Integer id_assignments, String adolescent_dni, String names, String program_name, Integer program_duration, String assignment_date, String program_status) {
        this.id_assignments = id_assignments;
        this.adolescent_dni = adolescent_dni;
        this.names = names;
        this.program_name = program_name;
        this.program_duration = program_duration;
        this.assignment_date = assignment_date;
        this.program_status = program_status;
    }
}
