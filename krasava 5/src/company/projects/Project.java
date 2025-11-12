package company.projects;

import company.empoloyees.Manager;
import company.empoloyees.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Project extends ProjectEntity {
    private final List<Task> tasks = new ArrayList<>();
    private final List<Employee> team = new ArrayList<>();
    private double estimatedHours;
    private double actualHours;

    /**
     * Creates a new project with the given details.
     *
     * @param id Project unique identifier
     * @param title Project title
     * @param description Project description
     * @param startDate Project start date
     * @param dueDate Project end date
     * @param manager Project manager (owner)
     */
    public Project(String id, String title, String description,
                  LocalDateTime startDate, LocalDateTime dueDate, Manager manager) {
        super(id, title, description, startDate, dueDate, manager);
        if (manager != null) {
            team.add(manager);
        }
    }

    @Override
    protected void onStatusChanged() {
        String s = getStatus();
        switch (s) {
            case "CANCELLED":
                for (Task t : tasks) {
                    t.markCancelled();
                }
                break;
            case "COMPLETED":
                if (tasks.stream().anyMatch(t -> !"COMPLETED".equals(t.getStatus()))) {
                    throw new IllegalStateException("Cannot complete project with unfinished tasks");
                }
                break;
            case "IN_PROGRESS":
                tasks.stream()
                    .filter(t -> "NEW".equals(t.getStatus()))
                    .forEach(Task::markInProgress);
                break;
        }
    }

    void addTeamMember(Employee member) {
        Objects.requireNonNull(member, "Team member cannot be null");
        if (!team.contains(member)) {
            team.add(member);
        }
    }

    void addTask(Task task) {
        Objects.requireNonNull(task, "Task cannot be null");
        tasks.add(task);
        if ("NEW".equals(getStatus())) {
            markInProgress();
        }
    }

    void updateActualHours(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Hours cannot be negative");
        }
        this.actualHours += hours;
        if ("NEW".equals(getStatus())) {
            markInProgress();
        }
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public List<Employee> getTeam() {
        return Collections.unmodifiableList(team);
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double hours) {
        if (hours < 0) {
            throw new IllegalArgumentException("Estimated hours cannot be negative");
        }
        this.estimatedHours = hours;
    }

    public double getActualHours() {
        return actualHours;
    }

    public double getProgress() {
        return estimatedHours > 0 ? (actualHours / estimatedHours) * 100 : 0.0;
    }

    public void start() {
        markInProgress();
    }

    public void pause() {
        markOnHold();
    }

    public void complete() {
        markCompleted();
    }

    public void cancel() {
        markCancelled();
    }

    @Override
    public String toString() {
        return String.format("Project{id='%s', title='%s', status=%s, owner=%s, tasks=%d}",
            id, title, getStatus(),
            owner != null ? owner.getFullName() : "Unassigned", tasks.size());
    }
}
