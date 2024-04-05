package moyeora.myapp.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.InputStream;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;


@Configuration
public class FileUploadHelper implements InitializingBean {

  final String endPoint;
  final String regionName;
  final String accessKey;
  final String secretKey;
  final String bucketName;

  final AmazonS3 s3;

  private static final Log log = LogFactory.getLog(FileUploadHelper.class);

  public FileUploadHelper(
    @Value("${ncp.storage.endpoint}") String endPoint,
    @Value("${ncp.storage.region}") String regionName,
    @Value("${ncp.storage.bucket}") String bucketName,
    @Value("${ncp.secretkey}") String accessKey,
    @Value("${ncp.accesskey}") String secretKey) {
    this.endPoint = endPoint;
    this.regionName = regionName;
    this.bucketName = bucketName;
    this.accessKey = accessKey;
    this.secretKey = secretKey;
    this.s3 = AmazonS3ClientBuilder.standard()
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, regionName))
      .withCredentials(
        new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
      .build();

  }

  @Override
  public void afterPropertiesSet() throws Exception {
    log.debug(String.format("endPoint : %s", this.endPoint));
    log.debug(String.format("reginName : %s", this.regionName));
    log.debug(String.format("accesskey : %s", this.accessKey));
    log.debug(String.format("secretkey : %s", this.secretKey));

  }

  public String upload(String path, MultipartFile multipartFile)
    throws Exception {
    try (InputStream fileIn = multipartFile.getInputStream()) {

      String filename = UUID.randomUUID().toString();
      String objectName = path + filename;

      //서버에 업로드할 파일의 정보를 준비
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(multipartFile.getContentType());

      //서버에 업로드 요청 정보 생성
      PutObjectRequest putObjectRequest = new PutObjectRequest(
        bucketName,
        objectName,
        fileIn,
        objectMetadata
      ).withCannedAcl(CannedAccessControlList.PublicRead);
      //서버에 업로드 실행
      s3.putObject(putObjectRequest);
      log.debug(String.format("Object %s has been created.\n", objectName));
      return filename;
    }
  }

  public void delete(String path, String objectName) throws Exception {
    s3.deleteObject(bucketName, path + objectName);
    log.debug(String.format("Object %s has been deleted.\n", objectName));
  }

}
