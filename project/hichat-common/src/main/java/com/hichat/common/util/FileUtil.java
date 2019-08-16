package com.hichat.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**

 * author : gaojunjie

 * date   : 2017-07-19 9:23

 * desc   : 文件流处理工具类

 **/
public class FileUtil {

    public static boolean writeToFile(InputStream reader, String path,String md5) {
        FileOutputStream fileWriter = null;
        File file = new File(path);
        boolean result=true;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileOutputStream(file);
            int writeLen = 0;
            int len = 0;
            byte[] buff = new byte[1024];
            while ((len = reader.read(buff)) >= 0) {
                if (len != 0) {
                    fileWriter.write(buff, 0, len);
                    writeLen += len;
                }

            }
            FileInputStream fileInputStream=new FileInputStream(file);
            String fileMd5 = DigestUtils.md5Hex(fileInputStream);
            if(!md5.equals(fileMd5)){
                //删掉该文件

                file.deleteOnExit();
                result= false;
            }
        } catch (Exception e) {
            file.deleteOnExit();
            result= false;
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.flush();
                    fileWriter.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }


    public static void wirteToResponse(HttpServletResponse response, File file) {
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte[] readByte = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(readByte)) >= 0) {
                outputStream.write(readByte, 0, len);
            }
        } catch (Exception e) {
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
    }

    public static boolean copyFile(String source, String target) {
        BufferedWriter bufferedWriter = null;
        BufferedReader bufferedReader = null;
        File copy=new File(target);
        boolean result=true;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(target));
            bufferedReader = new BufferedReader(new FileReader(source));

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            copy.deleteOnExit();
            result= false;
        } finally {
            try {
                bufferedWriter.close();
                bufferedReader.close();
            } catch (IOException e) {
            }
        }
        return result;
    }



    public static int getSavePath(String headPath) {
        File dicFile = new File(headPath);
        File[] files = dicFile.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        int childDicFileLength = files.length;
        if (childDicFileLength == 0) {
            //当前没有目录则创建一个

            File childDic = new File(headPath + childDicFileLength);
            childDic.mkdirs();
            return childDicFileLength;
        } else {
            int index = childDicFileLength - 1;
            File lastFile = files[index];
            String[] lastFileChild = lastFile.list();
            int lastFileChildLength = lastFileChild.length;
            if (lastFileChildLength >= 5000) {
                //超过该文件夹文件数目上限，则创建新的目录

                int resultPath = index + 1;
                File childDic = new File(headPath + resultPath);
                childDic.mkdirs();
                return resultPath;
            } else {
                return index;
            }
        }
    }

}