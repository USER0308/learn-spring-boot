package com.utils;

import java.io.*;

public class FileUtil {
    // File类仅对文件和目录进行操作
    // IOStream类对字符流、字节流进行操作
    // FileReader/FileWriter,BufferedReader/BufferedWriter字符流操作
    // FileInputStream/FileOutputStream,BufferedInputStream/BufferedOutputStream字节流操作
    // InputStreamReader字节流转化为字符流/OutputStreamWriter字符流转化为字节流

    public static void writeFile(String filePath, String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            closeQuietly(fos);
        }

    }

    public static void writeAppendFile(String filePath, String content) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, true);
            fos.write(content.getBytes());
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            closeQuietly(fos);
        }

    }

    public static void writeFileWithBuffer(String filePath, String content) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            fos = new FileOutputStream(filePath);
            bos = new BufferedOutputStream(fos);
            bos.write(content.getBytes());
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } finally {
            closeQuietly(fos);
            closeQuietly(bos);
        }

    }

    public static void closeQuietly(OutputStream out) {
        if (null != out) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
