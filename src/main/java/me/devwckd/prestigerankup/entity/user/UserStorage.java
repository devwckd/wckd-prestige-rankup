package me.devwckd.prestigerankup.entity.user;

import com.mongodb.client.MongoCollection;
import me.devwckd.prestigerankup.database.MongoDataProvider;
import org.bson.conversions.Bson;

import java.util.UUID;

import static com.mongodb.client.model.Filters.*;

public class UserStorage {

    private final MongoCollection<User> userCollection;

    public UserStorage(MongoDataProvider dataProvider) {
        this.userCollection = dataProvider.getCollection("user", User.class);
    }

    public User getByID(UUID uuid) {
        return userCollection.find(eq("uuid", uuid)).first();
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

}
