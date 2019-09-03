package com.aominfosystem.pulg.impl;

import com.aominfosystem.json.searchbean.SearchBeanVo;
import com.aominfosystem.json.searchbean.SongList;
import com.aominfosystem.pulg.MusicPulg;
import com.aominfosystem.utils.HttpClientUtils;
import com.aominfosystem.utils.RegularExpressionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sobte.cqp.jcq.event.JcqApp.CC;
import static com.sobte.cqp.jcq.event.JcqApp.CQ;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.pulg.impl
 * @date:2019/8/27
 **/
public class MusicPulgImpl implements MusicPulg {

    private String supportType[] = {"qq","163","xiami"};
    private String urlSupportType[] = {"qq","netease","xiami"};
    private String findAIPHost= "http://134.175.43.199/datageneration-Base/music/api/";

    //网易云 QQ 通用需格式化
    private String regexMusicId = "\"id\":\".*?\"";//5 -1 id
    private String regexMusicSinger = "\"singer\":\".*?\"";//9 -1 作者
    private String regexMusicName = "\"name\":\".*?\"" ;//格式化数据 歌名 7 -1

    @Override
    public String musicPlay(String parameter, long fromqq) {
        String result = "";
        String openType[] = supportType;
        String type = "163";
        long musicId = 31010566;
        //字符串处理
        String strlist[] = parameter.split(",");

        if (strlist.length > 0) {
            if (strlist.length > 1) {
                //暂时关闭qq查询
                if (!strlist[0].equals("qq")){
                    for (String anOpenType : openType) {
                        if (anOpenType.equals(strlist[0])) {
                            type = strlist[0];
                            break;
                        }
                    }
                    try {
                        if (type.equals("qq")){
                            result = "[CQ:music,id=" + strlist[1] + ",type=" + type + ",style=1" + "]";
                        }else {
                            result = "[CQ:music,id=" + strlist[1] + ",type=" + type + "]";
                        }
                        return result;
                    } catch (Exception e) {
                        System.out.println(e.toString());
                        CQ.logInfoRecv("用户错误的输入", "musicPlay Function input format error");
                    }
                }else {
                    result = "暂时不支持QQ播放，播放接口已经改变";
                }


            } else {
                result = "你输入的格式不对,请检查你的格式";
            }
        } else {
            result = "输入格式不对，长度不对";
        }

        return result;

    }

    @Override
    public String musicFind(String parameter, long fromqq) {

        String findType = supportType[0];
        String inputType = "song";
        String findApiHost = findAIPHost;
        String operationType = "search";
        String urlSupport = "netease";
        int limit = 5;
        int page = 1;

        StringBuilder apiResultStr = new StringBuilder();
        StringBuilder functionResult = new StringBuilder();
        //将空格改为%20
        parameter = parameter.replace(" ","%20");


        //字符串处理 0为查询平台 1为搜索字符串
        String strlist[] = parameter.split(",");
        //System.out.println(strlist.length);
        if (strlist.length>0&&strlist.length<3){
            for (int i=0;i<supportType.length;i++){
                if (supportType[i].equals(strlist[0])){
                    findType = supportType[i];
                    urlSupport = urlSupportType[i];
                    break;
                }
            }

            //http请求接口
            try {
                //处理url
                ObjectMapper mapper = new ObjectMapper();
                SearchBeanVo searchBeanVo;
                String apiUrl = findApiHost + operationType + "/" + inputType + "/" + urlSupport  + "?key=" +
                        strlist[1] + "&limit=" + limit + "&page=" + page;

                //findApi = findApi + "?keyword=" + strlist[1] + "&type=" + inputType + "&pageSize=" + pageSize + "&page=" + page + "&format=1";
                //System.out.println(apiUrl);

                apiResultStr.append(HttpClientUtils.doGet(apiUrl).getContent());
                searchBeanVo = mapper.readValue(apiResultStr.toString(),SearchBeanVo.class);
                List<SongList> searchSongList = searchBeanVo.getSongList();

                if (searchBeanVo.getSongList().size()>0){
                    functionResult.append("搜索到排行前5首歌曲");
                    functionResult.append("\n歌曲ID/歌曲名称/作者\n");
                    //整理返回的消息数据
                    for (int i=0;i<searchBeanVo.getSongList().size();i++){
                        //筛选字符串
                        functionResult.append("[").append(searchSongList.get(i).getId()).append("][")
                                .append(searchSongList.get(i).getName()).append("][");
                        for (int j=0;j<searchBeanVo.getSongList().get(i).getArtists().size();j++){
                            functionResult.append(searchSongList.get(i).getArtists().get(j).getName());
                            if (j!=searchSongList.get(i).getArtists().size()-1){
                                functionResult.append(",");
                            }
                        }
                        functionResult.append("]\n");
                    }
                    functionResult.append("[通过>_musicPlay ")
                            .append(findType)
                            .append(",")
                            .append(searchSongList.get(0).getId())
                            .append("这样的形式进行点歌]");

                }else {
                    functionResult.append("检测到解析的数据类型有误，故不返回消息内容");
                }

            } catch (Exception e) {
                e.printStackTrace();
                functionResult.append("请求接口数据出错，请检查接口类型是否可用");
            }
        }else {
            functionResult.append("输入的参数类型不符合要求，请检查输入的内容是否正确");
        }

        return functionResult.toString();
    }

    @Override
    public String musicPlayList(String paramenter) {
        return null;
    }

    @Override
    public String musicAdd(String parameter, long fromqq) {
        return null;
    }

    @Override
    public String musicDelete(String paramenter, long fromqq) {
        return null;
    }

    @Override
    public String musicDownload(String paramentr, long fromqq) {
        return null;
    }

    @Override
    public String returnMusicHelpMessage() {
        StringBuilder helpStr = new StringBuilder();
        helpStr.append("欢迎使用>_musicHelp指令，歌曲的指令使用情况如下:")
                .append("\n>_musicPlay X,Y X字符为点歌的类型，Y为歌曲ID号（目前只允许使用网易云音乐播放")
                .append("\n>_musicFind X,Y X为查询的平台，目前开放为qq,163,xiami")
                .append("\n163则是网易云音乐平台，qq则为qq音乐平台,xiami为虾米音乐。目前只允许这些的查询方式,Y则为查询的歌曲关键字")
                .append("\n例如：>_musicPlay 163,31010566")
                .append("\n>_musicFind 163,Sold Out")
                .append("\n尚未开通的功能：")
                .append("\n+歌曲下载链接")
                .append("\n+音乐播放列表")
                .append("\n+添加歌曲到播放列表")
                .append("\n+从歌曲列表中移除");
        return helpStr.toString();
    }
}
