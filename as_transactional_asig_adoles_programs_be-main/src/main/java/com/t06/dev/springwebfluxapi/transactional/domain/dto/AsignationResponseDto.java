package com.t06.dev.springwebfluxapi.transactional.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AsignationResponseDto implements Serializable {
   private static final long serialVersionUID = 8222253670338491507L;

   private Integer id;
   private Integer id_adolescent;
   private Integer id_program;
   private LocalDate assignment_date;

}
