package com.t06.dev.springwebfluxapi.transactional.web;

import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationRequestDto;
import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationResponseDto;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Adolescents;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation_View;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Programs;
import com.t06.dev.springwebfluxapi.transactional.repository.AsignationRepository;
import com.t06.dev.springwebfluxapi.transactional.service.AsignationService;
import com.t06.dev.springwebfluxapi.transactional.service.ReportService;
import com.t06.dev.springwebfluxapi.transactional.service.impl.AsignationServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;

//COMMIT DE PRUEBA USANDO INTELIDEA
@RestController
@RequestMapping("/v1/asignations")
@CrossOrigin(origins = "http://localhost:4200")
public class AsignationController {

    private final AsignationService asignationService;
    private final AsignationRepository asignationRepository;
    private final ReportService service;

    private final AsignationServiceImpl impl;

    public AsignationController(AsignationService asignationService, AsignationRepository asignationRepository, ReportService service, AsignationServiceImpl impl) {
        this.asignationService = asignationService;
        this.asignationRepository = asignationRepository;
        this.service = service;
        this.impl = impl;
    }

    @GetMapping( value = "/listPrograms", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Programs> listPrograms() {
        return this.asignationService.listPrograms();
    }

    @GetMapping( value = "/listAdolescents", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Adolescents> listAdolescents() {
        return this.asignationService.listAdolescents();
    }

    @GetMapping(value ="/get-assignments", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Flux<Asignation_View> getAssignments() {return asignationRepository.findAllAssignments();}


    @PostMapping(value = "/save")
    public Mono<AsignationResponseDto> create(@RequestBody AsignationRequestDto dto) {
        return this.asignationService.create(dto);
    }

    @GetMapping(value = "/find/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_EVENT_STREAM_VALUE})
    public Mono<AsignationResponseDto> findById(@PathVariable Integer id) {
        return this.asignationService.findById(id);
    }

    @GetMapping("/report/{format}")
    public Mono<String> generateReport(@PathVariable String format) throws FileNotFoundException {
        return service.generateReport(format);
    }

    @GetMapping("/reporttest")
    public Mono<ResponseEntity<Resource>> generatedReport() throws FileNotFoundException {
        return impl.exportar();
    }

}
