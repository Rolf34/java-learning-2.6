package company.storage;

import company.empoloyees.Employee;
import company.projects.Project;
import java.util.Collection;
import java.util.Objects;


class CompanyEntityManager {

    CompanyEntityManager() {}

    void addEmployee(Employee employee) {
        validateEmployee(employee);
        CompanyDataStore.addEmployee(employee);
    }

    Employee findEmployee(String employeeId) {
        return CompanyDataStore.findEmployee(employeeId);
    }

    Collection<Employee> getAllEmployees() {
        return CompanyDataStore.getAllEmployees().values();
    }
    void addProject(Project project) {
        validateProject(project);
        CompanyDataStore.addProject(project);
    }

    Project findProject(String projectId) {
        return CompanyDataStore.findProject(projectId);
    }

    Collection<Project> getAllProjects() {
        return CompanyDataStore.getAllProjects().values();
    }

    private void validateEmployee(Employee employee) {
        Objects.requireNonNull(employee, "Employee cannot be null");
        if (CompanyDataStore.EMPLOYEES.containsKey(employee.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with ID " + employee.getEmployeeId() + " already exists");
        }
    }

    private void validateProject(Project project) {
        Objects.requireNonNull(project, "Project cannot be null");
        if (CompanyDataStore.PROJECTS.containsKey(project.getId())) {
            throw new IllegalArgumentException("Project with ID " + project.getId() + " already exists");
        }
    }
}