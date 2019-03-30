package com.yuxh.blog.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "comment_info")
public class Comment {

    @Id
    @GeneratedValue
    private String id;

    private String blogId;
    private Date createDate;
    private String userIcon;
    private String userName;
    private String commentText;

}
