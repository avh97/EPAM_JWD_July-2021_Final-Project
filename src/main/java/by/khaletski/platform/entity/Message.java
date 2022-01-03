package by.khaletski.platform.entity;

import java.sql.Timestamp;

/**
 * Entity class "Message"
 *
 * @author Anton Khaletski
 */

public class Message {
    private int id;
    private Timestamp timestamp;
    private User user;
    private String question;
    private String answer;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (!this.timestamp.equals(message.timestamp)) return false;
        if (!this.user.equals(message.user)) return false;
        if (!this.question.equals(message.question)) return false;
        return this.answer.equals(message.answer);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + answer.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Message{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", timestamp=").append(timestamp);
        stringBuilder.append(", user=").append(user);
        stringBuilder.append(", question='").append(question).append('\'');
        stringBuilder.append(", answer='").append(answer).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class Builder {
        private Message newMessage;

        public Builder() {
            newMessage = new Message();
        }

        public Builder setId(int id) {
            newMessage.id = id;
            return this;
        }

        public Builder setTimestamp(Timestamp timestamp) {
            newMessage.timestamp = timestamp;
            return this;
        }

        public Builder setUser(User user) {
            newMessage.user = user;
            return this;
        }

        public Builder setQuestion(String question) {
            newMessage.question = question;
            return this;
        }

        public Builder setAnswer(String answer) {
            newMessage.answer = answer;
            return this;
        }

        public Message build() {
            return newMessage;
        }
    }
}
