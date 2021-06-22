package com.jarvish.service;

import com.jarvish.pojo.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<File> getAllByUid(int uid);

    File download(int fid);

    int deleteFileById(int fid);

    int saveFile(String name, String md5, int ope, MultipartFile file, int uid);

    File getById(int id);
}
