package com.example.Service;

import java.util.List;
import java.util.Objects;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Model.Employee;
import com.example.Repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Employee create(Employee employee) {
        String encodedPassword = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(encodedPassword);
        return employeeRepository.save(employee);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Transactional
    public Employee update(Long id, Employee employee) {
        var employeeFounded = findById(id);
        if (!Objects.equals(employeeFounded.getId(), id)) {
            return employeeFounded;
        }
        BeanUtils.copyProperties(employee, employeeFounded, "id");
        return employeeRepository.save(employeeFounded);
    }

    @Transactional
    public void delete(Long id) {
        try {
            employeeRepository.deleteById(id);
        } catch (ObjectNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
