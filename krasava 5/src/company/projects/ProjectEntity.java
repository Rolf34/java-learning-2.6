package company.projects;

import company.empoloyees.Employee;
import java.time.LocalDateTime;
public abstract class ProjectEntity {
    protected final String id;
    protected String title;
    protected String description;
    protected LocalDateTime startDate;
    protected LocalDateTime dueDate;
    protected Employee owner;

    static enum Status { NEW, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED }

    Status status;

    protected ProjectEntity(String id, String title, String description,
                          LocalDateTime startDate, LocalDateTime dueDate,
                          Employee owner) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.owner = owner;
        this.status = Status.NEW;
    }

    void validateDates() {
        if (startDate != null && dueDate != null && dueDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Due date cannot be before start date");
        }
    }

    protected void markInProgress() {
        if (status == Status.NEW || status == Status.ON_HOLD) {
            status = Status.IN_PROGRESS;
            onStatusChanged();
        }
    }

    protected void markOnHold() {
        if (status == Status.IN_PROGRESS) {
            status = Status.ON_HOLD;
            onStatusChanged();
        }
    }

    protected void markCompleted() {
        if (status == Status.IN_PROGRESS) {
            status = Status.COMPLETED;
            onStatusChanged();
        }
    }

    protected void markCancelled() {
        if (status != Status.COMPLETED) {
            status = Status.CANCELLED;
            onStatusChanged();
        }
    }

    protected abstract void onStatusChanged();

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getDueDate() { return dueDate; }
    public Employee getOwner() { return owner; }
    public String getStatus() { return status.name(); }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }
    public void setDescription(String description) { this.description = description; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; validateDates(); }
    public void setOwner(Employee owner) { this.owner = owner; }

    @Override
    public String toString() {
        return String.format("%s{id='%s', title='%s', status=%s, owner=%s}",
            getClass().getSimpleName(), id, title, status,
            owner != null ? owner.getFullName() : "Unassigned");
    }
}