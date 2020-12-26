package com.ooad.xproject.controller;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.config.FileConfig;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.service.FileService;
import com.ooad.xproject.service.StudentService;
import com.ooad.xproject.service.SubmissionInstService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


@RunWith(SpringRunner.class)
@SpringBootTest
class FileControllerTest {
    @Autowired
    private StudentService studentService;

    @Autowired
    private FileController fileController;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private SubmissionInstService submissionInstService;

    @Test
    void testComment(){
        studentService.appendStdPayload(3, "testpayload1");
    }

    @Test
    void uploadToDir() {
        SubmissionInst submissionInst = new SubmissionInst();
        submissionInst.setSbmId(3);
        submissionInst.setProjInstId(43);
        submissionInst.setSubmitterId(3);
        MultipartFile[] files = new MockMultipartFile[1];

        if (submissionInstService.upsertSubmissionInst(submissionInst) == 0) {
            System.out.println("fail");
        }
        File file = fileService.getOrCreateStudentDir(submissionInst);

        fileService.deleteFilesExceptFolder(file);
//        fileService.upload(files, file.getPath());
    }

    @Test
    void getAllSbmFiles() {
        int sbmId = 1;
        File file = fileService.getSbmDir(sbmId);
        String realPath = "C:\\BCSpace\\JetProjects\\JavaProject\\xproject_backend\\business\\output" + "\\" + "output.zip";
        SvResult<String> svResult = fileService.compressDir(file, realPath);
//        return fileService.download(request, realPath, userAgent, filename, inline);
    }

    @Test
    void getDeleteResources() {
        int srcId = 5;
        System.out.println(fileController.getDeleteResources(srcId).toString());
    }

    @SneakyThrows
    @Test
    void postResources() {
        int projId, creatorId, fileFrom, fileTo;
        projId = 1;
        creatorId = 1;
        fileFrom = 1;
        fileTo = 3;

        for (int i = fileFrom; i <= fileTo; ++i) {
            InputStream inputStream = new FileInputStream("C:/BCSpace/Block-Chain-Learning/slides/res/A.png");
            MultipartFile file = new MockMultipartFile("mockFile-" + i + ".png", inputStream);
            System.out.println(fileService.uploadResource(file, projId, creatorId));
        }

    }

    @Test
    void postUploadSubmission() {


    }
}