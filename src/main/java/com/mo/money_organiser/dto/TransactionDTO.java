package com.mo.money_organiser.dto;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TransactionDTO {
    private String type;
    private Double amount;
    private String category;
    private LocalDate date;
}