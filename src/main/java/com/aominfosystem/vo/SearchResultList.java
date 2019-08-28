package com.aominfosystem.vo;

import com.aominfosystem.vo.searchresultlits.Preview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.vo
 * @date:2019/8/27
 **/
public class SearchResultList {
    private Map<String,Integer> preview;
    private String songname_hilight;
    private int belongCD;
    private int newStatus;
    private List<HashMap<String,String>> singer;
    private String albumname_hilight;
    private String lyric_hilight;
}
