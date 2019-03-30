package com.yuxh.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "view_log")
public class ViewLog {

    @Id
    @GeneratedValue
    private Integer id;
    private String viewIp;
    private Date viewDate;
    private String blogId;

}
