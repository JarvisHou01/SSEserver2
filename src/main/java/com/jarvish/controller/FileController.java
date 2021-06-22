package com.jarvish.controller;


import com.alibaba.fastjson.JSONObject;
import com.jarvish.pojo.File;
import com.jarvish.result.Result;
import com.jarvish.result.ResultFactory;
import com.jarvish.service.serviceImpl.FileServiceImpl;
import com.jarvish.service.serviceImpl.KeywordServiceImpl;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.*;

import static com.jarvish.util.FileUtil.findSameElementIn2Arrays;


@RestController
public class FileController {

    @Resource
    FileServiceImpl fileService;
    @Resource
    KeywordServiceImpl keywordService;


    @RequestMapping(value = "files", method = RequestMethod.GET)
    public Result getAllFiles(@RequestParam Map<String, Object> map) {
        int uid = Integer.parseInt(map.get("uid").toString());
        List<File> files = fileService.getAllByUid(uid);
        String query = map.get("query").toString();
        if (query != null || query != "") {
            for (int i = files.size() - 1; i >= 0; i--) {
                if (!files.get(i).getName().contains(query)) {
                    files.remove(i);
                }
            }
        }

        HashMap<Object, Object> result = new HashMap<>();
        result.put("totalpage", 10);
        result.put("pagenum", 1);
        result.put("files", files);

        return ResultFactory.buildSuccessResult(result, "获取文件成功");
    }


    @RequestMapping(value = "download", method = RequestMethod.GET)
    public String downloadFile(@RequestParam Map<String, Object> map, HttpServletResponse response) throws UnsupportedEncodingException {

        System.out.println(map.size());

        File download = fileService.download(Integer.parseInt(map.get("fid").toString()));

        java.io.File file = new java.io.File(download.getPath());

        if (file.exists()) {
            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(download.getName(), "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return "successfully";
            } catch (Exception e) {
                return "failed";

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (os != null) {
                    try {
                        os.flush();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

        }

        return "";

    }


    @RequestMapping(value = "delete", method = RequestMethod.GET)
    public Result deleteFile(@RequestParam Map<String, Object> map) {
        int fid = Integer.parseInt(map.get("fid").toString());
        fileService.deleteFileById(fid);
        keywordService.deleteByFid(fid);
        return ResultFactory.buildSuccessResult(null, "删除成功");

    }


    @RequestMapping(value = "search", method = RequestMethod.GET)
    public Result searchFiles(@RequestParam Map<String, Object> map) {


        String query = map.get("query").toString();
        int uid = Integer.parseInt(map.get("uid").toString());
        boolean checkbox = Boolean.parseBoolean(map.get("checkbox").toString());
        System.out.println(query);
        System.out.println(uid);
        System.out.println(checkbox);

        HashMap<Object, Object> result = new HashMap<>();


        if (query == null || query == "") {
            return ResultFactory.buildSuccessResult(result, "返回空集合");
        }




        List<File> files = new ArrayList<>();

        String[] trapdoors = query.split(" ");

        if (!checkbox) {
            // 单关键词
            if (trapdoors.length == 1) {
                int[] search = keywordService.search(trapdoors[0]);
                for (int i : search) {
                    File file = fileService.getById(i);
                    if (file.getUid() == uid) {
                        files.add(file);
                    }
                }
            } else {
                // 多关键词
                int[] search1 = keywordService.search(trapdoors[0]);
                int[] search2 = keywordService.search(trapdoors[1]);

                int[] same = findSameElementIn2Arrays(search1, search2);

                for (int i = 2; i < trapdoors.length; i++) {
                    int[] search = keywordService.search(trapdoors[i]);
                    same = findSameElementIn2Arrays(same, search);
                }

                for (int i : same) {
                    File file = fileService.getById(i);
                    if (file.getUid() == uid) {
                        files.add(file);
                    }
                }

            }
        } else {
            // 词组搜索

            int[] phraseSearch = keywordService.phraseSearch(trapdoors);

            for (int i : phraseSearch) {
                File file = fileService.getById(i);
                if (file.getUid() == uid) {
                    files.add(file);
                }

            }

        }


        HashSet<File> filesSet = new HashSet<>(files);



        result.put("files", filesSet);


        return ResultFactory.buildSuccessResult(result, "获取文件成功");


    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public void test(@RequestBody Map map) {

        System.out.println(map.get("username"));

    }

    @RequestMapping(value = "test2", method = RequestMethod.GET)
    public void tes2t(@RequestBody String s) {
        System.out.println(s);

    }


    @RequestMapping(value = "test3", method = RequestMethod.POST)
    public Result uploadtest(
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("index") MultipartFile index,
                             @RequestParam("ope") String ope,
                             @RequestParam("uid") String uid) throws IOException {


        System.out.println(file);
        System.out.println(index);
        System.out.println(ope);
        System.out.println(uid);


        file.transferTo(Paths.get("C:\\Users\\HJW\\Desktop\\test.txt"));
        index.transferTo(Paths.get("C:\\Users\\HJW\\Desktop\\test2.txt"));


        String md5 = DigestUtils.md5DigestAsHex(file.getInputStream());
        String filename = file.getOriginalFilename();
        System.out.println(filename);


        System.out.println("在Filecontroller中的upload方法----------------------------------");
        System.out.println("文件md5值:" + md5);
        System.out.println("文件名:" + filename);

        int fid = fileService.saveFile(filename, md5, Integer.parseInt(ope), file, Integer.parseInt(uid));

        keywordService.addIndex(fid, index);

        return ResultFactory.buildSuccessResult(null, "上传成功");


    }


}
