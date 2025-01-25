package com.ariari.ariari.commons.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class LogicalDeleteEntity extends BaseEntity {

    private LocalDateTime deletedDateTime;

}
