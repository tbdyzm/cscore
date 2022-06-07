package com.longmao.score;

import com.longmao.enums.MODE;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ValueScore
 * @Description 根据实际代码和标准答案的格式说明符对应的变量和变量对应的值之间的json串打分
 * @Date 2022/1/7 16:02
 * @Created by zimu young
 */
public class ValueScore {
    private String actualJson;
    private String expectJson;
    private String[] actualKey;
    private String[] actualValue;
    private String[] expectKey;
    private String[] expectValue;

    public ValueScore(String actualJson, String expectJson){
        this.actualJson = actualJson;
        this.expectJson = expectJson;
    }

    public String[] getActualKey() {
        return actualKey;
    }

    public String[] getActualValue() {
        return actualValue;
    }

    public String[] getExpectKey() {
        return expectKey;
    }

    public String[] getExpectValue() {
        return expectValue;
    }

    private void getKV(String[] kvPair, String[] k, String[] v){
        for (int i = 0; i < kvPair.length; i++){
            String[] kv = kvPair[i].split(":");
            if (i == 0){
                k[i] = kv[0].replaceAll("\"", "").replaceAll("\\{", "");
                v[i] = kv[1];
            }
            else if (i == kvPair.length-1){
                k[i] = kv[0].replaceAll("\"", "");
                v[i] = kv[1].replaceAll("\\}", "");
            }
            else {
                k[i] = kv[0].replaceAll("\"", "");
                v[i] = kv[1];
            }
        }
    }

    public void getKVFromJson(){
        String[] actual = this.actualJson.split(",");
        this.actualKey = new String[actual.length];
        this.actualValue = new String[actual.length];
        this.getKV(actual, this.actualKey, this.actualValue);

        String[] expect = this.expectJson.split(",");
        this.expectKey = new String[expect.length];
        this.expectValue = new String[expect.length];
        this.getKV(expect, this.expectKey, this.expectValue);
    }

    /**
     * @description: 计算actualValue和expectValue的最长公共子序列包含的元素的个数(key值相等且value相等)
     * @author: zimu young
     * @date: 2022/1/12
     * @param mode 两种模式, 要么value相等要么actualValue为expectValue的子串
     * @return: int
     **/
    public int getLCS(MODE mode){
        int row = this.actualValue.length;
        int col = this.expectValue.length;
        int[][] num = new int[row+1][col+1];
        Arrays.fill(num[0], 0);
        for (int i = 0; i <= row; i++){
            num[i][0] = 0;
        }

        for (int i = 1; i <= row; i++){
            for (int j = 1; j <= col; j++){
                // 不允许actualValue成为expectValue的父串
                if (mode == MODE.MAX_EXPECT){
                    if (this.actualKey[i-1].equals(this.expectKey[j-1]) && this.expectValue[j-1].contains(this.actualValue[i-1]) && check(this.expectValue[j-1], this.actualValue[i-1])){
                        num[i][j] = num[i-1][j-1]+1;
                        continue;
                    }
                }
                else{
                    if (this.actualKey[i-1].equals(this.expectKey[j-1]) && this.expectValue[j-1].equals(this.actualValue[i-1])){
                        num[i][j] = num[i-1][j-1]+1;
                        continue;
                    }
                }
                if (num[i][j-1] > num[i-1][j]){
                    num[i][j] = num[i][j-1];
                }
                else {
                    num[i][j] = num[i-1][j];
                }
            }
        }

        return num[row][col];
    }

    /**
     * @description: 检查父串是否包含子串并且除了子串之外均为空格
     * @author: zimu young
     * @date: 2022/1/12
     * @param s 父串
     * @param substring 子串
     * @return: boolean
     **/
    public boolean check(String s, String substring){
        Pattern pattern = Pattern.compile(substring);
        Matcher matcher = pattern.matcher(s);

        int start;
        if (matcher.find()) {
            start = matcher.start();
        }
        else {
            return false;
        }
        int end = start+substring.length();

        if (start > 0 && !s.substring(0, start).trim().isEmpty()){
            return false;
        }
        else if (end < s.length() && !s.substring(end+1).trim().isEmpty()){
            return false;
        }
        else {
            return true;
        }
    }

    public String calculateValueScore(MODE mode){
        double score;

        this.getKVFromJson();
        int count = this.getLCS(mode);

        if (this.actualValue.length > this.expectValue.length){
            count = count - this.actualValue.length + this.expectValue.length;
        }
        if (count < 0){
            count = 0;
        }

        score = (double)(100*count)/this.expectValue.length;
        return String.format("%.2f", score);
    }
}
