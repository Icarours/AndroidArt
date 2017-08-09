package com.example;

import com.example.utils.FileUtils;

public class TestClass {
    public static void main(String[] args) {
        //创建目录
        String dirName = "F:/work/temp/temp0/temp1";
        FileUtils.createDir(dirName);
        //创建文件
        String fileName = dirName + "/temp2/tempFile.txt";
        FileUtils.createFile(fileName);
        //创建临时文件
        String prefix = "temp";
        String suffix = ".txt";
        for (int i = 0; i < 10; i++) {
            System.out.println("创建了临时文件："
                    + FileUtils.createTempFile(prefix, suffix, dirName));
        }
        //在默认目录下创建临时文件
        for (int i = 0; i < 10; i++) {
            System.out.println("在默认目录下创建了临时文件："
                    + FileUtils.createTempFile(prefix, suffix, null));
        }
    }
}
