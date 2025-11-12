package company.projects;

import company.empoloyees.Employee;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.Objects;

public class TimeEntry {
    private final String id;
    private final Employee employee;
    private final Project project;
    private final Task task;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private boolean approved;

    /**
     * Creates a new time entry.
     *
     * @param id Unique identifier for the time entry
     * @param employee Employee who performed the work
     * @param project Project the work was done for
     * @param task Optional task within the project
     * @param startTime When work began
     */
    public TimeEntry(String id, Employee employee, Project project, Task task, LocalDateTime startTime) {
        Objects.requireNonNull(id, "TimeEntry id cannot be null");
        Objects.requireNonNull(employee, "Employee cannot be null");
        Objects.requireNonNull(project, "Project cannot be null");
        Objects.requireNonNull(startTime, "Start time cannot be null");
        this.id = id;
        this.employee = employee;
        this.project = project;
        this.task = task;
        this.startTime = startTime;
        this.approved = false;
    }

    /**
     * Records when work ended.
     *
     * @param endTime When work stopped
     * @throws IllegalArgumentException if end time is before start time
     */
    public void stopWork(LocalDateTime endTime) {
        Objects.requireNonNull(endTime, "End time cannot be null");
        if (endTime.isBefore(startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        this.endTime = endTime;
    }

    /**
     * Calculates total duration of work.
     *
     * @return Duration between start and end time, or ZERO if work hasn't ended
     */
    public Duration getDuration() {
        return endTime != null ? Duration.between(startTime, endTime) : Duration.ZERO;
    }

    /**
     * Gets total hours worked.
     *
     * @return Hours worked as a decimal value
     */
    public double getHours() {
        return getDuration().toMinutes() / 60.0;
    }

    /**
     * Approves the time entry and updates task work log.
     *
     * @throws IllegalStateException if entry has no end time
     */
    public void approve() {
        if (endTime == null) {
            throw new IllegalStateException("Cannot approve without end time");
        }
        if (!approved) {
            approved = true;
            if (task != null) {
                task.logWork(this);
            }
        }
    }

    public String getId() {
        return id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Project getProject() {
        return project;
    }

    public Task getTask() {
        return task;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public boolean isApproved() {
        return approved;
    }

    /**
     * Updates the description of unapproved time entries.
     *
     * @param desc New description
     * @throws IllegalStateException if entry is already approved
     */
    public void updateDescription(String desc) {
        if (!approved) {
            this.description = desc;
        } else {
            throw new IllegalStateException("Cannot change approved entry");
        }
    }

    @Override
    public String toString() {
        return String.format("TimeEntry{id='%s', employee=%s, project=%s, hours=%.2f, approved=%b}",
            id, employee.getFullName(), project.getTitle(), getHours(), approved);
    }
}
