package company.storage;

import company.empoloyees.Employee;
import company.projects.Project;
import company.empoloyees.Manager;
import java.util.*;


public class CompanyDataService {
    private static final CompanyDataService instance = new CompanyDataService();
    private final CompanyEntityManager entityManager;
    
    private CompanyDataService() {
        this.entityManager = new CompanyEntityManager();
    }
    
    public static CompanyDataService getInstance() {
        return instance;
    }
    
    // Public methods for employee management
    public void registerEmployee(Employee employee) {
        entityManager.addEmployee(employee);
    }
    
    public Employee lookupEmployee(String employeeId) {
        return entityManager.findEmployee(employeeId);
    }
    
    public List<Employee> findEmployeesByDepartment(String departmentName) {
        return entityManager.getAllEmployees().stream()
            .filter(e -> e.getDepartment().getName().equals(departmentName))
            .toList();
    }
    
    // Project management methods with business logic
    public void registerProject(Project project) {
        entityManager.addProject(project);
    }
    
    public List<Project> findProjectsByManager(Manager manager) {
        return entityManager.getAllProjects().stream()
            .filter(p -> p.getOwner().equals(manager))
            .toList();
    }
    
    public List<Project> findActiveProjects() {
        return entityManager.getAllProjects().stream()
            .filter(p -> "IN_PROGRESS".equals(p.getStatus()))
            .toList();
    }
    
    public double calculateTotalProjectHours() {
        return entityManager.getAllProjects().stream()
            .mapToDouble(Project::getActualHours)
            .sum();
    }
    
    public Map<String, Double> getProjectProgress() {
        Map<String, Double> progress = new HashMap<>();
        entityManager.getAllProjects().forEach(p -> 
            progress.put(p.getTitle(), p.getProgress())
        );
        return Collections.unmodifiableMap(progress);
    }
}