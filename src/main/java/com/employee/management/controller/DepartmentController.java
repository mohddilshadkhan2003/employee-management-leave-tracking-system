package com.employee.management.controller;

import com.employee.management.model.Department;
import com.employee.management.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping("/departments")
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @PostMapping("/departments")
    public ResponseEntity<?> createDepartment(@RequestBody Map<String, String> payload) {
        String departmentName = payload.get("departmentName");
        if (departmentName == null || departmentName.trim().isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Department name is required");
            return ResponseEntity.badRequest().body(error);
        }

        int id = departmentRepository.save(departmentName.trim());
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("departmentName", departmentName.trim());
        return ResponseEntity.ok(response);
    }
}
