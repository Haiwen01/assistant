package com.haiwen.eventtrigger;

import com.haiwen.push.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/9 上午11:53</p>
 *
 * @author haiwen.li
 */
@Configuration
@Scope("singleton")
public class EventTriggerConfiguration {

    @Autowired
    private EventTrigger eventTrigger;

    public EventTriggerConfiguration(EventTrigger eventTrigger, PushService pushService) {
        eventTrigger.setEventTriggerListener(pushService);

        //TODO: need optimize
        CompletableFuture.runAsync(() -> {
            while (true) {
                eventTrigger.triggerEventAtTime(new Date());

                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
