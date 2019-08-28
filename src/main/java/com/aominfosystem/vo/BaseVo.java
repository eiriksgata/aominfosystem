package com.aominfosystem.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;

import java.sql.Timestamp;

/**
 * @author: create by Keith
 * @version: v1.0
 * @description: com.aominfosystem.vo
 * @date:2019/8/27
 **/
@Getter
@Setter
@NoArgsConstructor
public class BaseVo {
    private long code;
    private String msg;
    private Timestamp timestamp;
    private T data;
}
