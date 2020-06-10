package me.devwckd.prestigerankup.entity.user;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.IndexedCollection;

import java.util.UUID;

import static com.googlecode.cqengine.index.navigable.NavigableIndex.*;
import static com.googlecode.cqengine.query.QueryFactory.*;

public class UserController {

    private final IndexedCollection<User> users;

    public UserController() {
        users = new ConcurrentIndexedCollection<>();
        users.addIndex(onAttribute(User.UUID));
        users.addIndex(onAttribute(User.LOWERCASE_NICKNAME));
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

}
