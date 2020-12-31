package com.silent.utils;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author
 * @Title: FileUtils
 * @ProjectName CODE-X
 * @Description: 文档工具类
 * @date 2020/8/20 15:45
 */
@Slf4j
public class FileUtils {

    public static String getFileContent(String url) throws IOException {
        File file = new File(url);
        InputStream is = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = br.readLine()) != null){
            sb.append(str);
        }
        return sb.toString();
    }


}
