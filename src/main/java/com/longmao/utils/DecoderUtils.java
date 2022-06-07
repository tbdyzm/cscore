package com.longmao.utils;

import com.longmao.enums.MARKCONSTANT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname VariableBindingUtils
 * @Description 根据格式说明符, 替换后的格式说明符以及格式说明符前后加标记的三个.c文件的输出, 得到格式说明符对应的变量和它真实值之间绑定的json串
 * @Date 2021/12/30 16:23
 * @Created by zimu young
 */
public class DecoderUtils {
    private String originalFName; // 原始.c文件名

    private String sFormatSpecifier; // 格式说明符.c文件输出的字符串
    private String sReplacedFormatSpecifier; // 格式说明符被替换为新的变量.c文件输出的字符串
    private String sMarked; // 格式说明符前后插入标记.c文件输出的字符串

    private LinkedHashMap<String, String> variables; // 存储格式说明符替换后的变量名和格式说明符对应的变量名之间的绑定关系
    private LinkedList<String> key; // 以原始.c文件中格式说明符对应的变量为key, 按原始.c文件的输出, 依次存储输出的value对应的变量名
    private LinkedList<String> value; // 以原始.c文件中格式说明符对应的变量输出的值的value, 按原始.c文件, 依次存储输出的格式说明符对应的变量的值

    public void setVariables(LinkedHashMap<String, String> variables) {
        this.variables = variables;
    }

    public void setKey(LinkedList<String> key) {
        this.key = key;
    }

    public void setValue(LinkedList<String> value) {
        this.value = value;
    }

    public LinkedHashMap<String, String> getVariables() {
        return variables;
    }

    public LinkedList<String> getKey() {
        return key;
    }

    public LinkedList<String> getValue() {
        return value;
    }

    public DecoderUtils(String originalFName, String sFormatSpecifier, String sReplacedFormatSpecifier, String sMarked){
        this.originalFName = originalFName;
        this.sFormatSpecifier = sFormatSpecifier;
        this.sReplacedFormatSpecifier = sReplacedFormatSpecifier;
        this.sMarked = sMarked;
        this.variables = new LinkedHashMap<>();
        this.key = new LinkedList<>();
        this.value = new LinkedList<>();
    }

    /**
     * @description: 将包含printf和格式说明符的一行C代码中格式说明符对应的变量与格式说明符替换的变量进行绑定
     * @author: zimu young
     * @date: 2021/12/30
     * @param cLine
     * @param rCount
     * @return: int
     **/
    public int putCLineVariables(String cLine, int rCount){
        int start, middle, end; // start为第一个"出现的位置, middle为最后一个"出现的位置, end为最后一个)出现的位置
        start = middle = end = -1; // start < middle < end
        int vCount, fsCount; // vCount为格式说明符对应的变量个数计数, fsCount为格式说明符计数
        vCount = fsCount = 0;

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

        String[] sVariables = cLine.substring(middle+1, end).split(","); // 存储最后一个"和)之间的格式说明符对应的变量
        String actualVName; // 格式说明符对应的真实变量名
        for (int i = 0; i < sVariables.length; i++){
            actualVName = sVariables[i].replaceAll("\\s", "");
            if (actualVName.length() > 0){
                this.variables.put(MARKCONSTANT.replacedFormatSpecifierName+rCount+"]", actualVName);
                rCount++;
                vCount += 1;
            }
        }

        // 计算格式说明符的个数, 保持和encoder一样的逻辑
        String cPrintf = cLine.substring(start, middle+1);
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
                    fsCount++;
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
                            fsCount++;
                        }
                    }
                }
            }
        }
        rCount += fsCount-vCount;

        return rCount;
    }

    /**
     * @description: 查找所有格式说明符替换后的变量名和格式说明符对应的变量名之间的绑定关系
     * @author: zimu young
     * @date: 2021/12/30
     * @param
     * @return: void
     **/
    public void getAllVariables() throws IOException {
        String rFormatSpecifier = "%"; // 格式说明符正则表达式
        String rPrintf = "printf"; // printf语句正则表达式

        Pattern pFormatSpecifier = Pattern.compile(rFormatSpecifier);
        Pattern pPrintf = Pattern.compile(rPrintf);

        FileReader originalFR = new FileReader(this.originalFName);
        BufferedReader originalBR = new BufferedReader(originalFR);

        int rCount = 0; // 初始化格式说明符计数
        String cLine; // .c文件代码行
        while ((cLine = originalBR.readLine()) != null){
            Matcher mPrintf = pPrintf.matcher(cLine);
            Matcher mFormatSpecifier = pFormatSpecifier.matcher(cLine);
            // 行中没有printf或%
            if (!mPrintf.find() || !mFormatSpecifier.find())
                continue;

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

            // 只有一条printf语句
            if (i == 1){
                rCount = this.putCLineVariables(cLine, rCount);
            }
            // 代码行不止一条printf语句
            else {
                String[] multiCLine = new String[i]; // 存储多条包含printf的语句
                for (int j = 0; j < i; j++){
                    multiCLine[j] = cLine.substring(idxPrintf[j], idxPrintf[j+1]);
                }
                // 对于每一条printf语句, 格式说明符替换后的变量名和格式说明符对应的变量名之间的绑定关系存入variables中
                for (int j = 0; j < multiCLine.length; j++){
                    rCount = this.putCLineVariables(multiCLine[j], rCount);
                }
            }
        }

        originalBR.close();
        originalFR.close();
    }

    /**
     * @description: 从sReplacedFormatSpecifier中查找所有的替换后的变量, 将变量替换为变量对应的格式说明符对应的变量, 存入key中
     * @author: zimu young
     * @date: 2021/12/30
     * @param
     * @return: void
     **/
    public void getKeyFromRFS() {
        Pattern pReplaced = Pattern.compile(MARKCONSTANT.rReplacedFormatSpecifierName); // 格式串说明符替换后的变量的正则表达式
        Matcher mReplaced = pReplaced.matcher(this.sReplacedFormatSpecifier);

        int start, end;
        while (mReplaced.find()){
            start = mReplaced.start();
            end = start + MARKCONSTANT.replacedFormatSpecifierName.length()+1; // end >= start + MARKCONSTANT.replacedFormatSpecifierName.length()
            while (!"]".equals(this.sReplacedFormatSpecifier.substring(end, end+1)))
                end++;
            this.key.add(variables.get(this.sReplacedFormatSpecifier.substring(start, end+1)));
        }
    }

    /**
     * @description: 根据sFormatSpecifier和sMarked查找.c输出中所有格式说明符对应的变量对应的值, 存入value中
     * @author: zimu young
     * @date: 2021/12/30
     * @param
     * @return: void
     **/
    public void getValueFromFSAndMarked(){
        Pattern pMarkedStart = Pattern.compile(MARKCONSTANT.markedStart);
        Pattern pMarkedEnd = Pattern.compile(MARKCONSTANT.markedEnd);

        Matcher mFormatSpecifierStart = pMarkedStart.matcher(this.sFormatSpecifier);
        Matcher mFormatSpecifierEnd = pMarkedEnd.matcher(this.sFormatSpecifier);
        Matcher mMarkedStart = pMarkedStart.matcher(this.sMarked);
        Matcher mMarkedEnd = pMarkedEnd.matcher(this.sMarked);

        String formatSpecifier, value;
        int fsStart, fsEnd, vStart, vEnd;
        while (mFormatSpecifierStart.find() && mFormatSpecifierEnd.find() && mMarkedStart.find() && mMarkedEnd.find()){
            fsStart = mFormatSpecifierStart.start();
            fsEnd = mFormatSpecifierEnd.start();
            vStart = mMarkedStart.start();
            vEnd = mMarkedEnd.start();

            formatSpecifier = this.sFormatSpecifier.substring(fsStart+MARKCONSTANT.markedStart.length(), fsEnd);
            value = this.sMarked.substring(vStart+MARKCONSTANT.markedStart.length(), vEnd);
            if (!formatSpecifier.equals(value)) {
                this.value.add(value);
            }
        }
    }

    /**
     * @description: 根据key和value生成格式说明符对应的变量和值绑定的json串
     * @author: zimu young
     * @date: 2021/12/30
     * @param
     * @return: java.lang.String
     **/
    public String getJSON() throws IOException{
        this.getAllVariables();
        this.getKeyFromRFS();
        this.getValueFromFSAndMarked();

        int count = this.key.size();
        if (count == 0){
            return null;
        }
        else {
            String json = "{";
            for (int j = 0; j < count; j++){
                json += "\"" + this.key.get(j) + "\": " + this.value.get(j) +",";
            }
            json = json.substring(0, json.length()-1) + "}";
            return json;
        }
    }
}
