const API_BASE = '/api';

function safeText(value) {
    return (value == null ? '' : String(value)).replace(/</g, '&lt;').replace(/>/g, '&gt;');
}

function showMessage(elementId, message, isSuccess = false) {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.textContent = message;
    el.classList.toggle('success', isSuccess);
}

async function apiFetch(url, options = {}) {
    const response = await fetch(url, options);
    const data = await response.json().catch(() => ({}));
    if (!response.ok) {
        throw new Error(data.message || 'Request failed');
    }
    return data;
}

function logoutUser() {
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    window.location.href = '/index.html';
}

function statusClass(status) {
    const normalized = (status || '').toUpperCase();
    if (normalized === 'APPROVED') return 'status-approved';
    if (normalized === 'REJECTED') return 'status-rejected';
    return 'status-pending';
}

function formatCurrency(value) {
    const number = Number(value || 0);
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        maximumFractionDigits: 0
    }).format(number);
}

function renderEmployeeTable(rows) {
    const tbody = document.getElementById('employeeTableBody');
    if (!tbody) return;

    if (!Array.isArray(rows) || rows.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6">No employees found.</td></tr>';
        return;
    }

    tbody.innerHTML = rows.map(employee => `
        <tr>
            <td>${safeText(employee.id)}</td>
            <td>${safeText(employee.name)}</td>
            <td>${safeText(employee.email)}</td>
            <td>${safeText(employee.phone || '-')}</td>
            <td>${safeText(formatCurrency(employee.salary))}</td>
            <td>
                <div class="actions">
                    <button class="danger" data-delete-id="${safeText(employee.id)}">Delete</button>
                </div>
            </td>
        </tr>
    `).join('');

    tbody.querySelectorAll('[data-delete-id]').forEach(button => {
        button.addEventListener('click', async () => {
            const id = button.getAttribute('data-delete-id');
            try {
                await apiFetch(`${API_BASE}/employees/${id}`, { method: 'DELETE' });
                await loadEmployees();
                await loadDashboard();
                showMessage('employeeMessage', 'Employee removed successfully.', true);
            } catch (error) {
                showMessage('employeeMessage', error.message, false);
            }
        });
    });
}

function renderLeaveTable(rows) {
    const tbody = document.getElementById('leaveTableBody');
    if (!tbody) return;

    if (!Array.isArray(rows) || rows.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8">No leave requests available.</td></tr>';
        return;
    }

    tbody.innerHTML = rows.map(leave => `
        <tr>
            <td>${safeText(leave.id)}</td>
            <td>${safeText(leave.employeeId)}</td>
            <td>${safeText(leave.leaveType)}</td>
            <td>${safeText(leave.startDate)}</td>
            <td>${safeText(leave.endDate)}</td>
            <td><span class="status-pill ${statusClass(leave.status)}">${safeText(leave.status || 'PENDING')}</span></td>
            <td>${safeText(leave.reason || '-')}</td>
            <td>
                <div class="actions">
                    <button class="success" data-action="approve" data-id="${safeText(leave.id)}">Approve</button>
                    <button class="danger" data-action="reject" data-id="${safeText(leave.id)}">Reject</button>
                </div>
            </td>
        </tr>
    `).join('');

    tbody.querySelectorAll('[data-action]').forEach(button => {
        button.addEventListener('click', async () => {
            const id = button.getAttribute('data-id');
            const action = button.getAttribute('data-action');
            const status = action === 'approve' ? 'APPROVED' : 'REJECTED';
            try {
                await apiFetch(`${API_BASE}/leaves/${id}/status?status=${status}`, { method: 'PUT' });
                await loadLeaves();
                await loadDashboard();
                showMessage('employeeMessage', `Leave request ${status.toLowerCase()} successfully.`, true);
            } catch (error) {
                showMessage('employeeMessage', error.message, false);
            }
        });
    });
}

async function loadDashboard() {
    const total = document.getElementById('totalEmployees');
    const pending = document.getElementById('pendingLeaves');
    const approved = document.getElementById('approvedLeaves');
    const rejected = document.getElementById('rejectedLeaves');

    try {
        const data = await apiFetch(`${API_BASE}/dashboard`);
        if (total) total.textContent = data.totalEmployees ?? 0;
        if (pending) pending.textContent = data.pendingLeaveRequests ?? 0;
        if (approved) approved.textContent = data.approvedLeaveRequests ?? 0;
        if (rejected) rejected.textContent = data.rejectedLeaveRequests ?? 0;
    } catch (error) {
        console.error(error);
    }
}

async function loadEmployees() {
    try {
        const employees = await apiFetch(`${API_BASE}/employees`);
        renderEmployeeTable(employees);
    } catch (error) {
        console.error(error);
    }
}

async function loadLeaves() {
    try {
        const leaves = await apiFetch(`${API_BASE}/leaves`);
        renderLeaveTable(leaves);
    } catch (error) {
        console.error(error);
    }
}

async function loadDepartments() {
    const select = document.getElementById('departmentId');
    if (!select) return;

    try {
        const departments = await apiFetch(`${API_BASE}/departments`);
        select.innerHTML = '<option value="">Select Department</option>' + departments.map(dep => 
            `<option value="${dep.id}">${safeText(dep.departmentName)}</option>`
        ).join('');
    } catch (error) {
        console.error(error);
    }
}

function bindSearch() {
    const search = document.getElementById('employeeSearch');
    if (!search) return;

    search.addEventListener('input', async (event) => {
        const keyword = event.target.value.trim();
        try {
            const employees = await apiFetch(`${API_BASE}/employees/search?keyword=${encodeURIComponent(keyword)}`);
            renderEmployeeTable(employees);
        } catch (error) {
            console.error(error);
        }
    });
}

function bindEmployeeForm() {
    const form = document.getElementById('employeeForm');
    if (!form) return;

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const payload = {
            name: document.getElementById('name').value.trim(),
            email: document.getElementById('email').value.trim(),
            phone: document.getElementById('phone').value.trim(),
            departmentId: document.getElementById('departmentId').value,
            salary: Number(document.getElementById('salary').value)
        };

        try {
            await apiFetch(`${API_BASE}/employees`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            showMessage('employeeMessage', 'Employee added successfully.', true);
            form.reset();
            await loadEmployees();
            await loadDashboard();
        } catch (error) {
            showMessage('employeeMessage', error.message, false);
        }
    });
}

function setupEmployeePage() {
    const leaveForm = document.getElementById('leaveForm');
    const leaveMessage = document.getElementById('leaveMessage');

    if (!leaveForm) return;

    leaveForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const payload = {
            employeeId: Number(document.getElementById('employeeId').value),
            leaveType: document.getElementById('leaveType').value,
            startDate: document.getElementById('startDate').value,
            endDate: document.getElementById('endDate').value,
            reason: document.getElementById('reason').value
        };

        try {
            await apiFetch(`${API_BASE}/leaves`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (leaveMessage) {
                leaveMessage.textContent = 'Leave request submitted successfully';
                leaveMessage.classList.add('success');
            }
            leaveForm.reset();
            loadEmployeeLeaves(payload.employeeId);
        } catch (error) {
            if (leaveMessage) {
                leaveMessage.textContent = error.message;
                leaveMessage.classList.remove('success');
            }
        }
    });

    const employeeIdInput = document.getElementById('employeeId');
    if (employeeIdInput) {
        employeeIdInput.addEventListener('change', () => {
            loadEmployeeLeaves(Number(employeeIdInput.value));
        });
    }
}

async function loadEmployeeLeaves(employeeId) {
    const table = document.getElementById('employeeLeaveTable');
    if (!table || !employeeId) return;

    try {
        const rows = await apiFetch(`${API_BASE}/employees/${employeeId}/leaves`);
        table.innerHTML = rows.map(leave => `
            <tr>
                <td>${safeText(leave.leaveType)}</td>
                <td>${safeText(leave.startDate)}</td>
                <td>${safeText(leave.endDate)}</td>
                <td><span class="status-pill ${statusClass(leave.status)}">${safeText(leave.status || 'PENDING')}</span></td>
            </tr>
        `).join('');
    } catch (error) {
        table.innerHTML = '<tr><td colspan="4">No records found</td></tr>';
    }
}

function setupDepartmentPage() {
    const form = document.getElementById('departmentForm');
    const table = document.getElementById('departmentTableBody');
    if (!form || !table) return;

    const renderDepartments = (departments) => {
        if (!Array.isArray(departments) || departments.length === 0) {
            table.innerHTML = '<tr><td colspan="2">No departments available.</td></tr>';
            return;
        }

        table.innerHTML = departments.map(dep => `
            <tr>
                <td>${safeText(dep.id)}</td>
                <td>${safeText(dep.departmentName)}</td>
            </tr>
        `).join('');
    };

    const refreshDepartments = async () => {
        const departments = await apiFetch(`${API_BASE}/departments`);
        renderDepartments(departments);
    };

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const input = document.getElementById('departmentName');
        const message = document.getElementById('departmentMessage');

        try {
            await apiFetch(`${API_BASE}/departments`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ departmentName: input.value.trim() })
            });

            if (message) {
                message.textContent = 'Department added successfully';
                message.classList.add('success');
            }
            form.reset();
            await refreshDepartments();
        } catch (error) {
            if (message) {
                message.textContent = error.message;
                message.classList.remove('success');
            }
        }
    });

    refreshDepartments();
}

function attachLogoutHandlers() {
    document.querySelectorAll('[data-logout]').forEach((button) => {
        button.addEventListener('click', logoutUser);
    });
}

function initializePage() {
    const username = localStorage.getItem('username');
    const badge = document.getElementById('userBadge');
    if (badge) {
        badge.textContent = username ? `Welcome, ${username}` : 'Welcome';
    }
    attachLogoutHandlers();
}

document.addEventListener('DOMContentLoaded', () => {
    initializePage();

    const role = localStorage.getItem('role');

    if (document.getElementById('employeeForm')) {
        if (role !== 'ADMIN') {
            window.location.href = '/index.html';
            return;
        }
        loadDashboard();
        loadEmployees();
        loadLeaves();
        loadDepartments();
        bindSearch();
        bindEmployeeForm();
    }

    if (document.getElementById('leaveForm')) {
        if (role === 'ADMIN') {
            window.location.href = '/admin.html';
            return;
        }
        setupEmployeePage();
    }

    if (document.getElementById('departmentForm')) {
        if (role !== 'ADMIN') {
            window.location.href = '/index.html';
            return;
        }
        setupDepartmentPage();
    }
});
