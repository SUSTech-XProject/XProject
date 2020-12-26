package com.ooad.xproject.service.impl;

import com.ooad.xproject.bo.SvResult;
import com.ooad.xproject.config.FileConfig;
import com.ooad.xproject.entity.*;
import com.ooad.xproject.mapper.*;
import com.ooad.xproject.service.FileService;
import com.ooad.xproject.utils.ZipUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class FileServiceImpl implements FileService {
    private final ResourceMapper resourceMapper;
    private final ProjectMapper projectMapper;

    private final ProjectInstMapper projectInstMapper;

    private final SubmissionMapper submissionMapper;

    private final SchoolMapper schoolMapper;

    private final FileConfig fileConfig;

    public FileServiceImpl(ResourceMapper resourceMapper, ProjectMapper projectMapper, ProjectInstMapper projectInstMapper, SubmissionMapper submissionMapper, SchoolMapper schoolMapper, FileConfig fileConfig) {
        this.resourceMapper = resourceMapper;
        this.projectMapper = projectMapper;
        this.projectInstMapper = projectInstMapper;
        this.submissionMapper = submissionMapper;
        this.schoolMapper = schoolMapper;
        this.fileConfig = fileConfig;
    }

    @Override
    public ResponseEntity<byte[]> download(HttpServletRequest request, String realPath, String userAgent, String filename, boolean inline) {
        File file = new File(realPath);
        if (!file.exists()) {
            return (ResponseEntity<byte[]>) ResponseEntity.status(HttpStatus.NOT_FOUND);
        }
        // build response
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        bodyBuilder.contentLength(file.length());
        // bitstream
        bodyBuilder.contentType(MediaType.APPLICATION_OCTET_STREAM);
        // encode filename
        try {
            String encodeFileName = URLEncoder.encode(filename, StandardCharsets.UTF_8);
            // IE
            if (userAgent.indexOf("MSIE") > 0) {
                bodyBuilder.header("Content-Disposition", "attachment;filename=" + encodeFileName);
            } else if (inline) {
                // open in explorer
                URL url = new URL("file:///" + file);
                bodyBuilder.header("Content-Type", url.openConnection().getContentType());
                bodyBuilder.header("Content-Disposition", "inline;filename*=UTF-8''" + encodeFileName);
            } else {
                // download directly
                bodyBuilder.header("Content-Disposition", "attachment;filename*=UTF-8''" + encodeFileName);
            }
            // download successful
            return bodyBuilder.body(FileUtils.readFileToByteArray(file));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // return Error request
        return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
    }

    @Override
    public String upload(MultipartFile[] files, String uploadPath) {
        String filePath = "";
        for (MultipartFile file : files) {
            // get filename
            String originalFilename = file.getOriginalFilename();
            // rename to time + filename
            filePath = uploadPath + "\\" + originalFilename;
            try {
                // store the file
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    @Override
    public String upload(MultipartFile file, String uploadPath, String fileName) {
        String filePath = uploadPath + "\\" + fileName;
        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    @Override
    public int uploadResource(MultipartFile multipartFile, int projId, int creatorId) {
        Resource resource = new Resource();
        resource.setProjId(projId);
        resource.setCreatorRoleId(creatorId);
        resource.setSize(String.valueOf(multipartFile.getSize()));
        resource.setFileName(multipartFile.getOriginalFilename());
        int ret = resourceMapper.insert(resource);
        if (ret == 0)
            return 0;

        String filePath = getOrCreateResourceDir(projId, resource.getSrcId()).getPath();

        upload(multipartFile, filePath, multipartFile.getOriginalFilename());
        return ret;
    }

    private String getStorePath(School school, Project project, Submission submission, ProjectInst projectInst) {
        String dir = "\\school-" + school.getSchId() + "-" + school.getSchName() +
                "\\project-" + project.getProjId() + "-" + project.getProjName() +
                "\\submission-" + submission.getSbmId() + "-" + submission.getTitle() +
                "\\team-" + projectInst.getProjInstId() + "-" + projectInst.getTeamName();
        return dir;
    }

    private String getStorePath(School school, Project project, Submission submission) {
        String dir = "\\school-" + school.getSchId() + "-" + school.getSchName() +
                "\\project-" + project.getProjId() + "-" + project.getProjName() +
                "\\submission-" + submission.getSbmId() + "-" + submission.getTitle();
        return dir;
    }

    private String getResourcePath(School school, Project project, int srcId) {
        String dir = "\\school-" + school.getSchId() + "-" + school.getSchName() +
                "\\project-" + project.getProjId() + "-" + project.getProjName() +
                "\\resource-" + srcId;
        return dir;
    }

    @Override
    public void deleteFilesExceptFolder(File folder) {
        if (!folder.exists()) {
            return;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFilesExceptFolder(file);
                } else {
                    file.delete();
                }
            }
        }
    }

    @Override
    public void deleteFilesAndFolder(File folder) {
        if (!folder.exists()) {
            return;
        }
        deleteFilesExceptFolder(folder);
        folder.delete();
    }

    @Override
    public File getOrCreateStudentDir(SubmissionInst submissionInst) {
        ProjectInst projectInst = projectInstMapper.selectByPrimaryKey(submissionInst.getProjInstId());
        Submission submission = submissionMapper.selectByPrimaryKey(submissionInst.getSbmId());
        Project project = projectMapper.selectByPrimaryKey(submission.getProjId());
        School school = schoolMapper.selectByPrimaryKey(project.getSchId());
        String studentDir = getStorePath(school, project, submission, projectInst);
        String filePath = fileConfig.getUploadRoot() + "\\" + studentDir;
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
//        System.out.println(file.getPath() + "; " +  file.exists());
        return file;
    }

    @Override
    public File getSbmDir(int sbmId) {
        Submission submission = submissionMapper.selectByPrimaryKey(sbmId);
        Project project = projectMapper.selectByPrimaryKey(submission.getProjId());
        School school = schoolMapper.selectByPrimaryKey(project.getSchId());
        String sbmDir = getStorePath(school, project, submission);
        String filePath = fileConfig.getUploadRoot() + "\\" + sbmDir;
        return new File(filePath);
    }

    @Override
    public File getOrCreateResourceDir(int projId, int srcId) {
        Project project = projectMapper.selectByPrimaryKey(projId);
        School school = schoolMapper.selectByPrimaryKey(project.getSchId());

        String resDir = getResourcePath(school, project, srcId);
        String filePath = fileConfig.getResourceRoot() + "\\" + resDir;

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }


    public File getResDir(int srcId) {
        Resource resource = resourceMapper.selectByPrimaryKey(srcId);
        Project project = projectMapper.selectByPrimaryKey(resource.getProjId());
        School school = schoolMapper.selectByPrimaryKey(project.getSchId());

        String resDir = getResourcePath(school, project, srcId);
        String filePath = fileConfig.getResourceRoot() + "\\" + resDir;
        return new File(filePath);
    }

    @Override
    public SvResult<String> compressDir(File dir, String output) {
        try {
            OutputStream fileOutputStream = new FileOutputStream(output);
            ZipUtils.toZip(dir.getPath(), fileOutputStream, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new SvResult<>(0, "zip failed", output);
        }
        return new SvResult<>(1, "zip complete", output);
    }


}
