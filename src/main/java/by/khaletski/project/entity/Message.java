package by.khaletski.project.entity;

/**
 * Entity class "Message"
 *
 * @author Anton Khaletski
 */

public class Message {
    private int id;
    private int fromId;
    private int toId;
    private String content;

    public Message() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != message.id) return false;
        if (fromId != message.fromId) return false;
        if (toId != message.toId) return false;
        return content.equals(message.content);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + fromId;
        result = 31 * result + toId;
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Message{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", fromId=").append(fromId);
        stringBuilder.append(", toId=").append(toId);
        stringBuilder.append(", content='").append(content).append('\'');
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

        public Builder setFromId(int fromId) {
            newMessage.fromId = fromId;
            return this;
        }

        public Builder setToId(int toId) {
            newMessage.toId = toId;
            return this;
        }

        public Builder setContent(String content) {
            newMessage.content = content;
            return this;
        }

        public Message build() {
            return newMessage;
        }
    }
}
