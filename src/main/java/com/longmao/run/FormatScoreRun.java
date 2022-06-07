package com.longmao.run;

import com.longmao.enums.MODE;
import com.longmao.score.FormatScore;

/**
 * @Classname FormatScoreRun
 * @Description TODO
 * @Date 2022/1/12 21:58
 * @Created by zimu young
 */
public class FormatScoreRun {
    public static void main(String[] args) {
        FormatScore formatScore = new FormatScore(args[0], args[1]);

        MODE mode;
        if ("1".equals(args[2])){
            mode = MODE.MAX_EXPECT;
        }
        else {
            mode = MODE.EQUAL;
        }
        System.out.println(formatScore.calculateFormatScore(mode));
    }
}
