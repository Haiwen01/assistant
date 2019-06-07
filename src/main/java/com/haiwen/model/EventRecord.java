package com.haiwen.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/1 下午6:52</p>
 *
 * @author haiwen.li
 */
@Data
@Builder
public class EventRecord {

    private Date startTime;

    private String title;
}
