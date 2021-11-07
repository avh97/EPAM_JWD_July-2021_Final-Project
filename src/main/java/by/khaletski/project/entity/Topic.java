package by.khaletski.project.entity;

/**
 * Entity class "Topic"
 *
 * @author Anton Khaletski
 */

public class Topic {
    private int id;
    private String topicName;
    private String image;
    private String topicDescription;

    public Topic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (id != topic.id) return false;
        if (!topicName.equals(topic.topicName)) return false;
        if (!image.equals(topic.image)) return false;
        return topicDescription.equals(topic.topicDescription);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + topicName.hashCode();
        result = 31 * result + image.hashCode();
        result = 31 * result + topicDescription.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Topic{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", topicName='").append(topicName).append('\'');
        stringBuilder.append(", image='").append(image).append('\'');
        stringBuilder.append(", topicDescription='").append(topicDescription).append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class Builder {
        private Topic newTopic;

        public Builder() {
            newTopic = new Topic();
        }

        public Builder setId(int id) {
            newTopic.id = id;
            return this;
        }

        public Builder setName(String topicName) {
            newTopic.topicName = topicName;
            return this;
        }

        public Builder setImageName(String image) {
            newTopic.image = image;
            return this;
        }

        public Builder setDescription(String topicDescription) {
            newTopic.topicDescription = topicDescription;
            return this;
        }

        public Topic build() {
            return newTopic;
        }
    }
}
