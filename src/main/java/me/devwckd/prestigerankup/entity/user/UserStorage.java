package me.devwckd.prestigerankup.entity.user;

import com.mongodb.client.MongoCollection;
import me.devwckd.prestigerankup.database.MongoDataProvider;
import org.bson.conversions.Bson;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.mongodb.client.model.Filters.*;

public class UserStorage {

    private final MongoCollection<User> userCollection;
    private final Queue<User> updateQueue;

    public UserStorage(MongoDataProvider dataProvider) {
        this.userCollection = dataProvider.getCollection("user", User.class);
        this.updateQueue = new ConcurrentLinkedQueue<>();
    }

    public User getByUUID(UUID uuid) {
        try {
            return userCollection.find(eq("uuid", uuid)).first();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public User getByLowercaseNickname(String lowercaseNickname) {
        return userCollection.find(eq("lowercaseNickname", lowercaseNickname)).first();
    }

    public void update(User user) {
        Bson equalsUUID = eq("uuid", user.getUuid());
        if(userCollection.countDocuments(equalsUUID) > 0) {
            userCollection.replaceOne(equalsUUID, user);
        } else {
            userCollection.insertOne(user);
        }
    }

    public void requestUpdate(User user) {
        updateQueue.remove(user);
        updateQueue.add(user);
    }

    public Queue<User> getUpdateQueue() {
        return updateQueue;
    }

}
