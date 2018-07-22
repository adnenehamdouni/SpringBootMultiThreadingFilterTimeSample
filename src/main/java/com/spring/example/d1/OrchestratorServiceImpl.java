package com.spring.example.d1;

import com.spring.example.model.User;
import com.spring.example.services.HelloService;
import com.spring.example.services.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by adnenehamdouni on 20/07/2018.
 */

@Service
public class OrchestratorServiceImpl implements OrchestratorService {

    @Autowired
    private HelloService helloService;

    @Override
    public User getOneUser(final long id) {
        // Mapping to Request for orchestrator
        /*UserRequest userRequest = UserRequest.builder().build()
                .withQuotationMode(quotationMode)
                .withCalculationMode(calculationMode)
                .withMaContract(maContract)
                .withPolicy(policy)
                .withAccountManager(accountManager)
                .withMaSalesContext(maSalesContext)
                .withMaUserProfile(maUserProfile)
                .withMaProcess(maProcess);*/

        // Call orchestrator service
        return helloService.getUser(id);
    }

}
