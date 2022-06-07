package com.longmao.utils;

import com.longmao.enums.FILENAME;
import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

/**
 * @Classname encoderUtilsTest
 * @Description EncoderUtils测试用例
 * @Date 2021/12/29 15:56
 * @Created by zimu young
 */
public class EncoderUtilsTest implements IAbstractTest {
    private EncoderUtils encoderUtils;
    private String actualPath;
    private String expectPath;

    @BeforeTest
    public void initialize(){
        File tmp = new File("./");
        String path = tmp.getAbsolutePath().replace("\\.", "")+"\\src\\test\\resources\\cFile\\";
        this.actualPath = path + "actual\\";
        this.expectPath = path + "expect\\";
        String originalFName = path + FILENAME.originalFName;
        String formatSpecifierFName = this.actualPath + FILENAME.formatSpecifierFName;
        String replacedFormatSpecifierFName = this.actualPath + FILENAME.replacedFormatSpecifierFName;
        String markedFName = this.actualPath + FILENAME.markedFName;
        this.encoderUtils = new EncoderUtils(originalFName, formatSpecifierFName, replacedFormatSpecifierFName, markedFName);
    }

    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/EncoderUtils.xlsx", sheet = "GetFormatSpecifierPosition", dsArgs = "cPrintf,start,middle,expectPositions")
    public void testGetFormatSpecifierPosition(String cPrintf, String start, String middle, String expectPositions){
        LinkedHashMap<Integer, Integer> positions = this.encoderUtils.getFormatSpecifierPosition(cPrintf, Integer.parseInt(start), Integer.parseInt(middle));
        Assert.assertEquals(positions.toString(), expectPositions);
    }

    @Test(dataProvider = "DataProvider")
    @XlsDataSourceParameters(path = "xls/EncoderUtils.xlsx", sheet = "GetCurrentCline", dsArgs = "cLine,rCount,expectRCount,expectFSCLine,expectRFSCLine,expectMCLine")
    public void testGetCurrentCline(String cLine, String rCount, String expectRCount, String expectFSCLine, String expectRFSCLine, String expectMCLine){
        int actualRCount = this.encoderUtils.getCurrentCline(cLine, Integer.parseInt(rCount));
        Assert.assertEquals(actualRCount, Integer.parseInt(expectRCount));
        Assert.assertEquals(encoderUtils.getFormatSpecifierFileCline(), expectFSCLine);
        Assert.assertEquals(encoderUtils.getReplacedFormatSpecifierFileCline(), expectRFSCLine);
        Assert.assertEquals(encoderUtils.getMarkedFileCline(), expectMCLine);
    }

    @Test
    public void testCreateCFile() throws IOException {
        this.encoderUtils.createCFile();
        Path actualPath = Paths.get(this.actualPath);
        Path expectPath = Paths.get(this.expectPath);

        Path actualFSPath = actualPath.resolve(FILENAME.formatSpecifierFName);
        Path actualRFSPath = actualPath.resolve(FILENAME.replacedFormatSpecifierFName);
        Path actualMarkedPath = actualPath.resolve(FILENAME.markedFName);

        Path expectFSPath = expectPath.resolve(FILENAME.formatSpecifierFName);
        Path expectRFSPath = expectPath.resolve(FILENAME.replacedFormatSpecifierFName);
        Path expectMarkedPath = expectPath.resolve(FILENAME.markedFName);

        Assert.assertEquals(FileUtils.mismatch(actualFSPath, expectFSPath), -1);
        Assert.assertEquals(FileUtils.mismatch(actualRFSPath, expectRFSPath), -1);
        Assert.assertEquals(FileUtils.mismatch(actualMarkedPath, expectMarkedPath), -1);
    }
}
