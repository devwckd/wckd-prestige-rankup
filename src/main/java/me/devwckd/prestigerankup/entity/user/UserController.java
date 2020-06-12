package me.devwckd.prestigerankup.entity.user;

import lombok.Getter;
import me.devwckd.prestigerankup.database.MongoDataProvider;
import org.bukkit.entity.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class UserController {

    private final Map<UUID, User> users;
    private final UserStorage userStorage;

    public UserController(MongoDataProvider dataProvider) {
        this.users = new ConcurrentHashMap<>();

        this.userStorage = new UserStorage(dataProvider);
    }

    public User getByUUID(UUID uuid) {
        return users.get(uuid);
    }

    public User getByLowercaseNickname(String lowercaseNickname) {
        throw new NotImplementedException();
    }

    public void loadToMemory(Player player) {
        UUID uuid = player.getUniqueId();

        if(getByUUID(uuid) != null) {
            removeFromMemory(player);
        }

        User user = userStorage.getByUUID(uuid);
        if(user == null) {
            user = new User(uuid, player.getName().toLowerCase());
            requestUpdate(user);
        }

        users.put(user.getUuid(), user);
    }

    public void removeFromMemory(Player player) {
        users.remove(player.getUniqueId());
    }

    public void requestUpdate(Player player) {
        UUID uuid = player.getUniqueId();

        User user = getByUUID(uuid);
        if(user == null) user = userStorage.getByUUID(uuid);
        if(user == null) return;

        requestUpdate(user);
    }

    public void requestUpdate(User user) {
        userStorage.requestUpdate(user);
    }

}
