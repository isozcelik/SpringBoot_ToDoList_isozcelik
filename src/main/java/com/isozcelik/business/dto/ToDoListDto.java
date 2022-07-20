package com.isozcelik.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ToDoListDto {
    private long id;

    @NotEmpty(message="Not Girmediniz")
    private String note;

    private String status;
}
