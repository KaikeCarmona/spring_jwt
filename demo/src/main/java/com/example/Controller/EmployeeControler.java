package com.example.Controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Model.Employee;
import com.example.Service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/employee")
public class EmployeeControler {

    @Autowired
    private EmployeeService service;

    @PostMapping(value = "/post", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee post(@RequestBody Employee newEmployee) {
        Employee employee = new Employee();
        try {
            BeanUtils.copyProperties(newEmployee, employee);
            return service.create(employee);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        try {
            Employee employee = service.findById(id);
            return ResponseEntity.ok(employee); // 200 OK com o objeto
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee whith id: " + id + " not founded"); // 400 Bad Request
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<Employee> employees = service.findAll();

            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No employees found"); // 404 Not Found
            }
            return ResponseEntity.ok(employees); // 200 OK com a lista de objetos
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Erro ao buscar usuarios: " + e.getMessage()); // 400 Bad Request
        }
    }

    @PutMapping(value = "update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody Employee employeeUpdated) {
        Employee employee = new Employee();
        try {
            BeanUtils.copyProperties(employeeUpdated, employee);
            return ResponseEntity.ok(service.update(id, employee)); // 200 OK com o objeto atualizado
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee whith id: " + id + " not founded"); // 400 Bad Request
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok("Employee with id: " + id + " deleted"); // 200 OK
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Employee whith id: " + id + " not founded"); // 400 Bad Request
        }
    }

}
