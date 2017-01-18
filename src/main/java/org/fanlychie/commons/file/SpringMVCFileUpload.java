package org.fanlychie.commons.file;

import org.fanlychie.commons.file.exception.RuntimeCastException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * Spring MVC 文件上传
 * Created by fanlychie on 2017/1/13.
 */
public class SpringMVCFileUpload extends LocalFileUpload {

    /**
     * SpringMVC 文件对象数组
     */
    private MultipartFile[] files;

    /**
     * 创建一个 Spring MVC 文件上传对象
     *
     * @param files 文件数组
     */
    public SpringMVCFileUpload(MultipartFile[] files) {
        this.files = files;
    }

    /**
     * 执行文件上传
     *
     * @return 返回文件上传报告
     */
    @Override
    public FileUploadReport upload() {
        FileUploadReport report = new FileUploadReport();
        Arrays.stream(files).filter(file -> file != null && !file.isEmpty()).forEach(file -> {
            preFileUpload(report, file, file.getOriginalFilename(), file.getSize(), localFile -> {
                try {
                    file.transferTo(localFile);
                } catch (IOException e) {
                    throw new RuntimeCastException(e);
                }
            });
        });
        return report;
    }

}