package com.t06.dev.springwebfluxapi.transactional.repository;

import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation_View;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface AsignationRepository extends ReactiveCrudRepository<Asignation, Integer> {

    @Query("SELECT * FROM program_assignments_view ORDER BY id_assignments desc")
    Flux<Asignation_View> findAllAssignments();

}

//PRUEBA
