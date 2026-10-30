package com.employee.management.repository;

import com.employee.management.model.LeaveRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class LeaveRepository {
    private final JdbcTemplate jdbcTemplate;

    public LeaveRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<LeaveRequest> rowMapper = new RowMapper<LeaveRequest>() {
        @Override
        public LeaveRequest mapRow(ResultSet rs, int rowNum) throws SQLException {
            LeaveRequest leaveRequest = new LeaveRequest();
            leaveRequest.setId(rs.getInt("id"));
            leaveRequest.setEmployeeId(rs.getInt("employee_id"));
            leaveRequest.setLeaveType(rs.getString("leave_type"));
            Date startDate = rs.getDate("start_date");
            Date endDate = rs.getDate("end_date");
            leaveRequest.setStartDate(startDate != null ? startDate.toLocalDate() : null);
            leaveRequest.setEndDate(endDate != null ? endDate.toLocalDate() : null);
            leaveRequest.setStatus(rs.getString("status"));
            leaveRequest.setReason(rs.getString("reason"));
            return leaveRequest;
        }
    };

    public int save(LeaveRequest leaveRequest) {
        String sql = "INSERT INTO leave_requests (employee_id, leave_type, start_date, end_date, status, reason) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, leaveRequest.getEmployeeId());
            ps.setString(2, leaveRequest.getLeaveType());
            ps.setDate(3, Date.valueOf(leaveRequest.getStartDate()));
            ps.setDate(4, Date.valueOf(leaveRequest.getEndDate()));
            ps.setString(5, leaveRequest.getStatus() != null ? leaveRequest.getStatus() : "PENDING");
            ps.setString(6, leaveRequest.getReason());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }

    public List<LeaveRequest> findAll() {
        return jdbcTemplate.query("SELECT * FROM leave_requests ORDER BY id DESC", rowMapper);
    }

    public List<LeaveRequest> findByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query("SELECT * FROM leave_requests WHERE employee_id = ? ORDER BY id DESC", rowMapper, employeeId);
    }

    public Optional<LeaveRequest> findById(Integer id) {
        List<LeaveRequest> requests = jdbcTemplate.query("SELECT * FROM leave_requests WHERE id = ?", rowMapper, id);
        return requests.isEmpty() ? Optional.empty() : Optional.of(requests.get(0));
    }

    public int updateStatus(Integer id, String status) {
        return jdbcTemplate.update("UPDATE leave_requests SET status = ? WHERE id = ?", status, id);
    }

    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM leave_requests WHERE id = ?", id);
    }

    public long countPending() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM leave_requests WHERE status = 'PENDING'", Long.class);
    }
}
