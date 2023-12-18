package com.t06.dev.springwebfluxapi.transactional.service;

import com.t06.dev.springwebfluxapi.transactional.domain.model.Asignation;
import com.t06.dev.springwebfluxapi.transactional.repository.AsignationRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private AsignationRepository repository;

    public Mono<String> generateReport(String reportFormat) {
        String path = "C:\\Users\\USER\\Downloads";

        return repository.findAll()
                .collectList()
                .flatMap(asignation -> generateAndSaveReport(asignation, reportFormat, path))
                .onErrorResume(e -> Mono.just("Error generating report: " + e.getMessage()));
    }

    private Mono<String> generateAndSaveReport(List<Asignation> asignation, String reportFormat, String path) {
        return Mono.fromCallable(() -> {
            // load file and compile it
            File file = ResourceUtils.getFile("classpath:Asignacion.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(asignation);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Kevin");
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            saveReport(jasperPrint, reportFormat, path);

            return "Report generated successfully. Path: " + path;
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void saveReport(JasperPrint jasperPrint, String reportFormat, String path) throws JRException {
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\asignation.html");
        } else if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\asignation.pdf");
        } else {
            throw new IllegalArgumentException("Invalid report format: " + reportFormat);
        }
    }}


