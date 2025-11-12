package company.structure;

import company.empoloyees.Employee;
import company.empoloyees.Manager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Department extends OrganizationalUnit {
    private Manager manager;
    private Department parentDepartment;
    private final List<Department> subDepartments;
    private final List<Employee> employees;
    private double budget;

    public Department(String code, String name, String description) {
        super(code, name, description);
        this.subDepartments = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.budget = 0.0;
    }

    public void setManager(Manager manager) {
        this.manager = Objects.requireNonNull(manager, "Manager cannot be null");
        if (!employees.contains(manager)) {
            employees.add(manager);
        }
    }

    void addEmployee(Employee employee) {
        Objects.requireNonNull(employee, "Employee cannot be null");
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    void removeEmployee(Employee employee) {
        if (employee.equals(manager)) {
            throw new IllegalStateException("Cannot remove department manager");
        }
        employees.remove(employee);
    }

    @Override
    public String getType() {
        return "Department";
    }

    @Override
    public void activate() {
        if (!isActive) {
            isActive = true;
            updateLastModified();
        }
    }

    @Override
    public void deactivate() {
        if (isActive) {
            isActive = false;
            updateLastModified();
   
            employees.forEach(employee -> {
                if (employee.isActive()) {
                    employee.deactivate();
                }
            });
        }
    }


    public Manager getManager() { return manager; }
    public List<Employee> getEmployees() { return Collections.unmodifiableList(employees); }
    public double getBudget() { return budget; }
    public int getEmployeeCount() { return employees.size(); }

    public void setBudget(double newBudget) {
        if (newBudget < 0) {
            throw new IllegalArgumentException("Budget cannot be negative");
        }
        this.budget = newBudget;
        updateLastModified();
    }

    public double getTotalSalaries() {
        return employees.stream()
                       .mapToDouble(Employee::getSalary)
                       .sum();
    }

    public boolean isWithinBudget() {
        return getTotalSalaries() <= budget;
    }

    @Override
    public String toString() {
        return String.format("Department{id='%s', name='%s', manager=%s, employees=%d, budget=%.2f}",
                getCode(), getName(), 
                (manager != null ? manager.getFullName() : "None"),
                employees.size(), budget);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return getCode().equals(that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
}
