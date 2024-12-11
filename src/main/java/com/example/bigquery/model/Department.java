package com.example.bigquery.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    private Integer id;
    private String name;
    private String description;
}
