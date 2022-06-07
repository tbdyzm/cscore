package com.longmao.run;

import com.longmao.enums.MODE;
import com.longmao.score.ValueScore;

/**
 * @Classname ValueScoreRun
 * @Description TODO
 * @Date 2022/1/12 21:58
 * @Created by zimu young
 */
public class ValueScoreRun {
    public static void main(String[] args) {
        ValueScore valueScore = new ValueScore(args[0], args[1]);

        MODE mode;
        if ("1".equals(args[2])){
            mode = MODE.MAX_EXPECT;
        }
        else {
            mode = MODE.EQUAL;
        }
        System.out.println(valueScore.calculateValueScore(mode));
    }
}
