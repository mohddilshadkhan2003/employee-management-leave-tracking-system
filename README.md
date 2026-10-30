const API_BASE = '/api';

async function apiFetch(url, options = {}) {
    const response = await fetch(url, options);
    const data = await response.json().catch(() => ({}));
    if (!response.ok) {
        throw new Error(data.message || 'Request failed');
    }
    return data;
}

function statusClass(status) {
    const normalized = (status || '').toUpperCase();
    if (normalized === 'APPROVED') return 'status-approved';
    if (normalized === 'REJECTED') return 'status-rejected';
    return 'status-pending';
}

function renderEmployeeTable(rows) {
    const tbody = document.getElementById('employeeTableBody');
    if (!tbody) return;

    tbody.innerHTML = rows.map(employee => `
        <tr>
            <td>${employee.id}</td>
            <td>${employee.name}</td>
            <td>${employee.email}</td>
            <td>${employee.phone || '-'}</td>
            <td>${employee.salary ?? 0}</td>
            <td>
                <div class="actions">
                    <button class="danger" data-delete-id="${employee.id}">Delete</button>
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
            } catch (error) {
                alert(error.message);
            }
        });
    });
}

function renderLeaveTable(rows) {
    const tbody = document.getElementById('leaveTableBody');
    if (!tbody) return;

    tbody.innerHTML = rows.map(leave => `
        <tr>
            <td>${leave.id}</td>
            <td>${leave.employeeId}</td>
            <td>${leave.leaveType}</td>
            <td>${leave.startDate}</td>
            <td>${leave.endDate}</td>
            <td><span class="status-pill ${statusClass(leave.status)}">${leave.status || 'PENDING'}</span></td>
            <td>${leave.reason || '-'}</td>
            <td>
                <div class="actions">
                    <button class="success" data-action="approve" data-id="${leave.id}">Approve</button>
                    <button class="danger" data-action="reject" data-id="${leave.id}">Reject</button>
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
            } catch (error) {
                alert(error.message);
            }
        });
    });
}

async function loadDashboard() {
    const summary = document.getElementById('totalEmployees');
    const pending = document.getElementById('pendingLeaves');
    const approved = document.getElementById('approvedLeaves');

    if (!summary || !pending || !approved) return;

    try {
        const data = await apiFetch(`${API_BASE}/dashboard`);
        summary.textContent = data.totalEmployees ?? 0;
        pending.textContent = data.pendingLeaveRequests ?? 0;
        approved.textContent = data.approvedLeaveRequests ?? 0;
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
        if (!departments.length) {
            const option = document.createElement('option');
            option.textContent = 'No departments available';
            option.value = '';
            select.appendChild(option);
            return;
        }

        select.innerHTML = '<option value="">Select Department</option>' + departments.map(dep => 
            `<option value="${dep.id}">${dep.departmentName}</option>`
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
        const employees = await apiFetch(`${API_BASE}/employees/search?keyword=${encodeURIComponent(keyword)}`);
        renderEmployeeTable(employees);
    });
}

function bindEmployeeForm() {
    const form = document.getElementById('employeeForm');
    if (!form) return;

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const message = document.getElementById('employeeMessage');

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

            message.textContent = 'Employee added successfully';
            message.classList.add('success');
            form.reset();
            await loadEmployees();
            await loadDashboard();
        } catch (error) {
            message.textContent = error.message;
            message.classList.remove('success');
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

            leaveMessage.textContent = 'Leave request submitted successfully';
            leaveMessage.classList.add('success');
            leaveForm.reset();
            loadEmployeeLeaves(payload.employeeId);
        } catch (error) {
            leaveMessage.textContent = error.message;
            leaveMessage.classList.remove('success');
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
                <td>${leave.leaveType}</td>
                <td>${leave.startDate}</td>
                <td>${leave.endDate}</td>
                <td><span class="status-pill ${statusClass(leave.status)}">${leave.status || 'PENDING'}</span></td>
            </tr>
        `).join('');
    } catch (error) {
        table.innerHTML = '<tr><td colspan="4">No records found</td></tr>';
    }
}
