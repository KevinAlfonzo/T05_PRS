package com.t06.dev.springwebfluxapi.transactional.domain.mapper;

import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationRequestDto;
import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationResponseDto;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AsignationMapper {

    public static Asignation toModel(AsignationRequestDto dto) {
        return new Asignation(
                dto.getId_adolescent(),
                dto.getId_program(),
                dto.getAssignment_date()
        );
    }

    public static Asignation toModel(Integer id, AsignationRequestDto dto) {
        return new Asignation(
                id,
                dto.getId_adolescent(),
                dto.getId_program(),
                dto.getAssignment_date()
        );
    }

    public static AsignationResponseDto toDto(Asignation model) {
        return new AsignationResponseDto(
                model.getId(),
                model.getId_adolescent(),
                model.getId_program(),
                model.getAssignment_date()
        );
    }
}
