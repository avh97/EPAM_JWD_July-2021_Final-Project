package by.khaletski.platform.entity;

/**
 * Entity class "User".
 *
 * @author Anton Khaletski
 */

public class User {
    private int id;
    private String email;
    private String name;
    private String patronymic;
    private String surname;
    private Role role;

    public enum Role {
        ADMIN, PARTICIPANT
    }

    private User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (!email.equals(user.email)) return false;
        if (!name.equals(user.name)) return false;
        if (!patronymic.equals(user.patronymic)) return false;
        if (!surname.equals(user.surname)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + email.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + patronymic.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("User{");
        stringBuilder.append("id=").append(id);
        stringBuilder.append(", email='").append(email).append('\'');
        stringBuilder.append(", name='").append(name).append('\'');
        stringBuilder.append(", patronymic='").append(patronymic).append('\'');
        stringBuilder.append(", surname='").append(surname).append('\'');
        stringBuilder.append(", role=").append(role);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    public static class Builder {
        private final User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder setId(int id) {
            newUser.id = id;
            return this;
        }

        public Builder setEmail(String email) {
            newUser.email = email;
            return this;
        }

        public Builder setName(String name) {
            newUser.name = name;
            return this;
        }

        public Builder setPatronymic(String patronymic) {
            newUser.patronymic = patronymic;
            return this;
        }

        public Builder setSurname(String surname) {
            newUser.surname = surname;
            return this;
        }

        public Builder setRole(Role role) {
            newUser.role = role;
            return this;
        }

        public User build() {
            return newUser;
        }
    }
}
