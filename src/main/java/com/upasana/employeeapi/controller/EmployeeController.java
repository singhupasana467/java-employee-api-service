package com.upasana.employeeapi.controller;

import com.upasana.employeeapi.model.Employee;
import com.upasana.employeeapi.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping
    public ResponseEntity<Employee> create(@Valid @RequestBody Employee emp){
        return new ResponseEntity<>(service.create(emp), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

