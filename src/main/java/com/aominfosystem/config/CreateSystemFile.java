package com.aominfosystem.config;

import com.aominfosystem.utils.ConfigurationFile;
import com.aominfosystem.utils.ImportInfoFromText;

import java.io.*;

public class CreateSystemFile {

    public static String folderName = "aominfosystemConf";
    public static String configFileName = "aominfosystemConfig.ini";

    public CreateSystemFile() {
    }

    public static int createInifile() {


        createFolder();
        createConfigFile();
        return 0;
    }

    public static int createFolder() {
        File file = new File(folderName);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        return 0;
    }

    public static void createConfigFile() {

        File file = new File(folderName + "\\" + configFileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                String outString = "[AomInfoSystem]\r\n" +
                        ";设置插件系统采用本地数据模式\r\n" +
                        "usingLocalData=true\r\n" +
                        "[LocalConfig]\r\n" +
                        ";主人QQ(影响指令)\r\n" +
                        "adminNumberList=\r\n" +
                        ";QQ用户黑名单\r\n" +
                        "userBlacklist=45456566,23235656\r\n" +
                        ";群黑名单\r\n" +
                        "groupBlacklist=1212321,123123\r\n" +
                        ";采用前缀方式0为采用>_的形式1为.方式其他字符为使用该前缀\r\n" +
                        "instructionsPrefix=0\r\n" +
                        "[Draw]\r\n" +
                        ";冷却时间\r\n" +
                        "drawCooling=5000\r\n" +
                        ";抽奖冷却状态true为进入冷却状态\r\n" +
                        "cool=false\r\n" +
                        "[MessageRecord]\r\n" +
                        ";管理员QQ，不输入那么谁都可以开启消息记录指令，建议输入主人QQ，支持输入多个\r\n" +
                        "recordAdmin=\r\n" +
                        ";消息记录开关,false为消息记录功能关闭，true为激活，激活状态下，输入record指令才会开始记录\r\n" +
                        "switch=false\r\n" +
                        ";消息记录的群列表\r\n" +
                        "recordGroupList=123456,123456789\r\n" +
                        ";COC骰子设置\r\n" +
                        "[COCRoll]\r\n" +
                        "rollFaceNumber=100";

                ImportInfoFromText.writeToTxt(folderName + "\\" + "CardGroupDate.txt");

                writeFileContent(folderName + "\\" + configFileName, outString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 向文件中写入内容
     *
     * @param filepath 文件路径与名称
     * @param newstr   写入的内容
     * @return
     * @throws IOException
     */
    public static boolean writeFileContent(String filepath, String newstr) throws IOException {
        Boolean bool = false;
        String filein = newstr + "\r\n";//新写入的行，换行
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filepath);//文件路径(包括文件名称)
            //将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();

            //文件原有内容
            for (int i = 0; (temp = br.readLine()) != null; i++) {
                buffer.append(temp);
                // 行与行之间的分隔符 相当于“\n”
                buffer = buffer.append(System.getProperty("line.separator"));
            }
            buffer.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
            bool = true;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return bool;
    }

}
