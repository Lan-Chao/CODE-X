package com.silent.model;

import lombok.Data;

/**
 * @author zhaochangren
 * @Title: WeatherInfo
 * @ProjectName ai-athena-v2-backend
 * @Description: 天气
 * @date 2020/7/15 16:48
 */


@Data
public class WeatherInfo {

    private String time;
    /**  温度 */
    private String temp;
    private String icon;
    /** 实时天气 */
    private String condition;
    /** 风级 */
    private String windLevel;

}
