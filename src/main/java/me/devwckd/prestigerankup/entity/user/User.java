package me.devwckd.prestigerankup.entity.user;

import com.googlecode.cqengine.attribute.Attribute;
import lombok.Data;

import java.util.Objects;
import java.util.UUID;

import static com.googlecode.cqengine.query.QueryFactory.*;

@Data
public class User {

    public static final Attribute<User, UUID> UUID = attribute("UUID", User::getUuid);
    public static final Attribute<User, String> LOWERCASE_NICKNAME = attribute("lowercaseNickname", User::getLowercaseNickname);

    private final UUID uuid;
    private final String lowercaseNickname;

    private int rankPosition = 0;
    private int prestige = 0;

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
