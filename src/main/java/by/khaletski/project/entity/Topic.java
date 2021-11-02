package by.khaletski.project.entity;

/**
 * Entity class "Topic"
 *
 * @author Anton Khaletski
 */

public class Topic {
    private int id;
    private String name;
    private String imageName;
    private String description;

    public Topic() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Topic topic = (Topic) o;

        if (id != topic.id) return false;
        if (!name.equals(topic.name)) return false;
        if (!imageName.equals(topic.imageName)) return false;
        return description.equals(topic.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + imageName.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("Topic{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", name='").append(name).append('\'');
        stringBuilder.append(", imageName='").append(imageName).append('\'');
        stringBuilder.append(", description='").append(description).append('\'');
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

        public Builder setName(String name) {
            newTopic.name = name;
            return this;
        }

        public Builder setImageName(String imageName) {
            newTopic.imageName = imageName;
            return this;
        }

        public Builder setDescription(String description) {
            newTopic.description = description;
            return this;
        }

        public Topic build() {
            return newTopic;
        }
    }
}
