package com.spring.example.services;

import com.spring.example.model.User;

import java.util.HashMap;

/**
 * Created by adnenehamdouni on 18/07/2018.
 */

public interface HelloService {
    String sayHello();
    HashMap<Long, User> getUsers();
    User getUser(long id);
}

