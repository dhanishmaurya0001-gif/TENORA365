package com.tenora365.entity;
import jakarta.persistence.*;import lombok.*;import java.time.*;
@Entity @Table(name="module_records") @Getter @Setter @NoArgsConstructor
public class ModuleRecord{@Id @GeneratedValue(strategy=GenerationType.IDENTITY)Long id;@ManyToOne(fetch=FetchType.LAZY)@JoinColumn(name="school_id",nullable=false)@com.fasterxml.jackson.annotation.JsonIgnore School school;String module;String recordKey;@org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON) @Column(name="data_json",columnDefinition="json")String dataJson;LocalDateTime createdAt=LocalDateTime.now();LocalDateTime updatedAt;boolean active=true;}
