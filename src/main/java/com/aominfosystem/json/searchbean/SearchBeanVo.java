package com.aominfosystem.json.searchbean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.json.searchbean
 * @date:2019/9/2
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchBeanVo {

    private boolean success;
    private int total;
    private List<SongList> songList;
}
