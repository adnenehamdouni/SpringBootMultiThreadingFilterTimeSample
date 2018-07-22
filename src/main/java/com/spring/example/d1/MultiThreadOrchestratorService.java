package com.spring.example.d1;

import com.spring.example.model.User;

import java.util.HashMap;
import java.util.List;

/**
 * Created by adnenehamdouni on 19/07/2018.
 */
@FunctionalInterface
public interface MultiThreadOrchestratorService {
    List<User> getUsersRunnable(List<User> listUserSubmit);
}
