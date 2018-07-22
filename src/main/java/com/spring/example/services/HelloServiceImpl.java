package com.spring.example.services;

import com.spring.example.Utils.Constants;
import com.spring.example.d1.UserMapper;
import com.spring.example.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by adnenehamdouni on 18/07/2018.
 */

@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello() {
        return "Hello World!";
    }

    @Override
    public HashMap<Long, User> getUsers() {
        return Constants.getUsers();
    }

    @Override
    public User getUser(long id) {

        List<User> users = new ArrayList<>();
        List<UserRequest> userIds = new ArrayList<>();

        for(Map.Entry<Long, User> entry : Constants.getUsers().entrySet()) {
            String key = Long.toString(entry.getKey());
            User value = entry.getValue();
            // do what you have to do here
            // In your case, another loop.
            users.add(value);
        }

        for (int i = 0; i<3; i++) {
            userIds.add(new UserRequest().builder().build().withId(i).withName("user"+i));
        }

        User ur = new User();
        addOffersToList(users, userIds, ur);


        return ur;
    }

    private void addOffersToList(List<User> users, List<UserRequest> userRequestList, User user) {
        // BC_Format Calculate offers Output for aggregators ( part 1 )

        log.info("addOffersToList: users list size = {}", users.size());
        log.info("addOffersToList: userRequestList list size = {}", userRequestList.size());
        users.stream().filter(User::isProfessionnal).map(n -> mapUserToCalculateOffer(n, userRequestList, user)).forEachOrdered(userRequestList::add);
    }

    private UserRequest mapUserToCalculateOffer(User newUser, List<UserRequest> userRequestList, User user) {

        log.info("addOffersToList: newUser id = {}", newUser.getId());
        Optional<UserRequest> userRequestOpt = userRequestList.stream().filter(us -> us.getName().equals(newUser.getName())).findFirst();

        if (userRequestOpt.isPresent()) {
            log.info("userRequest: user name = {}", userRequestOpt.get().getName());
            user = UserMapper.toUser(userRequestOpt.get());
        }

        return userRequestOpt.get();

    }

}
