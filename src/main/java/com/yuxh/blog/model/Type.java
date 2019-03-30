package com.yuxh.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "type_info")
public class Type {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
}
