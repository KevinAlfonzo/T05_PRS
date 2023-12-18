package com.t06.dev.springwebfluxapi.transactional.service.impl;

import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationRequestDto;
import com.t06.dev.springwebfluxapi.transactional.domain.dto.AsignationResponseDto;
import com.t06.dev.springwebfluxapi.transactional.domain.mapper.AsignationMapper;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Adolescents;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation_View;
import com.t06.dev.springwebfluxapi.transactional.domain.model.Programs;
import com.t06.dev.springwebfluxapi.transactional.exception.ResourceNotFoundException;
import com.t06.dev.springwebfluxapi.transactional.repository.AsignationRepository;
import com.t06.dev.springwebfluxapi.transactional.service.AsignationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;

import static com.t06.dev.springwebfluxapi.transactional.domain.mapper.AsignationMapper.toModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsignationServiceImpl implements AsignationService {

    private final AsignationRepository asignationRepository;

    private final DatabaseClient databaseClient;


    @Override
    public Mono<AsignationResponseDto> findById(Integer id) {
        return this.asignationRepository.findById(id)
                .map(AsignationMapper::toDto);
    }

    @Override
    public Flux<AsignationResponseDto> findAll() {
        return this.asignationRepository.findAll()
                /*.filter(programa -> programa.getStatus().equals("A"))*/
                .map(AsignationMapper::toDto);
    }

    @Override
    public Flux<AsignationResponseDto> findInactive() {
        return this.asignationRepository.findAll()
               /* .filter(programa -> programa.getStatus().equals("I"))*/
                .map(AsignationMapper::toDto);
    }

    @Override
    public Mono<AsignationResponseDto> create(AsignationRequestDto request) {
        return this.asignationRepository.save(toModel(request))
                .map(AsignationMapper::toDto);
    }

    @Override
    public Flux<AsignationResponseDto> createMultiple(List<AsignationRequestDto> requests) {
        return null;
    }


    @Override
    public Mono<AsignationResponseDto> update(Integer id, AsignationRequestDto request) {
        return this.asignationRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El id : " + id + " no fue encontrado")))
                .flatMap(programs -> this.asignationRepository.save(toModel(programs.getId(), request)))
                .map(AsignationMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return this.asignationRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("El id : " + id + " no fue encontrado")))
                .flatMap(programa -> {
                    /*programa.setStatus("I");*/
                    return this.asignationRepository.save(programa);
                })
                .doOnSuccess(unused -> log.info("Se elimino el programa con id: {}", id))
                .then();

    }
    @Override
    public Flux<Programs> listPrograms() {
        WebClient webClient = WebClient.create("http://localhost:9898/v1/programs/list");
        Flux<Programs> programs = webClient.get()
                .retrieve()
                .bodyToFlux(Programs.class);

        return programs;
    }

    @Override
    public Flux<Adolescents> listAdolescents() {
        WebClient webClient = WebClient.create("http://localhost:8082/api/teenData/listData");
        Flux<Adolescents> adolescents = webClient.get()
                .retrieve()
                .bodyToFlux(Adolescents.class);

        return adolescents;
    }
    public Flux<Asignation_View> findAllAssignments() {
        return this.asignationRepository.findAllAssignments();
    }

    public ResponseEntity<Resource> jasperReport(String reportPath, String outputFileName, HashMap<String, Object> parameters) throws JRException {

        try {
            final File file = ResourceUtils.getFile("classpath:"+reportPath);
            //final File imgLogo = ResourceUtils.getFile("classpath:images/LogoSOA.png");
            final JasperReport report = (JasperReport) JRLoader.loadObject(file);
            //parameters.put("logo", new FileInputStream(imgLogo));

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);

            StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(outputFileName).toString())
                    .build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);
            return ResponseEntity.ok().contentLength((long) reporte.length)
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Mono<ResponseEntity<Resource>> exportar() {
        Flux<Asignation_View> data = findAllAssignments();
        return data.collectList()
                .flatMap(asignationReportDtos -> {
                    try {
                        final HashMap<String, Object> parameters = new HashMap<>();
                        parameters.put("Asignation", new JRBeanCollectionDataSource(asignationReportDtos));
                        return Mono.just(jasperReport("Programas.jasper", "asignationprograms", parameters));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Mono.error(e);
                    }
                })
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }
}
