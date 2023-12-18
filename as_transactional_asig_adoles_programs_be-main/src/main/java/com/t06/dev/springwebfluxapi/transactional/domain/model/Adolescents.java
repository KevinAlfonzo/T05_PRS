package com.t06.dev.springwebfluxapi.transactional.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("teen")
@Getter
@Setter
@NoArgsConstructor
public class Adolescents {

    @Id
    private Integer id_adolescente;
    private String name;
    private String surnamefather;
    private String surnamemother;
    private String dni;
    private String estado;
}
