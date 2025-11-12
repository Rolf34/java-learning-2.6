package company.structure;

import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

public class Position extends OrganizationalUnit {
    private double minSalary;
    private double maxSalary;
    private final Set<String> requiredSkills;
    private int accessLevel;

    public Position(String id, String title, String description, double minSalary, double maxSalary) {
        super(id, title, description);
        if (minSalary > maxSalary) {
            throw new IllegalArgumentException("Minimum salary cannot be greater than maximum salary");
        }
        if (minSalary < 0 || maxSalary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative");
        }

        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.requiredSkills = new HashSet<>();
        this.accessLevel = 1; // Default access level
    }


    @Override
    public String getType() {
        return "Position";
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
        }
    }


    public double getMinSalary() {
        return minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public void addRequiredSkill(String skill) {
        if (skill != null && !skill.trim().isEmpty()) {
            requiredSkills.add(skill.trim());
        }
    }

    public Set<String> getRequiredSkills() {
        return Collections.unmodifiableSet(requiredSkills);
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int level) {
        if (level < 1) {
            throw new IllegalArgumentException("Access level must be positive");
        }
        this.accessLevel = level;
    }

    @Override
    public String toString() {
        return String.format("Position{id='%s', title='%s', salary=%.2f-%.2f, skills=%s, level=%d, active=%b}",
            getCode(), getName(), minSalary, maxSalary, requiredSkills, accessLevel, isActive);
    }
}
