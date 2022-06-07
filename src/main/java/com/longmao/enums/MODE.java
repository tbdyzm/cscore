package com.longmao.enums;

/**
 * @Classname MODE
 * @Description 格式说明符打分模式
 * @Date 2022/1/12 15:00
 * @Created by zimu young
 */
public enum MODE {
    MAX_EXPECT("max_expect"),
    EQUAL("equal");

    private String mode;

    MODE(String mode){
        this.mode = mode;
    }
}
