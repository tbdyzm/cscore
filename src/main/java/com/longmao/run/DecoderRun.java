package com.longmao.run;

import com.longmao.enums.FILENAME;
import com.longmao.utils.DecoderUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Classname DecoderRun
 * @Description 根据原始.c文件, 格式说明符.c文件, 格式说明符替换为新的变量的.c文件名和原始.c文件在格式说明符前后插入标记后的新.c文件的输出得到格式说明符对应的变量和变量对应的值之间的json串
 * @Date 2022/1/2 10:06
 * @Created by zimu young
 */
public class DecoderRun {
    public static void main(String[] args) throws IOException {
        String path = args[0] + "/";
        String originalFName = path + FILENAME.originalFName;
        String sFormatSpecifier = args[1];
        String sReplacedFormatSpecifier = args[2];
        String sMarked = args[3];

        DecoderUtils decoderUtils = new DecoderUtils(originalFName, sFormatSpecifier, sReplacedFormatSpecifier, sMarked);
        System.out.println(decoderUtils.getJSON());

        String formatSpecifierFName = path + FILENAME.formatSpecifierFName;
        String replacedFormatSpecifierFName = path + FILENAME.replacedFormatSpecifierFName;
        String markedFName = path + FILENAME.markedFName;

        Path formatSpecifierPath = Paths.get(formatSpecifierFName);
        Path replacedFormatSpecifierPath = Paths.get(replacedFormatSpecifierFName);
        Path markedPath = Paths.get(markedFName);
        Files.deleteIfExists(formatSpecifierPath);
        Files.deleteIfExists(replacedFormatSpecifierPath);
        Files.deleteIfExists(markedPath);
    }
}
