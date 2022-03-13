package com.dhl.yxg.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class WriteLogs {
    public void WriteLogToTXT(String content) {
        try {
            // 保存路径
            String path = "D://";
            String title = "logInfo";
            // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw
            /* 写入Txt文件 */
            File mkdirsName = new File(path);// 相对路径，如果没有则要建立一个新的output。txt文件
            if (!mkdirsName.exists()) {
                mkdirsName.mkdirs();
            }
            File writename = new File(path + "\\" + title + ".txt");// 相对路径，如果没有则要建立一个新的output。txt文件
            // 判断文件是否存在，不存在即新建
            // 存在即根据操作系统添加换行符
            if (!writename.exists()) {
                writename.createNewFile(); // 创建新文件
            } else {
                String osName = System.getProperties().getProperty("os.name");
                if (osName.equals("Linux")) {
                    content = "\r" + content;
                } else {
                    content = "\r\n" + content;
                }
            }
            // 如果是在原有基础上写入则append属性为true，默认为false
            BufferedWriter out = new BufferedWriter(new FileWriter(writename, true));
            out.write(content); // 写入TXT
            out.flush(); // 把缓存区内容压入文件
            out.close(); // 最后记得关闭文件
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}