package com.haiwen.eventtrigger;

import com.haiwen.entity.EventDO;
import com.haiwen.mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/9 上午11:21</p>
 *
 * @author haiwen.li
 */
@Component("eventTrigger")
public class EventTrigger {

    private static final CopyOnWriteArraySet<EventTriggerListener> triggerListenerList = new CopyOnWriteArraySet<>();

    @Autowired
    private EventMapper eventMapper;

    public interface EventTriggerListener {
        void onEventOccur(List<EventDO> eventDOList);
    }

    //TODO: optimize on async
    public void triggerEventAtTime(Date dateTime) {
        List<EventDO> eventDOList = eventMapper.selectByStartTime(dateTime);
        if (!CollectionUtils.isEmpty(eventDOList)) {
            Iterator<EventTriggerListener> listenerIterable = triggerListenerList.iterator();
            while (listenerIterable.hasNext()) {
                listenerIterable.next().onEventOccur(eventDOList);
            }
        }
    }

    public void setEventTriggerListener(EventTriggerListener listener) {
        if (Objects.nonNull(listener)) {
            triggerListenerList.clear();
            triggerListenerList.add(listener);
        }
    }
}
