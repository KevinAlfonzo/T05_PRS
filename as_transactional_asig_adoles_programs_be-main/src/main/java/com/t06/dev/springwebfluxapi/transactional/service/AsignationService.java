package com.t06.dev.springwebfluxapi.transactional.service;

import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationRequestDto;
import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationResponseDto;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Adolescents;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation_View;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Programs;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface AsignationService {

    Mono<AsignationResponseDto> findById(Integer id);

    Flux<AsignationResponseDto> findAll();

    Flux<AsignationResponseDto> findInactive();

    Mono<AsignationResponseDto> create(AsignationRequestDto request);

    Flux<AsignationResponseDto> createMultiple(List<AsignationRequestDto> requests);

    Mono<AsignationResponseDto> update(Integer id, AsignationRequestDto request);

    Mono<Void> delete(Integer id);

    Flux<Programs> listPrograms();
    Flux<Adolescents> listAdolescents();

    Flux<Asignation_View> findAllAssignments();
}
