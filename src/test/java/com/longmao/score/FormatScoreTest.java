package com.longmao.score;

import com.longmao.enums.MODE;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import org.testng.Assert;
import org.testng.annotations.Test;


/**
 * @Classname FormatScoreTest
 * @Description FormatScore测试用例
 * @Date 2022/1/12 21:19
 * @Created by zimu young
 */
public class FormatScoreTest implements IAbstractTest {
    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/FormatScore.xlsx", sheet = "GetLCS", dsArgs = "actual,expect,expectCount")
    public void testGetLCS(String actual, String expect, String expectCount){
        FormatScore formatScore = new FormatScore(null, null);
        int count = formatScore.getLCS(actual, expect);
        Assert.assertEquals(count, Integer.parseInt(expectCount));
    }

    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/FormatScore.xlsx", sheet = "CalculateFormatScore", dsArgs = "actual,expect,m,expectScore")
    public void testCalculateFormatScore(String actual, String expect, String m, String expectScore){
        FormatScore formatScore = new FormatScore(actual, expect);

        MODE mode;
        if ("max_expect".equals(m)){
            mode = MODE.MAX_EXPECT;
        }
        else{
            mode = MODE.EQUAL;
        }
        String score = formatScore.calculateFormatScore(mode);
        Assert.assertEquals(score, expectScore);
    }
}
