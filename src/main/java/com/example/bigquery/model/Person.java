package com.example.bigquery.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    private String id;
    private String name;
    private String email;
    private Integer age;
}
