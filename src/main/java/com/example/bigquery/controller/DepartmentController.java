package com.example.bigquery.controller;

import com.example.bigquery.model.Department;
import com.example.bigquery.model.Person;
import com.example.bigquery.model.PersonDetails;
import com.example.bigquery.service.DepartmentService;
import com.example.bigquery.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    @GetMapping("/{departmentId}")
    public Department findByDepartmentId(@PathVariable String departmentId){
        return departmentService.findDepartmentById(departmentId);
    }

    @GetMapping
    public List<Department> fetchAllPersons(){
        return departmentService.getAllDepartments();
    }

}
