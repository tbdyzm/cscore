package com.longmao.utils;

import com.longmao.enums.FILENAME;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * @Classname DecoderUtilsTest
 * @Description DecoderUtils测试用例
 * @Date 2022/1/4 15:09
 * @Created by zimu young
 */
public class DecoderUtilsTest implements IAbstractTest {
    DecoderUtils decoderUtils;

    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/DecoderUtils.xlsx", sheet = "PutCLineVariables", dsArgs = "cLine,rCount,expectRCount,expectVariables")
    public void testPutCLineVariables(String cLine, String rCount, String expectRCount, String expectVariables){
        DecoderUtils decoderUtils = new DecoderUtils(null, null, null, null);
        int actualRCount = decoderUtils.putCLineVariables(cLine, Integer.parseInt(rCount));
        Assert.assertEquals(actualRCount, Integer.parseInt(expectRCount));
        Assert.assertEquals(decoderUtils.getVariables().toString(), expectVariables);
    }

    @Test
    public void initialize(){
        File tmp = new File("./");
        String path = tmp.getAbsolutePath().replace("\\.", "")+"\\src\\test\\resources\\cFile\\";
        String originalFName = path + FILENAME.originalFName;
        String sReplacedFormatSpecifier = "▁replaced[0]\n▁replaced[1], ▁replaced[2], ▁replaced[3]\n▁replaced[4], ▁replaced[5], ▁replaced[6], ▁replaced[7]\n▁replaced[8]\n▁replaced[9], ▁replaced[10]\n▁replaced[11]\n" +
                "▁replaced[12]\n▁replaced[13]\n▁replaced[14]\n▁replaced[15]\n▁replaced[16]\n▁replaced[17]\n▁replaced[18]\n▁replaced[19]\n▁replaced[20]\n▁replaced[21]\n▁replaced[0]\n▁replaced[1], ▁replaced[2], " +
                "▁replaced[3]\n▁replaced[4], ▁replaced[5], ▁replaced[6], ▁replaced[7]\n▁replaced[8]\n▁replaced[9], ▁replaced[10]\n% no format specifier %\n% format specifier ▁replaced[22]\n% no format specifier " +
                "%\n% no format specifier %\n% no format specifier %\n% multi format specifier in one line ▁replaced[23]\n▁replaced[24], ▁replaced[25], ▁replaced[26], ▁replaced[27]\n▁replaced[28], ▁replaced[29]\n" +
                "% no format specifier %\n% format specifier ▁replaced[30]\n▁replaced[31], ▁replaced[32], ▁replaced[33], ▁replaced[34]\n▁replaced[35], ▁replaced[36]\n";
        String sFormatSpecifier = "▁markedStart%a▁markedEnd\n" +
                "▁markedStart%c▁markedEnd, ▁markedStart%c▁markedEnd, ▁markedStart%c▁markedEnd\n▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd\n" +
                "▁markedStart%e▁markedEnd\n▁markedStart%f▁markedEnd, ▁markedStart%f▁markedEnd\n▁markedStart%g▁markedEnd\n▁markedStart%i▁markedEnd\n▁markedStart%o▁markedEnd\n▁markedStart%p▁markedEnd\n" +
                "▁markedStart%s▁markedEnd\n▁markedStart%u▁markedEnd\n▁markedStart%x▁markedEnd\n▁markedStart%A▁markedEnd\n▁markedStart%E▁markedEnd\n▁markedStart%G▁markedEnd\n▁markedStart%X▁markedEnd\n" +
                "▁markedStart%a▁markedEnd\n▁markedStart%c▁markedEnd, ▁markedStart%c▁markedEnd, ▁markedStart%c▁markedEnd\n▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, " +
                "▁markedStart%d▁markedEnd\n▁markedStart%e▁markedEnd\n▁markedStart%f▁markedEnd, ▁markedStart%f▁markedEnd\n% no format specifier %\n% format specifier ▁markedStart%-5.4f▁markedEnd\n" +
                "% no format specifier %\n% no format specifier %\n% no format specifier %\n% multi format specifier in one line ▁markedStart%-5.4f▁markedEnd\n▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, " +
                "▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd\n▁markedStart% #010.4le▁markedEnd, ▁markedStart%-+#10.4Le▁markedEnd\n% no format specifier %\n% format specifier ▁markedStart%" +
                "-5.4f▁markedEnd\n▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd, ▁markedStart%d▁markedEnd\n▁markedStart% 8jd▁markedEnd, ▁markedStart% 08jd▁markedEnd\n";
        String sMarked = "▁markedStart-0x1.47ae147ae147bp-7▁markedEnd\n▁markedStartc▁markedEnd, ▁markedStartc▁markedEnd, ▁markedStartc▁markedEnd\n▁markedStart-1▁markedEnd, ▁markedStart-1▁markedEnd, ▁markedStart-1" +
                "▁markedEnd, ▁markedStart1▁markedEnd\n▁markedStart-1.000000e-02▁markedEnd\n▁markedStart0.010000▁markedEnd, ▁markedStart-0.010000▁markedEnd\n▁markedStart0.01▁markedEnd\n▁markedStart-1" +
                "▁markedEnd\n▁markedStart37777777777▁markedEnd\n▁markedStart0x7ffed9f80582▁markedEnd\n▁markedStarthello▁markedEnd\n▁markedStart4294967295▁markedEnd\n▁markedStartffffffff▁markedEnd\n" +
                "▁markedStart-0X1.47AE147AE147BP-7▁markedEnd\n▁markedStart-1.000000E-02▁markedEnd\n▁markedStart0.01▁markedEnd\n▁markedStartFFFFFFFF▁markedEnd\n▁markedStart-0x1.47ae147ae147bp-7▁markedEnd\n" +
                "▁markedStartc▁markedEnd, ▁markedStartc▁markedEnd, ▁markedStartc▁markedEnd\n▁markedStart-1▁markedEnd, ▁markedStart-1▁markedEnd, ▁markedStart-1▁markedEnd, ▁markedStart1▁markedEnd\n" +
                "▁markedStart-1.000000e-02▁markedEnd\n▁markedStart0.010000▁markedEnd, ▁markedStart-0.010000▁markedEnd\n% no format specifier %\n% format specifier ▁markedStart0.0100▁markedEnd\n" +
                "% no format specifier %\n% no format specifier %\n% no format specifier %\n% multi format specifier in one line ▁markedStart0.0100▁markedEnd\n▁markedStart-1▁markedEnd, ▁markedStart-1" +
                "▁markedEnd, ▁markedStart-1▁markedEnd, ▁markedStart1▁markedEnd\n▁markedStart-1.0000e-02▁markedEnd, ▁markedStart-1.0000e-02▁markedEnd\n% no format specifier %\n% format specifier " +
                "▁markedStart0.0100▁markedEnd\n▁markedStart-1▁markedEnd, ▁markedStart-1▁markedEnd, ▁markedStart-1▁markedEnd, ▁markedStart1▁markedEnd\n▁markedStart      -1▁markedEnd, ▁markedStart-0000001" +
                "▁markedEnd";
        this.decoderUtils = new DecoderUtils(originalFName, sFormatSpecifier, sReplacedFormatSpecifier, sMarked);
    }

    @Test(dependsOnMethods = "initialize")
    public void testGetAllVariables() throws IOException {
        this.decoderUtils.getAllVariables();
        Assert.assertEquals(this.decoderUtils.getVariables().toString(), "{▁replaced[0]=d, ▁replaced[1]=c, ▁replaced[2]=uc, ▁replaced[3]=sc, ▁replaced[4]=i, ▁replaced[5]=ui, ▁replaced[6]=s, " +
                "▁replaced[7]=us, ▁replaced[8]=d, ▁replaced[9]=f, ▁replaced[10]=d, ▁replaced[11]=f, ▁replaced[12]=i, ▁replaced[13]=ui, ▁replaced[14]=str, ▁replaced[15]=str, ▁replaced[16]=ui, ▁replaced[17]=i, " +
                "▁replaced[18]=d, ▁replaced[19]=d, ▁replaced[20]=f, ▁replaced[21]=i, ▁replaced[22]=f, ▁replaced[23]=f, ▁replaced[24]=i, ▁replaced[25]=ui, ▁replaced[26]=s, ▁replaced[27]=us, ▁replaced[28]=d, " +
                "▁replaced[29]=ld, ▁replaced[30]=f, ▁replaced[31]=i, ▁replaced[32]=ui, ▁replaced[33]=s, ▁replaced[34]=us, ▁replaced[35]=l, ▁replaced[36]=ul}");
    }

    @Test(dependsOnMethods = {"testGetAllVariables"})
    public void testGetKeyFromRFS(){
        this.decoderUtils.getKeyFromRFS();
        Assert.assertEquals(this.decoderUtils.getKey().toString(), "[d, c, uc, sc, i, ui, s, us, d, f, d, f, i, ui, str, str, ui, i, d, d, f, i, d, c, uc, sc, i, ui, s, us, d, f, d, f, f, i, ui, s, us, d, ld, f, i, ui, s, us, l, ul]");
    }

    @Test(dependsOnMethods = {"initialize"})
    public void testGetValueFromFSAndMarked(){
        this.decoderUtils.getValueFromFSAndMarked();
        Assert.assertEquals(this.decoderUtils.getValue().toString(), "[-0x1.47ae147ae147bp-7, c, c, c, -1, -1, -1, 1, -1.000000e-02, 0.010000, -0.010000, 0.01, -1, 37777777777, 0x7ffed9f80582, hello, 4294967295, ffffffff, " +
                "-0X1.47AE147AE147BP-7, -1.000000E-02, 0.01, FFFFFFFF, -0x1.47ae147ae147bp-7, c, c, c, -1, -1, -1, 1, -1.000000e-02, 0.010000, -0.010000, 0.0100, 0.0100, -1, -1, -1, 1, -1.0000e-02, -1.0000e-02, 0.0100, -1, -1, -1, 1," +
                "       -1, -0000001]");
    }

    @Test(dependsOnMethods = {"testGetAllVariables", "testGetKeyFromRFS", "testGetValueFromFSAndMarked"})
    public void testGetJson() throws IOException{
        this.decoderUtils.setVariables(new LinkedHashMap<>());
        this.decoderUtils.setKey(new LinkedList<>());
        this.decoderUtils.setValue(new LinkedList<>());
        Assert.assertEquals(this.decoderUtils.getKey().size(), this.decoderUtils.getValue().size());
        Assert.assertEquals(this.decoderUtils.getJSON(), "{\"d\": -0x1.47ae147ae147bp-7,\"c\": c,\"uc\": c,\"sc\": c,\"i\": -1,\"ui\": -1,\"s\": -1,\"us\": 1,\"d\": -1.000000e-02,\"f\": 0.010000,\"d\": -0.010000,\"f\": 0.01," +
                "\"i\": -1,\"ui\": 37777777777,\"str\": 0x7ffed9f80582,\"str\": hello,\"ui\": 4294967295,\"i\": ffffffff,\"d\": -0X1.47AE147AE147BP-7,\"d\": -1.000000E-02,\"f\": 0.01,\"i\": FFFFFFFF,\"d\": -0x1.47ae147ae147bp-7,\"c\": c," +
                "\"uc\": c,\"sc\": c,\"i\": -1,\"ui\": -1,\"s\": -1,\"us\": 1,\"d\": -1.000000e-02,\"f\": 0.010000,\"d\": -0.010000,\"f\": 0.0100,\"f\": 0.0100,\"i\": -1,\"ui\": -1,\"s\": -1,\"us\": 1,\"d\": -1.0000e-02,\"ld\": -1.0000e-02," +
                "\"f\": 0.0100,\"i\": -1,\"ui\": -1,\"s\": -1,\"us\": 1,\"l\":       -1,\"ul\": -0000001}");
    }
}
