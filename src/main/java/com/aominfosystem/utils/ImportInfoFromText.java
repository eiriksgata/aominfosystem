package com.aominfosystem.utils;

import com.aominfosystem.model.CardGroupFile;
import com.aominfosystem.pojo.Cardgroup;

import java.io.*;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;


public class ImportInfoFromText {
    public static final String OPEN_FILE_STYLE = "r"; //r=read
    public static final String FIELLD_LIMIT_CHAR = "\\|";//这里竖线需要被转义一下

    /**
     * 从文本中读取信息
     *
     * @param filePath
     */
    public static List<CardGroupFile> readFromText(String filePath) {
        String line_record = null;
        List<CardGroupFile> resultList = new ArrayList<CardGroupFile>();
        int count = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(filePath, OPEN_FILE_STYLE);
            while (null != (line_record = raf.readLine())) {
                //这里看操作系统的编码集,一般Windows中文默认是GBK,Linux是UTF-8
                line_record = new String(line_record.getBytes());
                CardGroupFile cardGroupFile = parseRecord(line_record);
                resultList.add(cardGroupFile);
                //System.out.println(cardGroupFile.getProbability() + " : " + cardGroupFile.getDescribe());
                count += 1;
            }
            raf.close();
            System.out.println("本次共导入数据 " + count + " 条");
        } catch (FileNotFoundException ef) {
            ef.printStackTrace();
        } catch (IOException ei) {
            ei.printStackTrace();
        }
        return resultList;
    }

    /**
     * @param line_record
     * @return
     */
    public static CardGroupFile parseRecord(String line_record) {
        CardGroupFile cardGroupFile = new CardGroupFile();

        String[] fields = line_record.split(FIELLD_LIMIT_CHAR);//拆分块记录
        cardGroupFile.setProbability(Double.parseDouble(fields[0]));
        cardGroupFile.setDescribe(fields[1]);

        return cardGroupFile;
    }

    public static void writeToTxt( String fileName) throws IOException {
        CardGroupFile cardGroupFile = new CardGroupFile();
        //CardGroupFile.setFile_status("01");//对于文件状态,01表示未导出,02表示导出中,03表示已导出成功.
        List<CardGroupFile> cardGroupFileList = new ArrayList<CardGroupFile>();//通过查询得到

        cardGroupFile.setProbability(0.5);
        cardGroupFile.setDescribe("this test describe");

        cardGroupFileList.add(cardGroupFile);
        cardGroupFileList.add(cardGroupFile);
        cardGroupFileList.add(cardGroupFile);
        int count = 0;
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName));
        try {
            if (cardGroupFileList.size() > 0) {
                for (int i=0;i<cardGroupFileList.size();i++) {
                    out.write(String.valueOf(cardGroupFileList.get(i).getProbability()) + "|");
                    out.write(cardGroupFileList.get(i).getDescribe()+"\r\n");
                    //System.out.println(cardGroupFileList.toString());
                    count++;
                }
                System.out.println("本次共成功导出文到文件 " + count + " 条");
            } else {
                System.out.println("未能查询到需要导出的数据");
            }
        } catch (Exception e) {
            System.out.println("writeToTxt error");
        } finally {
            out.flush();
            out.close();
        }
    }
}

