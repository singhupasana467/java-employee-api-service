package com.upasana.employeeapi.service;

import com.upasana.employeeapi.model.Employee;
import com.upasana.employeeapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {
    private final Map<Long, Employee> store = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();
    
    public Employee create(Employee emp){
        emp.setId(idCounter.incrementAndGet());
        store.put(emp.getId(), emp);
        return emp; 
    }

    public List<Employee> getAll(){
        return new ArrayList<>(store.values());
    }

    public Employee getById(Long id){
        return Optional.ofNullable(store.get(id))
            .orElseThrow(() -> new ResourceNotFoundException("Employee Not found"));
    }

    public void delete(Long id){
        store.remove(id);   
    }
}
