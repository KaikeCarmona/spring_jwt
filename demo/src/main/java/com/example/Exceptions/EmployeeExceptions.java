package com.example.Exceptions;

public class EmployeeExceptions extends RuntimeException {
    public EmployeeExceptions(Long id) {
        super("Could not find employee " + id);
    }
}
