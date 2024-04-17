package moyeora.myapp.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import moyeora.myapp.vo.AttachedFile;

@Component
public class MultipartFileToAttachedFileConverter implements Converter<MultipartFile, AttachedFile> {

    @Override
    public AttachedFile convert(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            return null;
        }

        // MultipartFile을 AttachedFile로 변환하여 반환
        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setFileName(multipartFile.getOriginalFilename());
        // 나머지 필드 설정

        return attachedFile;
    }
}
