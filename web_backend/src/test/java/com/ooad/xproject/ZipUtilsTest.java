package com.ooad.xproject;

import com.ooad.xproject.utils.ZipUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

import static org.junit.jupiter.api.Assertions.*;

class ZipUtilsTest {

    @SneakyThrows
    @Test
    void toZip() {
        String filePath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\temp";
        String outputPath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output\\outputZip.zip";

        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
        ZipUtils.toZip(filePath, fileOutputStream, true);

    }

    @SneakyThrows
    @Test
    void testToZip() {

        String filePath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\temp";
        File file1 = new File(filePath + "\\A.png");
        File file2 = new File(filePath + "\\B.png");
        List<File> files = new ArrayList<>();
        files.add(file1);
        files.add(file2);
        String outputPath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output\\outputZip.zip";

        FileOutputStream fileOutputStream = new FileOutputStream(outputPath);

        ZipUtils.toZip(files, fileOutputStream);
    }

    @Test
    void unZipFiles() {
    }
}