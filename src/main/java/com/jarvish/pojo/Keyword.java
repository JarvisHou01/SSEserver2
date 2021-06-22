package com.jarvish.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {
    private int id;
    private String keyword;
    private int fid;
    private String position;

    public Keyword(String keyword, int fid,String position) {
        this.keyword = keyword;
        this.fid = fid;
        this.position=position;
    }

}
