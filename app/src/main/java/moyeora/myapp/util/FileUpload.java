package moyeora.myapp.util;

import org.springframework.web.multipart.MultipartFile;

public interface FileUpload {

    public String upload(String bucketName, String path, MultipartFile multipartFile) throws Exception;

    void delete(String bucketName, String path, String objectName) throws Exception;
}
