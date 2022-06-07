package com.longmao.score;

import com.longmao.enums.MODE;

import java.util.Arrays;

/**
 * @Classname FormatScore
 * @Description 根据actual.c文件和expect.c文件的输出打分
 * @Date 2022/1/7 16:02
 * @Created by zimu young
 */
public class FormatScore {
    private String actual;
    private String expect;

    public FormatScore(String actual, String expect){
        this.actual = actual;
        this.expect = expect;
    }

    /**
     * @description: 计算两个字符串的最长公共子序列的长度
     * @author: zimu young
     * @date: 2022/1/12
     * @param actual
     * @param expect
     * @return: int
     **/
    public int getLCS(String actual, String expect){
        int row = actual.length();
        int col = expect.length();
        int[][] num = new int[row+1][col+1];
        Arrays.fill(num[0], 0);
        for (int i = 0; i < row; i++){
            num[i][0] = 0;
        }

        for (int i= 1; i <= row; i++){
            for (int j = 1; j <= col; j++){
                if (actual.substring(i-1, i).equals(expect.substring(j-1, j))){
                    num[i][j] = num[i-1][j-1]+1;
                }
                else if (num[i-1][j] > num[i][j-1]){
                    num[i][j] = num[i-1][j];
                }
                else {
                    num[i][j] = num[i][j-1];
                }
            }
        }

        return num[row][col];
    }

    public String calculateFormatScore(MODE mode){
        int count = 0;
        int total = 0;
        String[] actual = this.actual.split("\n");
        String[] expect = this.expect.split("\n");

        for (int i = 0; i < actual.length && i < expect.length; i++){
            if (mode.equals(MODE.EQUAL)) {
                for (int j = 0; j < actual[i].length() && j < expect[i].length(); j++){
                    if (actual[i].substring(j, j+1).equals(expect[i].substring(j, j+1))){
                        count++;
                    }
                }
            }
            else {
                count = count + getLCS(actual[i], expect[i]);
            }
            if (actual[i].length() > expect[i].length()) {
                count = count - actual[i].length() + expect[i].length();
            }
            total += expect[i].length();
        }

        if (actual.length < expect.length){
            for (int i = actual.length; i < expect.length; i++){
                total += expect[i].length();
            }
        }
        else if (actual.length > expect.length){
            for (int i = expect.length; i < actual.length; i++){
                count -= actual[i].length();
            }
        }

        if (count < 0){
            return "0.00";
        }
        else {
            if (total > 0) {
                return String.format("%.2f", (double) (100 * count) / total);
            }
            else {
                return "100.00";
            }
        }
    }
}
