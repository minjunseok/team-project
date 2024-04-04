package moyeora.myapp.service.impl;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import moyeora.myapp.service.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class DefaultStorageService implements StorageService {

  private static Log log = LogFactory.getLog(DefaultStorageService.class);

  @Override
  public String upload(String bucketName, String path, MultipartFile multipartFile) throws Exception {
    try (InputStream fileIn = multipartFile.getInputStream()) {

      String filename = UUID.randomUUID().toString();
      String objectName = path + filename;

      log.info(String.format("%s(%s)",
          multipartFile.getOriginalFilename(),
          multipartFile.getContentType()));

      log.debug(String.format("Object %s has been created.\n", objectName));

      return filename;
    }
  }


 @Override
  public void delete(String bucketName, String path, String objectName) throws Exception {

    log.debug(String.format("Object %s has been deleted.\n", objectName));
  }
}