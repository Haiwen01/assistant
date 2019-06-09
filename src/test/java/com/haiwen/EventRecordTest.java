package com.haiwen;

import com.haiwen.entity.EventDO;
import com.haiwen.eventtrigger.EventTrigger;
import com.haiwen.model.EventRecord;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/1 下午6:49</p>
 *
 * @author haiwen.li
 */
public class EventRecordTest extends CucumberBaseTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventTrigger eventTrigger;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<EventRecord> eventRecordList;
    private String happenedEventMsg = null;

    @Given("^I input event content with format \"([^\"]*)\"$")
    public void i_input_event_content_with_format(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String[] eventStringArray = arg1.split(";");
        String dateString = eventStringArray[0];
        Date startTime = dateString.contains(" ") ? dateAndTimeFormat.parse(dateString) : dateFormat.parse(dateString);

        EventRecord eventRecord = EventRecord.builder()
                .startTime(startTime)
                .title(eventStringArray[1])
                .build();
//        eventService.addNewEvent(eventRecord);
    }

    @When("^I search the event happen at date \"([^\"]*)\"$")
    public void i_search_the_event_happen_at_date(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Date startTime = dateFormat.parse(arg1);
        eventRecordList = eventService.getEventsOnDate(startTime);
    }

    @Then("^I should get the event content \"([^\"]*)\"$")
    public void i_should_get_the_event_content(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        StringBuilder stringBuilder = new StringBuilder();
        for (EventRecord eventRecord : eventRecordList) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(" ");
            }

            stringBuilder.append(dateAndTimeFormat.format(eventRecord.getStartTime()));
            stringBuilder.append(";");
            stringBuilder.append(eventRecord.getTitle());
        }

        Assert.assertEquals(arg1, stringBuilder.toString());
    }

    @When("^Time arrive at date \"([^\"]*)\"$")
    public void time_arrive_at_date(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String[] eventStringArray = arg1.split(";");
        String dateString = eventStringArray[0];
        Date startTime = dateString.contains(" ") ? dateAndTimeFormat.parse(dateString) : dateFormat.parse(dateString);

        eventTrigger.setEventTriggerListener((eventDOList) -> {
            if (Objects.nonNull(eventDOList)) {
                EventDO eventDO = eventDOList.get(0);
                happenedEventMsg = dateAndTimeFormat.format(eventDO.getStartTime()) + ";" + eventDO.getTitle();
            }
        });
        eventTrigger.triggerEventAtTime(startTime);
    }

    @Then("^I should get the event alert message \"([^\"]*)\"$")
    public void i_should_get_the_event_alert_message(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(arg1, happenedEventMsg);
    }
}
