package com.t06.dev.springwebfluxapi.transactional.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("programs")
@Getter
@Setter
@NoArgsConstructor
public class Programs {

    @Id
    private Integer id;
    private String name;
    private String type;
    private String beneficiary;
    private String responsible;
    private String description;
    private String condition;
    private Integer duration;
    private String level;

    public Programs(Integer id, String name, String type, String beneficiary, String responsible, String description, String condition, Integer duration, String level) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.beneficiary = beneficiary;
        this.responsible = responsible;
        this.description = description;
        this.condition = condition;
        this.duration = duration;
        this.level = level;
    }

    public Programs(String name, String type, String beneficiary, String responsible, String description, String condition, Integer duration, String level) {
        this.name = name;
        this.type = type;
        this.beneficiary = beneficiary;
        this.responsible = responsible;
        this.description = description;
        this.condition = condition;
        this.duration = duration;
        this.level = level;
    }
}
