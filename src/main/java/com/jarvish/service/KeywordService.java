package com.jarvish.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface KeywordService {

    int deleteByFid(int fid);

    void addIndex(int fid, MultipartFile indexFile) throws IOException;

    int[] search(String trapdoor);

    int[] phraseSearch(String[] trapdoors);

}
