package attend.geo.attend.service;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.UserDto;
import attend.geo.attend.dto.UserRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    HttpEntity<?> addUser(UserDto request);
    HttpEntity<?> addUserAndFile(MultipartFile multipartFile, List<MultipartFile> files, UserRequest request);
    HttpEntity<?> getAllUserRequest(GetAllUserRequest request);
    HttpEntity<?> updateUser(UserDto userDto,Long id);
}
