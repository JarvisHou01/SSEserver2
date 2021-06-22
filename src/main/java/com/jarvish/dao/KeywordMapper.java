package com.jarvish.dao;

import com.jarvish.pojo.Keyword;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface KeywordMapper {

    int add(Keyword keyword);

    int deleteByFid(int fid);

    int[] search(String trapdoor);

    String[] searchPositionByFidAndKeyword(String keyword,int fid);

}