package moyeora.myapp.service;


import org.springframework.web.multipart.MultipartFile;

public interface StorageService {


    String upload(String path, MultipartFile multipartFile) throws Exception;

    void delete(String path, String objectName) throws Exception;
}
