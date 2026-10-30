package com.employee.management.repository;

import com.employee.management.model.Department;
import com.employee.management.model.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Employee> employeeRowMapper = new RowMapper<Employee>() {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setName(rs.getString("name"));
            employee.setEmail(rs.getString("email"));
            employee.setPhone(rs.getString("phone"));
            employee.setDepartmentId(rs.getObject("department_id") == null ? null : rs.getInt("department_id"));
            employee.setSalary(rs.getDouble("salary"));
            employee.setCreatedAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null);
            employee.setUpdatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null);
            return employee;
        }
    };

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employees ORDER BY id DESC", employeeRowMapper);
    }

    public Optional<Employee> findById(Integer id) {
        String sql = "SELECT * FROM employees WHERE id = ?";
        List<Employee> employees = jdbcTemplate.query(sql, employeeRowMapper, id);
        return employees.isEmpty() ? Optional.empty() : Optional.of(employees.get(0));
    }

    public Optional<Employee> findByEmail(String email) {
        String sql = "SELECT * FROM employees WHERE email = ?";
        List<Employee> employees = jdbcTemplate.query(sql, employeeRowMapper, email);
        return employees.isEmpty() ? Optional.empty() : Optional.of(employees.get(0));
    }

    public List<Employee> searchByNameOrId(String keyword) {
        String sql = "SELECT * FROM employees WHERE name LIKE ? OR CAST(id AS CHAR) LIKE ? ORDER BY id DESC";
        String pattern = "%" + keyword + "%";
        return jdbcTemplate.query(sql, employeeRowMapper, pattern, pattern);
    }

    public int save(Employee employee) {
        String sql = "INSERT INTO employees (name, email, phone, department_id, salary) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPhone());
            if (employee.getDepartmentId() != null) {
                ps.setInt(4, employee.getDepartmentId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            ps.setDouble(5, employee.getSalary() != null ? employee.getSalary() : 0.0);
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.intValue() : 0;
    }

    public int update(Employee employee) {
        String sql = "UPDATE employees SET name = ?, email = ?, phone = ?, department_id = ?, salary = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        return jdbcTemplate.update(sql,
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartmentId(),
                employee.getSalary(),
                employee.getId());
    }

    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM employees WHERE id = ?", id);
    }
}
