package com.example.bigquery.controller;

import com.example.bigquery.model.Person;
import com.example.bigquery.model.PersonDetails;
import com.example.bigquery.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public String savePerson(@RequestBody Person person){
        personService.savePerson(person);
        return "Person saved successfully!";
    }

    @PutMapping
    public String updatePerson(@RequestBody Person person){
        personService.updatePerson(person);
        return "Person updated successfully!";
    }

    @DeleteMapping("/{personId}")
    public String deletePerson(@PathVariable String personId){
        personService.deletePerson(personId);
        return "Person delete successfully!";
    }

    @GetMapping("/{personId}")
    public Person findByPersonId(@PathVariable String personId){
        return personService.findPersonById(personId);
    }

    @GetMapping
    public List<Person> fetchAllPersons(){
        return personService.getAllPerson();
    }


    @GetMapping("/details")
    public List<PersonDetails> fetchAllPersonDetails(){
        return personService.getAllPersonDetails();
    }
}
