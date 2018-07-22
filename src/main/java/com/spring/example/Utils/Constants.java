package com.spring.example.Utils;

import com.spring.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by adnenehamdouni on 19/07/2018.
 */
public class Constants {

    private HashMap<Long, User> userList = null;


    public static HashMap<Long, User> getUsers() {
        HashMap<Long, User> userList = new HashMap<>();

        User user1 = new User(1, "user"+1, true);
        User user2 = new User(1, "user"+2, false);
        User user3 = new User(1, "user"+3, true);

        userList.put(Long.parseLong(Integer.toString(1)), user1);
        userList.put(Long.parseLong(Integer.toString(2)), user2);
        userList.put(Long.parseLong(Integer.toString(3)), user3);

        return userList;

    }

    public static List<User> getUsersToSubmit() {

        List<User> userList = new ArrayList<>();

        User user1 = new User(1, "user"+1, true);
        User user2 = new User(1, "user"+2, false);
        User user3 = new User(1, "user"+3, true);

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        return userList;
    }
}
