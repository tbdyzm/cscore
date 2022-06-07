package com.longmao.utils;

import com.longmao.enums.MARKCONSTANT;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname Generator
 * @Description 根据原始.c文件生成格式说明符.c文件, 替换格式说明符.c文件(格式说明符替换不可剩余, 不可替换非格式说明符)和格式说明符前后加标记.c文件
 * @Date 2021/12/28 16:08
 * @Author zimu young
 */
public class EncoderUtils {
    private String originalFName; // 原始.c文件名
    private String formatSpecifierFName; // 格式说明符.c文件名, 该.c文件编译运行只输出格式说明符如%d而不输出格式说明符对应的变量的值
    private String replacedFormatSpecifierFName; // 格式说明符替换为新的变量的.c文件名, 该.c文件编译运行将格式串部分替换为新的变量输出
    private String markedFName; // 原始.c文件在格式说明符前后插入标记后的新.c文件名
    private String formatSpecifierFileCline; // formatSpecifierFName.c文件的一行
    private String replacedFormatSpecifierFileCline; // replacedFormatSpecifierFName.c文件的一行
    private String markedFileCline; // markedFName.c文件的一行

    public EncoderUtils(String originalFName, String formatSpecifierFName, String replacedFormatSpecifierFName, String markedFName){
        this.originalFName = originalFName;
        this.formatSpecifierFName = formatSpecifierFName;
        this.replacedFormatSpecifierFName = replacedFormatSpecifierFName;
        this.markedFName = markedFName;
    }

    public String getFormatSpecifierFileCline() {
        return formatSpecifierFileCline;
    }

    public String getReplacedFormatSpecifierFileCline() {
        return replacedFormatSpecifierFileCline;
    }

    public String getMarkedFileCline() {
        return markedFileCline;
    }

    /**
     * @description: 返回格式说明符在printf语句中的位置
     * @author: zimu young
     * @date: 2021/12/28
     * @param cPrintf c语言printf语句中两个"之间的字符串, 每次只有一条printf语句
     * @param start printf语句中第一个"出现的位置
     * @param middle printf语句中最后一个"出现的位置
     * @return: java.util.LinkedHashMap<java.lang.Integer,java.lang.Integer>
     **/
    public LinkedHashMap<Integer, Integer> getFormatSpecifierPosition(String cPrintf, int start, int middle){
        LinkedHashMap<Integer, Integer> positions = new LinkedHashMap<>(); // 存储格式说明符在printf语句中的位置

        int fsStart = 0; // 格式说明符起始位置
        int fsEnd = 0; // 格式说明符终止位置
        String formatSpecifier; // 存储格式说明符

        for (int i = 1; i < middle-start; i++){
            // cPrint中找到疑似格式说明符
            if ("%".equals(cPrintf.substring(i, i+1)) && i != middle-start-1) {
                fsStart = i;
                // 寻找格式说明符的终止位置
                while (!cPrintf.substring(i, i + 1).matches("[a|c|d|e|f|g|i|o|p|s|u|x|A|E|G|X]")) {
                    if (i != middle - start - 1) {
                        i++;
                    } else break;
                }
                fsEnd = i;
                formatSpecifier = cPrintf.substring(fsStart, fsEnd + 1);
                // fsEnd - fsStart = 1时, formatSpecifier必为格式说明符
                if (fsEnd - fsStart == 1) {
                    positions.put(start + fsStart, start + fsEnd);
                } else {
                    // 判断%和[c|d|e|f|g|o|s|x|u|E|G|X]之间的字符是否为非法字符
                    for (int j = 1; j < fsEnd - fsStart; j++) {
                        // 疑似格式说明符中包含非法字符
                        if (!formatSpecifier.substring(j, j + 1).matches("[0-9|+|\\-|\\s|#|.|h|j|l|t|z|L]")) {
                            i = fsStart + 1; // 下一次搜索的位置从当前疑似格式串起始位置的第2个位置开始
                            break;
                        }
                        // 判断到最后一个字符之前的字符仍不是非法字符, 该疑似格式说明符是格式说明符(格式说明符%造成输出warning时不考虑)
                        if (j == fsEnd - fsStart - 1) {
                            positions.put(start + fsStart, start + fsEnd);
                        }
                    }
                }
            }
        }

        return positions;
    }

    /**
     * @description: 根据当前包含格式说明符的printf语句和格式说明符的位置得到写入三个.c文件的新的printf语句
     * @author: zimu young
     * @date: 2021/12/28
     * @param cLine 当前包含格式说明符的printf语句
     * @param rCount 计数替换格式说明符的次数
     * @return: void
     **/
    public int getCurrentCline(String cLine, int rCount){
        int start, middle, end; // start为第一个"出现的位置, middle为最后一个"出现的位置, end为最后一个)出现的位置
        start = middle = end = -1; // start < middle < end

        for (int i = 0; i < cLine.length(); i++){
            // printf语句中, 第一个位置和最后一个位置的"之间出现的"都以\"的形式出现
            if ("\"".equals(cLine.substring(i, i+1)) && !"\\".equals(cLine.substring(i-1, i))){
                if (start < 0){
                    start = i;
                    continue;
                }
                if (middle < 0){
                    middle = i;
                    continue;
                }
            }
            // 最后一次出现)时middle>0, 否则在start和end之间是有概率出现)的
            if (")".equals(cLine.substring(i, i+1)) && middle > 0){
                end = i;
                break;
            }
        }

        // 初始化当前待写入三个文件的printf语句
        this.formatSpecifierFileCline = cLine.substring(0, start); // 格式说明符前后加标记并输出格式说明符而不输出格式格式说明符对应变量的值
        this.replacedFormatSpecifierFileCline = cLine.substring(0, start); // 将格式说明符替换为新的变量名
        this.markedFileCline = cLine.substring(0, start); // 在格式说明符前后加标记并输出格式说明符对应的变量的值

        StringBuilder formatSpecifierFileClineBuilder = new StringBuilder(this.formatSpecifierFileCline);
        StringBuilder replacedFormatSpecifierFileClineBuilder = new StringBuilder(this.replacedFormatSpecifierFileCline);
        StringBuilder markedFileClineBuilder = new StringBuilder(markedFileCline);

        int fsStart, fsEnd;
        fsStart = start;

        LinkedHashMap<Integer, Integer> positions = getFormatSpecifierPosition(cLine.substring(start, middle+1), start, middle);
        for (Map.Entry<Integer, Integer> integerIntegerEntry : positions.entrySet()) {
            Map.Entry<Integer, Integer> position = integerIntegerEntry;
            Object key = position.getKey();
            Object value = position.getValue();
            fsEnd = Integer.parseInt(key.toString());

            formatSpecifierFileClineBuilder.append(cLine, fsStart, fsEnd).append(MARKCONSTANT.markedStart).append("%");
            replacedFormatSpecifierFileClineBuilder.append(cLine, fsStart, fsEnd).append(MARKCONSTANT.replacedFormatSpecifierName).append(rCount).append("]");
            markedFileClineBuilder.append(cLine, fsStart, fsEnd).append(MARKCONSTANT.markedStart);

            fsStart = fsEnd;
            fsEnd = Integer.parseInt(value.toString()) + 1;
            formatSpecifierFileClineBuilder.append(cLine, fsStart, fsEnd).append(MARKCONSTANT.markedEnd);
            markedFileClineBuilder.append(cLine, fsStart, fsEnd).append(MARKCONSTANT.markedEnd);

            fsStart = fsEnd;
            rCount++;
        }

        this.formatSpecifierFileCline = formatSpecifierFileClineBuilder + cLine.substring(fsStart, middle+1) + cLine.substring(end);
        this.replacedFormatSpecifierFileCline = replacedFormatSpecifierFileClineBuilder + cLine.substring(fsStart, middle+1) + cLine.substring(end);
        this.markedFileCline = markedFileClineBuilder + cLine.substring(fsStart);

        return rCount;
    }

    public void createCFile() throws IOException {
        String rFormatSpecifier = "%"; // 格式说明符正则表达式
        String rPrintf = "printf"; // printf语句正则表达式

        Pattern pFormatSpecifier = Pattern.compile(rFormatSpecifier);
        Pattern pPrintf = Pattern.compile(rPrintf);

        FileReader originalFR = new FileReader(this.originalFName);
        BufferedReader originalBR = new BufferedReader(originalFR);
        FileWriter formatSpecifierFW = new FileWriter(this.formatSpecifierFName);
        BufferedWriter formatSpecifierBW = new BufferedWriter(formatSpecifierFW);
        FileWriter replaceFormatSpecifierFW = new FileWriter(this.replacedFormatSpecifierFName);
        BufferedWriter replaceFormatSpecifierBW = new BufferedWriter(replaceFormatSpecifierFW);
        FileWriter markedFW = new FileWriter(this.markedFName);
        BufferedWriter markedBW = new BufferedWriter(markedFW);

        int rCount = 0; // 初始化格式说明符计数
        String cLine; // .c文件代码行
        while ((cLine = originalBR.readLine()) != null){
            Matcher mPrintf = pPrintf.matcher(cLine);
            Matcher mFormatSpecifier = pFormatSpecifier.matcher(cLine);
            // 行中没有printf或%
            if (!mPrintf.find() || !mFormatSpecifier.find()){
                formatSpecifierBW.write(cLine);
                formatSpecifierBW.newLine();
                replaceFormatSpecifierBW.write(cLine);
                replaceFormatSpecifierBW.newLine();
                markedBW.write(cLine);
                markedBW.newLine();
                continue;
            }

            // 记录每个printf子串在代码行出现的起始位置
            mPrintf = pPrintf.matcher(cLine);
            int[] idxPrintf = new int[cLine.length()/6]; // cLine.length()>=12
            Arrays.fill(idxPrintf, -1);
            int i = 0;
            while (mPrintf.find()){
                idxPrintf[i] = mPrintf.start();
                i++;
            }
            idxPrintf[i] = cLine.length();

            // 代码行只有一条printf语句
            if (i == 1){
                rCount = getCurrentCline(cLine, rCount);
                formatSpecifierBW.write(this.formatSpecifierFileCline);
                formatSpecifierBW.newLine();
                replaceFormatSpecifierBW.write(this.replacedFormatSpecifierFileCline);
                replaceFormatSpecifierBW.newLine();
                markedBW.write(this.markedFileCline);
                markedBW.newLine();
            }
            // 代码行不止一条printf语句
            else {
                String[] multiCLine = new String[i]; // 存储多条包含printf的语句
                for (int j = 0; j < i; j++){
                    if (j == 0)
                        multiCLine[j] = cLine.substring(0, idxPrintf[1]);
                    else
                        multiCLine[j] = cLine.substring(idxPrintf[j], idxPrintf[j+1]);
                }
                // 对于每一条printf语句, 得到写入三个.c文件的新的printf语句
                for (int j = 0; j < multiCLine.length; j++){
                    rCount = getCurrentCline(multiCLine[j], rCount);
                    formatSpecifierBW.write(this.formatSpecifierFileCline);
                    replaceFormatSpecifierBW.write(this.replacedFormatSpecifierFileCline);
                    markedBW.write(this.markedFileCline);
                }
                formatSpecifierBW.newLine();
                replaceFormatSpecifierBW.newLine();
                markedBW.newLine();
            }
        }

        originalBR.close();
        originalFR.close();
        formatSpecifierBW.close();
        formatSpecifierFW.close();
        replaceFormatSpecifierBW.close();
        replaceFormatSpecifierFW.close();
        markedBW.close();
        markedFW.close();
    }
}
