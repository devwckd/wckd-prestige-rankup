package me.devwckd.prestigerankup.entity.user;

import lombok.Data;

import java.util.Objects;
import java.util.UUID;

@Data
public class User {

    private UUID uuid;
    private String lowercaseNickname;

    private int rankPosition = 0;
    private int prestige = 0;

    public void increasePrestige() {
        prestige++;
    }

    public User() { }

    public User(UUID uuid, String lowercaseNickname) {
        this.uuid = uuid;
        this.lowercaseNickname = lowercaseNickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return uuid.equals(user.getUuid()) && lowercaseNickname.equals(user.getLowercaseNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, lowercaseNickname);
    }
}
