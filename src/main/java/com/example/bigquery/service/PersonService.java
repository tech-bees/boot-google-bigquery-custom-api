package com.example.bigquery.service;

import com.example.bigquery.client.BigQueryClient;
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
public class PersonService {

    @Autowired
    private BigQueryClient bigQueryClient;

    @Value("${google.cloud.project-id}")
    private String projectId;

    @Value("${google.cloud.person.dataset-id}")
    private String personDatasetId;

    @Value("${google.cloud.department.dataset-id}")
    private String departmentDatasetId;

    private String personTableName = null;

    private String departmentTableName = null;

    @PostConstruct
    private void init(){
        this.personTableName = String.format("%s.%s.person",projectId,personDatasetId);
        this.departmentTableName = String.format("%s.%s.department",projectId,departmentDatasetId);
    }

    public List<PersonDetails> getAllPersonDetails(){
        String query = String.format("Select p.id, p.name, p.email, p.age, d.name as department, d.description from %s p left join %s d on p.department_id=d.id",personTableName, departmentTableName);
        TableResult result = bigQueryClient.query(query);
        List<PersonDetails> persons = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            String id = row.get("id").getStringValueOrDefault("");
            String name = row.get("name").getStringValueOrDefault("");
            String email = row.get("email").getStringValueOrDefault("");
            Integer age = Integer.valueOf(row.get("age").getStringValueOrDefault("0"));
            String department = row.get("department").getStringValueOrDefault("");
            String description = row.get("description").getStringValueOrDefault("");
            PersonDetails person = PersonDetails.builder()
                    .id(id)
                    .name(name)
                    .email(email)
                    .department(department)
                    .description(description)
                    .age(age)
                    .build();
            persons.add(person);
        });
        return persons;
    }

    public List<Person> getAllPerson(){
        String query = String.format("Select id, name, email, age from %s ",personTableName);
        TableResult result = bigQueryClient.query(query);
        List<Person> persons = new ArrayList<>();
        result.iterateAll().forEach(row -> {
            String id = row.get("id").getStringValueOrDefault("");
            String name = row.get("name").getStringValueOrDefault("");
            String email = row.get("email").getStringValueOrDefault("");
            Integer age = Integer.valueOf(row.get("age").getStringValueOrDefault("0"));
            Person person = Person.builder().id(id).name(name).email(email).age(age).build();
            persons.add(person);
        });
        return persons;
    }

    public Person findPersonById(String personId){
        String query = String.format("Select id, name, email, age from %s where id='%s'",personTableName,personId);
        TableResult result = bigQueryClient.query(query);
        AtomicReference<Person> person = new AtomicReference<>();
        result.iterateAll().forEach(row -> {
            String id = row.get("id").getStringValueOrDefault("");
            String name = row.get("name").getStringValueOrDefault("");
            String email = row.get("email").getStringValueOrDefault("");
            Integer age = Integer.valueOf(row.get("age").getStringValueOrDefault("0"));
            person.set(Person.builder().id(id).name(name).email(email).age(age).build());
        });
        return person.get();
    }

    public void savePerson(Person person){
        person.setId(UUID.randomUUID().toString().replace("-",""));
        String query = String.format("INSERT INTO %s (id, name, email, age) VALUES('%s', '%s','%s', %d)",
                personTableName, person.getId(),person.getName(),person.getEmail(), person.getAge());
        bigQueryClient.query(query);
    }

    public void updatePerson(Person person){
        String query = String.format("UPDATE %s SET name='%s', email='%s', age=%d where id='%s'",
                personTableName,person.getName(),person.getEmail(), person.getAge(), person.getId());
        bigQueryClient.query(query);
    }

    public void deletePerson(String personId){
        String query = String.format("DELETE from %s where id='%s'",personTableName,personId);
        bigQueryClient.query(query);
    }


}

