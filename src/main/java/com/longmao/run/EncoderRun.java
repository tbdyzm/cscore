package com.longmao.run;

import com.longmao.enums.FILENAME;
import com.longmao.utils.EncoderUtils;

import java.io.IOException;

/**
 * @Classname EncoderRun
 * @Description 根据原始.c文件生成格式说明符.c文件, 格式说明符替换为新的变量的.c文件名和原始.c文件在格式说明符前后插入标记后的新.c文件
 * @Date 2022/1/2 10:03
 * @Created by zimu young
 */
public class EncoderRun {
    public static void main(String[] args) throws IOException {
        String path = args[0] + "/";

        String originalFName = path + FILENAME.originalFName;
        String formatSpecifierFName = path + FILENAME.formatSpecifierFName;
        String replacedFormatSpecifierFName = path + FILENAME.replacedFormatSpecifierFName;
        String markedFName = path + FILENAME.markedFName;
        EncoderUtils encoderUtils = new EncoderUtils(originalFName, formatSpecifierFName, replacedFormatSpecifierFName, markedFName);
        encoderUtils.createCFile();
    }
}
