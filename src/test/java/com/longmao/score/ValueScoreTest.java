package com.longmao.score;

import com.longmao.enums.MODE;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

/**
 * @Classname ValueScoreTest
 * @Description ValueScore测试用例
 * @Date 2022/1/10 15:21
 * @Created by zimu young
 */
public class ValueScoreTest implements IAbstractTest {
    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/ValueScore.xlsx", sheet = "GetKVFromJson", dsArgs = "actualJson,expectJson,actualKey,actualValue,expectKey,expectValue")
    public void testGetKVFromJson(String actualJson, String expectJson, String actualKey, String actualValue, String expectKey, String expectValue){
        ValueScore valueScore = new ValueScore(actualJson, expectJson);
        valueScore.getKVFromJson();

        System.out.println(Arrays.toString(valueScore.getActualKey()));
        System.out.println(Arrays.toString(valueScore.getActualValue()));
        System.out.println(Arrays.toString(valueScore.getExpectKey()));
        System.out.println(Arrays.toString(valueScore.getExpectValue()));
    }

    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/ValueScore.xlsx", sheet = "GetLCS", dsArgs = "actualJson,expectJson,m,expectCount")
    public void testGetLCS(String actualJson, String expectJson, String m, String expectCount){
        ValueScore valueScore = new ValueScore(actualJson, expectJson);
        valueScore.getKVFromJson();
        MODE mode;
        if ("equal".equals(m)){
            mode = MODE.EQUAL;
        }
        else {
            mode = MODE.MAX_EXPECT;
        }
        int count = valueScore.getLCS(mode);
        Assert.assertEquals(count, Integer.parseInt(expectCount));
    }

    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/ValueScore.xlsx", sheet = "CalculateValueScore", dsArgs = "actualJson,expectJson,m,expectScore")
    public void testCalculateValueScore(String actualJson, String expectJson, String m, String expectScore){
        ValueScore valueScore = new ValueScore(actualJson, expectJson);

        MODE mode;
        if ("equal".equals(m)){
            mode = MODE.EQUAL;
        }
        else {
            mode = MODE.MAX_EXPECT;
        }
        String score = valueScore.calculateValueScore(mode);
        Assert.assertEquals(score, expectScore);
    }
}
