package com.yuxh.blog.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "blog_info")
public class Blog {

    @Id
    @GeneratedValue
    private String id;

    private String blogAbstract;

    private String blogAuthor;

    private String blogCover;

    private String blogTitle;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    private Integer recommendSwitch;

    private Integer topSwitch;

    private Integer lableId;

    private Integer typeId;

    private String blogContext;

    private String blogHtml;

    @Transient
    private Integer viewCount;
    @Transient
    private Integer commentCount;
    @Transient
    private String lableName;
    @Transient
    private String typeName;
    @Transient
    private List<Comment> commentList;
    @Transient
    private String year;
    @Transient
    private String month;
    @Transient
    private String date;
}
