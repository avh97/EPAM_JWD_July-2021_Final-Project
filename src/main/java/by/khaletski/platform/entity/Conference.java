package by.khaletski.platform.entity;

import java.sql.Date;

/**
 * Entity class "Conference"
 *
 * @author Anton Khaletski
 */

public class Conference {
    private int id;
    private Topic topic;
    private String name;
    private String description;
    private Date date;
    private Status status;

    public enum Status {
        PENDING, CANCELED, ENDED
    }

    public Conference() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

        Conference that = (Conference) o;

        if (id != that.id) return false;
        if (!topic.equals(that.topic)) return false;
        if (!name.equals(that.name)) return false;
        if (!description.equals(that.description)) return false;
        if (!date.equals(that.date)) return false;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + topic.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Conference{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", topic=").append(topic);
        stringBuilder.append(", name='").append(name).append('\'');
        stringBuilder.append(", description='").append(description).append('\'');
        stringBuilder.append(", date=").append(date);
        stringBuilder.append(", status=").append(status);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class Builder {
        private Conference newConference;

        public Builder() {
            newConference = new Conference();
        }

        public Builder setId(int id) {
            newConference.id = id;
            return this;
        }

        public Builder setTopic(Topic topic) {
            newConference.topic = topic;
            return this;
        }

        public Builder setName(String conferenceName) {
            newConference.name = conferenceName;
            return this;
        }

        public Builder setDescription(String conferenceDescription) {
            newConference.description = conferenceDescription;
            return this;
        }

        public Builder setDate(Date conferenceDate) {
            newConference.date = conferenceDate;
            return this;
        }

        public Builder setStatus(Status conferenceStatus) {
            newConference.status = conferenceStatus;
            return this;
        }

        public Conference build() {
            return newConference;
        }
    }
}
