package com.haiwen.push;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import com.haiwen.eventtrigger.EventTrigger;
import com.haiwen.entity.EventDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/9 上午10:56</p>
 *
 * @author haiwen.li
 */
@Service("pushService")
@Slf4j
public class PushService implements EventTrigger.EventTriggerListener{

    private final String MASTER_SECRET = "f61f80eb5c1286b06b068e4c";
    private final String APP_KEY = "311a52edab3f85f394b21fbf";
    private final JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
    private final SimpleDateFormat dateAndTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onEventOccur(List<EventDO> eventDOList) {
        if (CollectionUtils.isEmpty(eventDOList)) {
            return;
        }

        List<EventDO> copyDOList = new ArrayList<>(eventDOList);
        StringBuilder stringBuilder = new StringBuilder();
        for (EventDO eventDO : copyDOList) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(" ");
            }

            stringBuilder.append(dateAndTimeFormat.format(eventDO.getStartTime()));
            stringBuilder.append(";");
            stringBuilder.append(eventDO.getTitle());
        }
        pushMessageWithContent(stringBuilder.toString());
    }

    private void pushMessageWithContent(String content) {
        PushPayload payload = buildPushMessageWithContent(content);
        try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
            log.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
            log.error("Should review the error, and fix the request", e);
            log.info("HTTP Status: " + e.getStatus());
            log.info("Error Code: " + e.getErrorCode());
            log.info("Error Message: " + e.getErrorMessage());
        }
    }

    private PushPayload buildPushMessageWithContent(String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
                        .build())
                .build();
    }
}
