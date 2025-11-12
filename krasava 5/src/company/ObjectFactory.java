package company;

import company.empoloyees.*;
import company.projects.*;
import company.structure.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class ObjectFactory {
    private ObjectFactory() {}

    public static Department createDepartment(String code, String name, String description) {
        return new Department(code, name, description);
    }

    public static Position createPosition(String id, String title, String description, double minSalary, double maxSalary) {
        return new Position(id, title, description, minSalary, maxSalary);
    }

    public static WorkSchedule createWorkSchedule(String id, String name) {
        return new WorkSchedule(id, name, 5, 8.0, LocalTime.of(9,0), LocalTime.of(18,0), Duration.ofHours(1), false);
    }

    public static Employee createEmployee(String id, String firstName, String lastName, Department department, Position position, String hireDate, double salary, WorkSchedule schedule) {
        return new Employee(id, firstName, lastName, firstName.toLowerCase()+"."+lastName.toLowerCase()+"@company.com",
                "+380931234" + id.replaceAll("[^0-9]", ""), department, position, hireDate, salary, schedule);
    }

    public static Manager createManager(String id, String firstName, String lastName, Department department, Position position, String hireDate, double salary, WorkSchedule schedule) {
        return new Manager(id, firstName, lastName, firstName.toLowerCase()+"."+lastName.toLowerCase()+"@company.com",
                "+380931234" + id.replaceAll("[^0-9]", ""), department, position, hireDate, salary, schedule, true, department);
    }

    public static Project createProject(String id, String title, Manager owner) {
        return new Project(id, title, "Created by ObjectFactory", LocalDateTime.now(), LocalDateTime.now().plusMonths(1), owner);
    }

    public static Task createTask(String id, String title, Project project, Employee assignee) {
        return new Task(id, title, project, assignee, LocalDateTime.now().plusDays(7));
    }

    public static TimeEntry createTimeEntry(String id, Employee employee, Project project, Task task) {
        return new TimeEntry(id, employee, project, task, LocalDateTime.now().minusHours(2));
    }
}
