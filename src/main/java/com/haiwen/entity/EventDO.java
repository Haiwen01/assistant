package com.haiwen.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * event
 * @author 
 */
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class EventDO implements Serializable {
    private static final long serialVersionUID = -3413342081007291875L;

    private Integer id;

    private Date createTime;

    private Date updateTime;

    private String title;

    private Date startTime;
}