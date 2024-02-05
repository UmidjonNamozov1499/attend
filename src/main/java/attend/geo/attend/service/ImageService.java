package attend.geo.attend.service;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service
public interface ImageService {
    HttpEntity<?> uploadFile(List<MultipartFile> files, MultipartFile fileOne);
    HttpEntity<?> updateFile(Long id,List<Long> ids,List<MultipartFile> files, MultipartFile fileOne);
}
