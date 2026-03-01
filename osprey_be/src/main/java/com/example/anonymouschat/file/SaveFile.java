package com.example.anonymouschat.file;

import com.example.anonymouschat.constant.SystemMsg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {


    //存储聊天日志信息
    //建议命名方式改为备份时间
    public static void save(String fileName , String message){
		try {
		    // 创建一个新文件
		    File file = new File(SystemMsg.TALKING_MESSAGE_FILE_PATH.msg + fileName);
		    if (file.createNewFile()) {
				System.out.println("文件创建成功： " + file.getName());
		    } else {
				System.out.println("文件已存在.");
		    }
		    // 使用FileWriter将内容写入文件
		    FileWriter writer = new FileWriter(file);
		    writer.write(message);
		    writer.close();
		    System.out.println("成功写入文件.");
		} catch (IOException e) {
		    System.out.println("发生错误.");
		    e.printStackTrace();
		}
    }

}
