package com.haiwen.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <p>Description: .</p>
 * <p>Date: 2019/6/1 下午6:52</p>
 *
 * @author haiwen.li
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRecord {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    private String title;
}
