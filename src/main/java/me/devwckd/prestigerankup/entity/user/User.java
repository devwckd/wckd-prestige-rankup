package me.devwckd.prestigerankup.entity.user;

import com.googlecode.cqengine.attribute.Attribute;
import lombok.Data;

import java.util.UUID;

import static com.googlecode.cqengine.query.QueryFactory.*;

@Data
public class User {

    public static final Attribute<User, UUID> UUID = attribute("UUID", User::getUuid);
    public static final Attribute<User, String> LOWERCASE_NICKNAME = attribute("lowercaseNickname", User::getLowercaseNickname);

    private final UUID uuid;
    private final String lowercaseNickname;

    private int rankPosition;
    private int prestige;

}
