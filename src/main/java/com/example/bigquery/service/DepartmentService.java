package com.example.bigquery.service;

import com.example.bigquery.client.BigQueryClient;
import com.example.bigquery.model.Department;
import com.example.bigquery.model.Person;
import com.example.bigquery.model.PersonDetails;
import com.google.cloud.bigquery.TableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DepartmentService {

    @Autowired
    private BigQueryClient bigQueryClient;

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("${google.cloud.department.dataset-id}")
    private String departmentDatasetId;

    private String departmentTableName = null;

    @PostConstruct
    private void init(){
        this.departmentTableName = String.format("%s.%s.department",projectId,departmentDatasetId);
    }

    public List<Department> getAllDepartments(){
        String query = String.format("Select id, name, description from %s ", departmentTableName);
        TableResult result = bigQueryClient.query(query);
        List<Department> departments = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            Integer id = Integer.valueOf(row.get("id").getStringValueOrDefault("0"));
            String name = row.get("name").getStringValueOrDefault("");
            String description = row.get("description").getStringValueOrDefault("");
            Department department = Department.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .build();
            departments.add(department);
        });
        return departments;
    }

    public Department findDepartmentById(String did){
        String query = String.format("Select id, name, description from %s where id=%s",departmentTableName,did);
        TableResult result = bigQueryClient.query(query);
        AtomicReference<Department> department = new AtomicReference<>();
        result.iterateAll().forEach(row -> {
            Integer id = Integer.valueOf(row.get("id").getStringValueOrDefault("0"));
            String name = row.get("name").getStringValueOrDefault("");
            String description = row.get("description").getStringValueOrDefault("");
            department.set(Department.builder().id(id).name(name).description(description).build());
        });
        return department.get();
    }

}

