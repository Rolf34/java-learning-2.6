package company.storage;

import company.empoloyees.Employee;
import company.projects.Project;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class CompanyDataStore {
    // Public static maps - demonstrate static storage of application entities
    public static final Map<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();
    public static final Map<String, Project> PROJECTS = new ConcurrentHashMap<>();

    private CompanyDataStore() {

    }

    public static void addEmployee(Employee e) {
        EMPLOYEES.put(e.getEmployeeId(), e);
    }

    public static Employee findEmployee(String id) {
        return EMPLOYEES.get(id);
    }

    public static Map<String, Employee> getAllEmployees() {
        return Collections.unmodifiableMap(EMPLOYEES);
    }

    public static void addProject(Project p) {
        PROJECTS.put(p.getId(), p);
    }

    public static Project findProject(String id) {
        return PROJECTS.get(id);
    }

    public static Map<String, Project> getAllProjects() {
        return Collections.unmodifiableMap(PROJECTS);
    }
}
