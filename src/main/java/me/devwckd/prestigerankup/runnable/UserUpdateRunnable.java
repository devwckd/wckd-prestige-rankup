package me.devwckd.prestigerankup.runnable;

import me.devwckd.prestigerankup.entity.user.User;
import me.devwckd.prestigerankup.entity.user.UserController;
import me.devwckd.prestigerankup.entity.user.UserStorage;

import java.util.Queue;

public class UserUpdateRunnable implements Runnable {

    private final UserStorage userStorage;
    private final Queue<User> updateQueue;

    public UserUpdateRunnable(UserController userController) {
        this.userStorage = userController.getUserStorage();
        this.updateQueue = userStorage.getUpdateQueue();
    }

    @Override
    public void run() {

        if(updateQueue.isEmpty()) return;

        User user = updateQueue.poll();
        if(user == null) return;

        userStorage.update(user);

    }

}
