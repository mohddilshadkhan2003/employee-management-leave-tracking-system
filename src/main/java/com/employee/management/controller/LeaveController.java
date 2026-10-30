package com.employee.management.controller;

import com.employee.management.dto.LeaveRequestDto;
import com.employee.management.model.LeaveRequest;
import com.employee.management.service.LeaveService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/leaves")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequestDto request) {
        try {
            LeaveRequest saved = leaveService.applyLeave(request);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @GetMapping("/leaves")
    public List<LeaveRequest> getAllLeaves() {
        return leaveService.getAllLeaves();
    }

    @GetMapping("/leaves/{id}")
    public ResponseEntity<?> getLeaveById(@PathVariable Integer id) {
        return leaveService.getLeaveById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/employees/{employeeId}/leaves")
    public List<LeaveRequest> getLeavesByEmployeeId(@PathVariable Integer employeeId) {
        return leaveService.getLeavesByEmployeeId(employeeId);
    }

    @PutMapping("/leaves/{id}/status")
    public ResponseEntity<?> updateLeaveStatus(@PathVariable Integer id, @RequestParam String status) {
        try {
            return ResponseEntity.ok(leaveService.updateLeaveStatus(id, status));
        } catch (IllegalArgumentException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("message", ex.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/leaves/{id}")
    public ResponseEntity<Map<String, String>> deleteLeave(@PathVariable Integer id) {
        boolean deleted = leaveService.deleteLeave(id);
        Map<String, String> response = new HashMap<>();
        if (deleted) {
            response.put("message", "Leave request deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("message", "Leave request not found");
        return ResponseEntity.notFound().build();
    }
}
