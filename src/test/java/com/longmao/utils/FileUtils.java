package com.longmao.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static com.sun.jersey.core.util.ReaderWriter.BUFFER_SIZE;

/**
 * @Classname FileUtils
 * @Description TODO
 * @Date 2021/12/30 16:10
 * @Created by zimu young
 */
public class FileUtils {
    public static long mismatch(Path path, Path path2) throws IOException {
        if (Files.isSameFile(path, path2)) {
            return -1;
        }
        byte[] buffer1 = new byte[BUFFER_SIZE];
        byte[] buffer2 = new byte[BUFFER_SIZE];
        try (InputStream in1 = Files.newInputStream(path);
             InputStream in2 = Files.newInputStream(path2);) {
            long totalRead = 0;
            while (true) {
                int nRead1 = in1.readNBytes(buffer1, 0, BUFFER_SIZE);
                int nRead2 = in2.readNBytes(buffer2, 0, BUFFER_SIZE);

                int i = Arrays.mismatch(buffer1, 0, nRead1, buffer2, 0, nRead2);
                if (i > -1) {
                    return totalRead + i;
                }
                if (nRead1 < BUFFER_SIZE) {
                    // we've reached the end of the files, but found no mismatch
                    return -1;
                }
                totalRead += nRead1;
            }
        }
    }
}
