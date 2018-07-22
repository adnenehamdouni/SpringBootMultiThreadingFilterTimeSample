package com.spring.example.d1;

import com.spring.example.model.User;
import com.spring.example.services.HelloService;
import com.spring.example.services.UserRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by adnenehamdouni on 19/07/2018.
 */

@Service
@Slf4j
public class MultiThreadOrchestratorServiceImpl implements MultiThreadOrchestratorService {


    private ExecutorService executorService;

    @Autowired
    private HelloService helloService;

    @Autowired
    @Qualifier("tracking")
    private ThreadLocal<String> trackingId;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(10);
    }

    @PreDestroy
    public void destroy() {
        executorService.shutdownNow();
    }

    @Override
    public List<User>  getUsersRunnable(List<User> listUserToSubmit) {

        log.info("call quotation real service");

        List<User>  user = null;

        if (!listUserToSubmit.isEmpty()) {

            List<RunnableOneQuotation> runnableQuotations = new ArrayList<>();

            int index = 0;
            for (User myUser : listUserToSubmit) {

                RunnableOneQuotation myQuotation = new RunnableOneQuotation(helloService, myUser, trackingId, trackingId.get() + "-" + index);
                runnableQuotations.add(myQuotation);
                index++;

            }

            try {

                List<Future<User>> futures = executorService.invokeAll(runnableQuotations, 60, TimeUnit.SECONDS);

                // Transform occurences of Futures -> List of <MaPolicy>
                // 1- Filter isDone -> Select only Future with status isDone() ( isCancelled() are excluded )
                // 2- Transform Future<MaPolicy> in MaPolicy -> Select only Future without Exception ( InterruptedException && ExecutionException are excluded ( set to null ) )
                // 3- Filter MaPolicy not null ( exclude previous step errors )
                // 4- Transform occurences of MaPolicy in List of MaPolicy
                user = futures.stream().filter(Future::isDone).map(f -> transformFuture(f)).filter(Objects::nonNull).collect(Collectors.toList());

                log.error("MultiThreadOrchestratorService : user size {}",user.size());
            } catch (InterruptedException e) {
                log.error("MultiThreadOrchestratorService InterruptedException{}",e);
                // Restore interrupted state...
                Thread.currentThread().interrupt();
            }

        }

        return user;
    }

    private User transformFuture(Future<User> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("getQuotationsRunnable future interrupted or execution {}",e);
            return null;
        }
    }

    private static class RunnableOneQuotation implements Callable<User> {

        private HelloService helloService;
        private ThreadLocal<String> tl;
        private String trackingId;
        private User user;

        RunnableOneQuotation(HelloService helloService, User user, ThreadLocal<String> tl, String trackingId) {
            this.helloService=helloService;
            this.tl = tl;
            this.user = user;
            this.trackingId=trackingId;
        }

        @Override
        public User call() {
            log.debug(trackingId + " - Start process in thread " + Thread.currentThread().getName() + "  ...");
            // As we are in a new Thread, record trackingId to ThreadLocal
            tl.set(trackingId);
            User user = null;
            try {
                user = helloService.getUser(1);
            } catch (Exception e) {
                log.error("Error while calling CISL", e);
                return null;
            }
            log.debug(trackingId + " - process ended");
            // Clean value, as the Thread may be reused for something else
            tl.set(null);
            return user;
        }

    }

}
