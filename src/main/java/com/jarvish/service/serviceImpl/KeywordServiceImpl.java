package com.jarvish.service.serviceImpl;

import com.jarvish.dao.KeywordMapper;
import com.jarvish.pojo.Keyword;
import com.jarvish.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.jarvish.util.FileUtil.findSameElementIn2Arrays;

@Service
public class KeywordServiceImpl implements KeywordService {

    @Autowired
    KeywordMapper keywordMapper;

    @Override
    public int deleteByFid(int fid) {
        return keywordMapper.deleteByFid(fid);
    }

    @Override
    public void addIndex(int fid, MultipartFile indexFile) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(indexFile.getInputStream()));




        int lineNum = 1;
        int flag = 0;
        HashMap<String, String> hashMap = new HashMap<>();

        long t1 = System.currentTimeMillis();

        while (reader.ready()) {
            String line = reader.readLine();
            if ("=====End of sequential word storage=====".equals(line)) {
                flag = 1;
                continue;
            }
            switch (flag) {
                case 0:
                    if (hashMap.containsKey(line)) {
                        String value = hashMap.get(line);
                        value += "," + lineNum;
                        hashMap.put(line, value);
                    } else {
                        hashMap.put(line, String.valueOf(lineNum));
                    }
//                    System.out.println("flag为0:::"+line+":::"+linenum);
                    break;
                case 1:
                    keywordMapper.add(new Keyword(line,fid,0+""));
                    break;
            }
            lineNum++;
        }

        long t2 = System.currentTimeMillis();

        System.out.println("读取文件的时间为:"+(t2-t1));

        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            keywordMapper.add(new Keyword(entry.getKey(),fid,entry.getValue()));
        }

        long t3 = System.currentTimeMillis();

        System.out.println("写入数据库的时间为"+(t3-t2));
    }

    @Override
    public int[] search(String trapdoor) {
        return keywordMapper.search(trapdoor);
    }

    @Override
    public int[] phraseSearch(String[] trapdoors) {
        // 多关键词
        int[] search1 = search(trapdoors[0]);
        int[] search2 = search(trapdoors[1]);

        int[] same = findSameElementIn2Arrays(search1, search2);

        for (int i = 2; i < trapdoors.length; i++) {
            int[] search = search(trapdoors[i]);
            same = findSameElementIn2Arrays(same, search);
        }


        ArrayList<Integer> finalResult = new ArrayList<>();


        String[] temp =null;
        int flag = 0;
        for (int fid : same) {

            for (String trapdoor : trapdoors) {

                for (String position : keywordMapper.searchPositionByFidAndKeyword(trapdoor, fid)) {
                    // 排除fuzzy位置
                    if (!"0".equals(position)){
                        String[] strings = position.split(",");
                        flag=0;

                        if (temp!=null){
                            for (String string : strings) {
                                for (String s : temp) {
                                    if (Integer.parseInt(string) - Integer.parseInt(s) == 1) {
                                        flag = 1;
                                    }
                                }
                            }
                        }else {
                            flag=1;
                        }
                        if (flag!=1){
                            break;
                        }
                        temp = strings;
                    }
                }
                if (flag!=1){
                    break;
                }
            }
            if (flag==1){
                finalResult.add(fid);
            }
            temp=null;

        }


        return finalResult.stream().mapToInt(Integer::intValue).toArray();
    }
}
