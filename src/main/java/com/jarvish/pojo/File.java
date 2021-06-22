package com.jarvish.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class File {
    private int id;
    private String name;
    private String md5;
    private String path;
    private int ope;
    private Date uploadTime;
    private int uid;


}
