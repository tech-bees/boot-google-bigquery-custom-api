package com.example.bigquery.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDetails {

    private String id;
    private String name;
    private String email;
    private Integer age;
    private String department;
    private String description;
}