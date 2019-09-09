package com.aominfosystem.utils;

import com.aominfosystem.model.DrawDataFile;


import java.io.*;
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
    public static List<DrawDataFile> readFromText(String filePath) {
        String line_record = null;
        List<DrawDataFile> resultList = new ArrayList<DrawDataFile>();
        int count = 0;
        try {
            BufferedReader raf = new BufferedReader(new FileReader(filePath));
            while (null != (line_record = raf.readLine())) {
                //这里看操作系统的编码集,一般Windows中文默认是GBK,Linux是UTF-8
                //line_record = new String(line_record.getBytes(),"gb2312");
                DrawDataFile drawDataFile = parseRecord(line_record);
                resultList.add(drawDataFile);
                //System.out.println(drawDataFile.getProbability() + " : " + drawDataFile.getDescribe());
                count += 1;
            }
            raf.close();
            System.out.println("本次共导入数据 " + count + " 条");
        } catch (FileNotFoundException ef) {
            ef.printStackTrace();
            System.out.println(ef);
        } catch (IOException ei) {
            ei.printStackTrace();
            System.out.println(ei);
        }
        return resultList;
    }

    /**
     * @param line_record
     * @return
     */
    public static DrawDataFile parseRecord(String line_record) {
        DrawDataFile drawDataFile = new DrawDataFile();

        String[] fields = line_record.split(FIELLD_LIMIT_CHAR);//拆分块记录
        drawDataFile.setProbability(Double.parseDouble(fields[0]));
        drawDataFile.setDescribe(fields[1]);

        return drawDataFile;
    }

    public static void writeToTxt( String fileName) throws IOException {
        DrawDataFile drawDataFile = new DrawDataFile();
        //DrawDataFile.setFile_status("01");//对于文件状态,01表示未导出,02表示导出中,03表示已导出成功.
        List<DrawDataFile> drawDataFileList = new ArrayList<DrawDataFile>();//通过查询得到

        drawDataFile.setProbability(0.5);
        drawDataFile.setDescribe("this test describe");

        drawDataFileList.add(drawDataFile);
        drawDataFileList.add(drawDataFile);
        drawDataFileList.add(drawDataFile);
        int count = 0;
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(fileName));
        try {
            if (drawDataFileList.size() > 0) {
                for (int i = 0; i< drawDataFileList.size(); i++) {
                    out.write(String.valueOf(drawDataFileList.get(i).getProbability()) + "|");
                    out.write(drawDataFileList.get(i).getDescribe()+"\r\n");
                    //System.out.println(drawDataFileList.toString());
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

