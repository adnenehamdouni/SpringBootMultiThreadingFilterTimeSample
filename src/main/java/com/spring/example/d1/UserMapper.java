package com.spring.example.d1;

import com.spring.example.model.User;
import com.spring.example.services.UserRequest;

/**
 * Created by adnenehamdouni on 20/07/2018.
 */
public class UserMapper {
    public static User toUser(UserRequest u) {
        User user = new User();
        user.setId(u.getId());
        user.setName(u.getName());
        user.setProfessionnal(u.isProfessionnal());
        return user;

    }
}
