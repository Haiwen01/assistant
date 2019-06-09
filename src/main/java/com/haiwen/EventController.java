package com.haiwen;

import com.haiwen.model.EventRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/6 下午9:45</p>
 *
 * @author haiwen.li
 */
@RestController
public class EventController {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private EventService eventService;

    @GetMapping("/allEvents")
    public List<EventRecord> getAllEvents() {
        try {
            Date testDate = dateFormat.parse("2019-05-31 16:00:00");
            List<EventRecord> eventRecordList = eventService.getEventsOnDate(testDate);
            return eventRecordList;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/addEvent")
    public String addNewEvent(@RequestBody EventRecord eventRecord) {
        boolean success = eventService.addNewEvent(eventRecord);
        return success ? "true" : "false";
    }
}
