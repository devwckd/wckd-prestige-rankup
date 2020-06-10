package me.devwckd.prestigerankup.entity.user;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;
import me.devwckd.prestigerankup.database.MongoDataProvider;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.googlecode.cqengine.index.navigable.NavigableIndex.*;
import static com.googlecode.cqengine.query.QueryFactory.*;

public class UserController {

    private final IndexedCollection<User> users;
    private final UserStorage userStorage;

    public UserController(MongoDataProvider dataProvider) {
        this.users = new ConcurrentIndexedCollection<>();
        this.users.addIndex(onAttribute(User.UUID));
        this.users.addIndex(onAttribute(User.LOWERCASE_NICKNAME));

        this.userStorage = new UserStorage(dataProvider);
    }

    public User getByUUID(UUID uuid) {
        try {
            return users.retrieve(equal(User.UUID, uuid)).uniqueResult();
        } catch (Exception $) {
            return null;
        }
    }

    public User getByLowercaseNickname(String lowercaseNickname) {
        try {
            return users.retrieve(equal(User.LOWERCASE_NICKNAME, lowercaseNickname)).uniqueResult();
        } catch (Exception $) {
            return null;
        }
    }

    public void loadToMemory(Player player) {
        UUID uuid = player.getUniqueId();

        if(getByUUID(uuid) != null) {

        }

        User user = userStorage.getByID(uuid);
        if(user == null) {
            user = new User(uuid, player.getName().toLowerCase());
        }

        users.add(user);
    }

    public void removeFromMemory(Player player) {
        User user = getByUUID(player.getUniqueId());
        if(user == null) return;
        users.remove(user);
    }


}
