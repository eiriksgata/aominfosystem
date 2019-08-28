package com.aominfosystem.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


/**
 * Description: 封装httpClient响应结果
 *
 * @author JourWon
 * @date Created on 2018年4月19日
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HttpClientResult implements Serializable {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int scInternalServerError) {
    }
}