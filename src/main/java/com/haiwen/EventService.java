package com.haiwen;

import com.haiwen.entity.EventDO;
import com.haiwen.mapper.EventMapper;
import com.haiwen.model.EventRecord;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/1 下午6:51</p>
 *
 * @author haiwen.li
 */
@Service("eventService")
public class EventService {

    @Autowired
    private EventMapper eventMapper;

    public boolean addNewEvent(EventRecord eventRecord) {
        Date nowTime = new Date();
        EventDO eventDO = EventDO.builder()
                .createTime(nowTime)
                .updateTime(nowTime)
                .startTime(eventRecord.getStartTime())
                .title(eventRecord.getTitle())
                .build();
        int value = eventMapper.insert(eventDO);
        return value > 0;
    }

    public List<EventRecord> getEventsOnDate(Date dateTime) {
        if (Objects.isNull(dateTime)) {
            return Collections.EMPTY_LIST;
        }

        List<EventDO> eventDOList = eventMapper.selectByStartTime(dateTime);
        if (CollectionUtils.isEmpty(eventDOList)) {
            return Collections.EMPTY_LIST;
        }

        List<EventRecord> eventRecordList = eventDOList.stream().map(oneEventDO -> {
            return EventRecord.builder()
                    .startTime(oneEventDO.getStartTime())
                    .title(oneEventDO.getTitle())
                    .build();
        }).collect(Collectors.toList());
        return eventRecordList;
    }
}
