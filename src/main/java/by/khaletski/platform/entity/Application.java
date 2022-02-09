package by.khaletski.platform.entity;

/**
 * Entity class "Application".
 *
 * @author Anton Khaletski
 */

public class Application {
    private int id;
    private User user;
    private Conference conference;
    private String description;
    private Status status;

    public enum Status {
        CLAIMED, CONFIRMED, REJECTED
    }

    private Application() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
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
        if (!user.equals(that.user)) return false;
        if (!conference.equals(that.conference)) return false;
        if (!description.equals(that.description)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + conference.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Application{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", conference=").append(conference);
        sb.append(", description='").append(description).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
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

        public Builder setUser(User user) {
            newApplication.user = user;
            return this;
        }

        public Builder setConference(Conference conference) {
            newApplication.conference = conference;
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