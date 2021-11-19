package by.khaletski.project.entity;

/**
 * Entity class "Application"
 *
 * @author Anton Khaletski
 */

public class Application {
    private int id;
    private User user;
    private Conference conference;
    private String applicationDescription;
    private Status applicationStatus;

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

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
    }

    public Status getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Status applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (id != that.id) return false;
        if (!user.equals(that.user)) return false;
        if (!conference.equals(that.conference)) return false;
        if (!applicationDescription.equals(that.applicationDescription)) return false;
        return applicationStatus == that.applicationStatus;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + conference.hashCode();
        result = 31 * result + applicationDescription.hashCode();
        result = 31 * result + applicationStatus.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Application{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", conference=").append(conference);
        sb.append(", applicationDescription='").append(applicationDescription).append('\'');
        sb.append(", applicationStatus=").append(applicationStatus);
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

        public Builder setDescription(String applicationDescription) {
            newApplication.applicationDescription = applicationDescription;
            return this;
        }

        public Builder setStatus(Status applicationStatus) {
            newApplication.applicationStatus = applicationStatus;
            return this;
        }

        public Application build() {
            return newApplication;
        }
    }
}