package com.huandemberg.EBStore.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DateRangeDTO {

    @NotBlank
    @Size(min = 10, max = 15)
    private String startDate;

    @NotBlank
    @Size(min = 10, max = 15)
    private String endDate;
    
}
