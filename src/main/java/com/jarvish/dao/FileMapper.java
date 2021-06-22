package com.jarvish.dao;


import com.jarvish.pojo.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    /**
     * 通过主键获取一行数据
     * @return
     */
    File getById(int id);

    /**
     * 插入一行数据
     * @param file
     * @return
     */
    int save(File file);

    /**
     * 更新一行数据
     * @param file
     * @return
     */
    int update(File file);

    /**
     * 删除一行数据
     * @param id
     * @return
     */
    int deleteById(int id);

    /**
     * 根据一个或多个属性获取File
     * @param file
     * @return
     */
    File getByFile(File file);

    /**
     * 根据用户id返回属于该用户的所有文件
     * @param uid
     * @return
     */
    List<File> getFilesByUid(int uid);

    /**
     * 根据ope范围返回文件
     * @param start
     * @param end
     * @return
     */
    List<File> getFilesByopeRange(@Param("uid")int uid, @Param("start")int start, @Param("end")int end);

}