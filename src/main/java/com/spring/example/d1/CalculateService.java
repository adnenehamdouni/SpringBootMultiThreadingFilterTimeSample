package com.spring.example.d1;

import com.spring.example.model.User;
import com.spring.example.services.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adnenehamdouni on 19/07/2018.
 */


@Service
@Slf4j
public class CalculateService {

    @Autowired
    private MultiThreadOrchestratorService multiThreadOrchestratorService;

    @Autowired
    private OrchestratorService orchestratorService;

    public CalculateResult calculateQuotations(long id, List<User> listPolicyToSubmit) {

        List<User> userList = new ArrayList<>();

        CalculateResult calculateResult = new CalculateResult();

        if (!listPolicyToSubmit.isEmpty()) {
            log.debug(" Number of MaPolicy to Calculate : " + listPolicyToSubmit.size());
            if ( listPolicyToSubmit.size() > 1 ) {
                List<User> newUsers = multiThreadOrchestratorService.getUsersRunnable(listPolicyToSubmit);
                // Filter null policies ( already done )
                if (!newUsers.isEmpty()) {
                    for ( User user : newUsers) {
                        if (user != null) {
                            userList.add(user);
                        }
                    }
                }
            } else {

                User newUser = orchestratorService.getOneUser(id);
                if ( newUser != null ) {
                    userList.add(newUser);
                }

            }
            log.debug(" User calculated : " + userList.size());
        }

        calculateResult.setProfessionStatus(ProfessionStatus.WITH_PROFESSION);
        calculateResult.setListPolicies(userList);

        return calculateResult;
    }

}
