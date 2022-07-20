package com.isozcelik.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="todolist")
public class ToDoListEntity extends BaseEntity {

    @Column(name="note")
    private String note;

    @Column(name="status")
    private String status;
}
