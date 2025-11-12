package company.structure;

import java.time.LocalDateTime;


public abstract class OrganizationalUnit {
    protected String code;
    protected String name;
    protected String description;
    protected LocalDateTime createdDate;
    protected LocalDateTime lastModifiedDate;
    protected boolean isActive;

    protected OrganizationalUnit(String code, String name, String description) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        
        this.code = code.trim();
        this.name = name.trim();
        this.description = description;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = this.createdDate;
        this.isActive = true;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public boolean isActive() { return isActive; }

    protected void updateLastModified() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    protected void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
        updateLastModified();
    }

    protected void setDescription(String description) {
        this.description = description;
        updateLastModified();
    }

    public abstract void activate();
    public abstract void deactivate();
    public abstract String getType();

    @Override
    public String toString() {
        return String.format("%s{code='%s', name='%s', active=%b}",
            getType(), code, name, isActive);
    }
}