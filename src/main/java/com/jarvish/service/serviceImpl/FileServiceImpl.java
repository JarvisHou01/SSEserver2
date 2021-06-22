package com.jarvish.service.serviceImpl;

import com.jarvish.dao.FileMapper;
import com.jarvish.pojo.File;
import com.jarvish.service.FileService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public List<File> getAllByUid(int uid) {
        return fileMapper.getFilesByUid(uid);
    }

    @Override
    public File download(int fid) {

        return fileMapper.getById(fid);

    }

    @Override
    public int deleteFileById(int fid) {
        return fileMapper.deleteById(fid);
    }

    @Override
    public int saveFile(String name, String md5, int ope, MultipartFile file, int uid){
        String path = "D:\\uploadfile\\" + file.getOriginalFilename();
        try {
            file.transferTo(new java.io.File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File fileToCommit = new File();
        fileToCommit.setName(name);
        fileToCommit.setMd5(md5);
        fileToCommit.setPath(path);
        fileToCommit.setOpe(ope);
        fileToCommit.setUploadTime(new Date());
        fileToCommit.setUid(uid);
        fileMapper.save(fileToCommit);
        return fileToCommit.getId();
    }

    @Override
    public File getById(int id) {
        return fileMapper.getById(id);
    }


}
