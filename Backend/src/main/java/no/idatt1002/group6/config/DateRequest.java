package no.idatt1002.group6.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateRequest {

    // Formatted yyyy-MM-dd
    private String date;
}
