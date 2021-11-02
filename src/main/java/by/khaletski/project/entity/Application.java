package by.khaletski.project.entity;

/**
 * Entity class "Application"
 *
 * @author Anton Khaletski
 */

public class Application {
    private int id;
    private int userId;
    private int conferenceId;
    private String description;
    private Status status;

    public enum Status {
        CLAIMED, CONFIRMED, CANCELED, ENDED
    }

    public Application() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (conferenceId != that.conferenceId) return false;
        if (!description.equals(that.description)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + conferenceId;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Application{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", userId=").append(userId);
        stringBuilder.append(", conferenceId=").append(conferenceId);
        stringBuilder.append(", description='").append(description).append('\'');
        stringBuilder.append(", status=").append(status);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class Builder {
        private Application newApplication;

        public Builder() {
            newApplication = new Application();
        }

        public Builder setId(int id) {
            newApplication.id = id;
            return this;
        }

        public Builder setUserId(int userId) {
            newApplication.userId = userId;
            return this;
        }

        public Builder setConferenceId(int conferenceId) {
            newApplication.conferenceId = conferenceId;
            return this;
        }

        public Builder setDescription(String description) {
            newApplication.description = description;
            return this;
        }

        public Builder setStatus(Status status) {
            newApplication.status = status;
            return this;
        }

        public Application build() {
            return newApplication;
        }
    }
}