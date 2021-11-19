package by.khaletski.project.entity;

import java.sql.Date;

/**
 * Entity class "Conference"
 *
 * @author Anton Khaletski
 */

public class Conference {
    private int id;
    private Topic topic;
    private String conferenceName;
    private String conferenceDescription;
    private Date date;
    private Status conferenceStatus;

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

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
    }

    public String getConferenceDescription() {
        return conferenceDescription;
    }

    public void setConferenceDescription(String conferenceDescription) {
        this.conferenceDescription = conferenceDescription;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getConferenceStatus() {
        return conferenceStatus;
    }

    public void setConferenceStatus(Status conferenceStatus) {
        this.conferenceStatus = conferenceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conference that = (Conference) o;

        if (id != that.id) return false;
        if (!topic.equals(that.topic)) return false;
        if (!conferenceName.equals(that.conferenceName)) return false;
        if (!conferenceDescription.equals(that.conferenceDescription)) return false;
        if (!date.equals(that.date)) return false;
        return conferenceStatus == that.conferenceStatus;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + topic.hashCode();
        result = 31 * result + conferenceName.hashCode();
        result = 31 * result + conferenceDescription.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + conferenceStatus.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Conference{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", topic=").append(topic);
        stringBuilder.append(", conferenceName='").append(conferenceName).append('\'');
        stringBuilder.append(", conferenceDescription='").append(conferenceDescription).append('\'');
        stringBuilder.append(", date=").append(date);
        stringBuilder.append(", conferenceStatus=").append(conferenceStatus);
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
            newConference.conferenceName = conferenceName;
            return this;
        }

        public Builder setDescription(String conferenceDescription) {
            newConference.conferenceDescription = conferenceDescription;
            return this;
        }

        public Builder setDate(Date date) {
            newConference.date = date;
            return this;
        }

        public Builder setStatus(Status conferenceStatus) {
            newConference.conferenceStatus = conferenceStatus;
            return this;
        }

        public Conference build() {
            return newConference;
        }
    }
}
