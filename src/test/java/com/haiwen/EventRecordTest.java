package com.haiwen;

import com.haiwen.model.EventRecord;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/1 下午6:49</p>
 *
 * @author haiwen.li
 */
public class EventRecordTest extends CucumberBaseTest{

    @Autowired
    private EventService eventService;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private final SimpleDateFormat readDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private List<EventRecord> eventRecordList;

    @Given("^I input event content with format \"([^\"]*)\"$")
    public void i_input_event_content_with_format(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        String[] eventStringArray = arg1.split(";");
        EventRecord eventRecord = EventRecord.builder()
                .startTime(dateFormat.parse(eventStringArray[0]))
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

            stringBuilder.append(readDateFormat.format(eventRecord.getStartTime()));
            stringBuilder.append(";");
            stringBuilder.append(eventRecord.getTitle());
        }

        Assert.assertEquals(arg1, stringBuilder.toString());
    }
}
