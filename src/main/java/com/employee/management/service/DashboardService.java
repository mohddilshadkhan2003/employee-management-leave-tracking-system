package com.employee.management.service;

import com.employee.management.repository.EmployeeRepository;
import com.employee.management.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {
    private final EmployeeRepository employeeRepository;
    private final LeaveRepository leaveRepository;

    public DashboardService(EmployeeRepository employeeRepository, LeaveRepository leaveRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRepository = leaveRepository;
    }

    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalEmployees", employeeRepository.findAll().size());
        summary.put("pendingLeaveRequests", leaveRepository.countPending());
        return summary;
    }
}
