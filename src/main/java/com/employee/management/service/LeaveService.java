package com.employee.management.service;

import com.employee.management.dto.LeaveRequestDto;
import com.employee.management.model.LeaveRequest;
import com.employee.management.repository.LeaveRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;

    public LeaveService(LeaveRepository leaveRepository) {
        this.leaveRepository = leaveRepository;
    }

    public LeaveRequest applyLeave(LeaveRequestDto request) {
        if (request.getStartDate() == null || request.getEndDate() == null) {
            throw new IllegalArgumentException("Leave start and end date are required");
        }

        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(request.getEmployeeId());
        leaveRequest.setLeaveType(request.getLeaveType());
        leaveRequest.setStartDate(request.getStartDate());
        leaveRequest.setEndDate(request.getEndDate());
        leaveRequest.setReason(request.getReason());
        leaveRequest.setStatus("PENDING");

        int id = leaveRepository.save(leaveRequest);
        leaveRequest.setId(id);
        return leaveRequest;
    }

    public List<LeaveRequest> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public List<LeaveRequest> getLeavesByEmployeeId(Integer employeeId) {
        return leaveRepository.findByEmployeeId(employeeId);
    }

    public Optional<LeaveRequest> getLeaveById(Integer id) {
        return leaveRepository.findById(id);
    }

    public LeaveRequest updateLeaveStatus(Integer id, String status) {
        LeaveRequest leave = leaveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found"));

        if (!"APPROVED".equalsIgnoreCase(status) && !"REJECTED".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException("Status must be APPROVED or REJECTED");
        }

        leaveRepository.updateStatus(id, status.toUpperCase());
        leave.setStatus(status.toUpperCase());
        return leave;
    }

    public boolean deleteLeave(Integer id) {
        return leaveRepository.deleteById(id) > 0;
    }
}
