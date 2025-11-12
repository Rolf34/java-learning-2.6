package company.projects;

import company.empoloyees.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class Task extends ProjectEntity {
    public enum TaskPriority { LOW, MEDIUM, HIGH, CRITICAL }

    private final Project project;
    private final List<TimeEntry> timeEntries;
    private double estimatedHours;
    private double actualHours;
    private TaskPriority priority;

    public Task(String id, String title, Project project, Employee assignee, LocalDateTime dueDate) {
        super(id, title, "", LocalDateTime.now(), dueDate, assignee);
        this.project = Objects.requireNonNull(project, "Project cannot be null");
        this.timeEntries = new ArrayList<>();
        this.priority = TaskPriority.MEDIUM;
        this.estimatedHours = 0;
        this.actualHours = 0;
    }

    @Override
    protected void onStatusChanged() {
        if ("COMPLETED".equals(getStatus())) {
            validateDates();
        }
    }

    @Override
    void validateDates() {
        super.validateDates();
        LocalDateTime ps = project.getStartDate();
        LocalDateTime pd = project.getDueDate();
        if (ps != null && getStartDate() != null && getStartDate().isBefore(ps)) {
            throw new IllegalArgumentException("Task cannot start before its project");
        }
        if (pd != null && getDueDate() != null && getDueDate().isAfter(pd)) {
            throw new IllegalArgumentException("Task cannot end after its project");
        }
    }

    void logWork(TimeEntry entry) {
        Objects.requireNonNull(entry, "TimeEntry cannot be null");
        if (!entry.getTask().equals(this)) {
            throw new IllegalArgumentException("TimeEntry for different task");
        }
        timeEntries.add(entry);
        this.actualHours += entry.getHours();
        project.updateActualHours(entry.getHours());
        if ("NEW".equals(getStatus())) {
            markInProgress();
        }
    }

    public Project getProject() {
        return project;
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

    public List<TimeEntry> getTimeEntries() {
        return Collections.unmodifiableList(timeEntries);
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = Objects.requireNonNull(priority, "Priority cannot be null");
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
        return String.format("Task{id='%s', title='%s', status=%s, priority=%s}",
            id, title, getStatus(), priority);
    }
}
