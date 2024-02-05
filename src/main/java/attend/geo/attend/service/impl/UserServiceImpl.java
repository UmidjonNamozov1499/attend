package attend.geo.attend.service.impl;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.UserDto;
import attend.geo.attend.dto.UserRequest;
import attend.geo.attend.entity.Images;
import attend.geo.attend.entity.User;
import attend.geo.attend.repository.ImageRepository;
import attend.geo.attend.repository.UserRepository;
import attend.geo.attend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final String upload = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadImage\\";


    @Override
    public HttpEntity<?> addUser(UserDto userDto) {
        try {
            if (userDto != null) {

                Random random = new Random();
                long min = 100_000L;
                long max = 999_999L;
                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;

                if (randomSixDigitNumber < 0) {
                    randomSixDigitNumber = -randomSixDigitNumber;
                }

                User user = new User();
                user.setUserName(userDto.getUserName());
                user.setIsBlocked(true);
                user.setRandomCode(randomSixDigitNumber);
                if (userDto.getTokens() != null && !userDto.getTokens().isEmpty()) {
                    List<Images> images = new ArrayList<>();
                    for (String token : userDto.getTokens()) {
                        Optional<Images> imagesOptional = imageRepository.findByToken(UUID.fromString(token));
                        imagesOptional.ifPresent(images::add);
                    }
                    user.setImages(images);
                }
                user = userRepository.save(user);
                return new HttpEntity<>("Successful add user");
            } else {
                return new HttpEntity<>("Error user not add");
            }
        } catch (Exception e) {
            return new HttpEntity<>("File exception");
        }
    }

    @Override
    public HttpEntity<?> addUserAndFile(MultipartFile file, List<MultipartFile> files, UserRequest request) {
        try {
            User user = new User();
            if (file == null && files != null && request != null) {
                List<Images> images = new ArrayList<>();
                for (MultipartFile multipartFile : files) {
                    Images images1 = new Images();
                    images1.setFileName(multipartFile.getName());
                    images1.setContentType(multipartFile.getContentType());
                    images1.setFileSize(multipartFile.getSize());
                    String originalName = multipartFile.getOriginalFilename();
                    String[] split = originalName.split("\\.");
                    String actualName = UUID.randomUUID() + "." + split[split.length - 1];
                    images1.setActualName(actualName);
                    imageRepository.save(images1);
                    images.add(images1);
                }

                user.setUserName(request.getUserName());
                user.setImages(images);

                Random random = new Random();
                long min = 100_000L;
                long max = 999_999L;
                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;

                if (randomSixDigitNumber < 0) {
                    randomSixDigitNumber = -randomSixDigitNumber;
                }
                user.setRandomCode(randomSixDigitNumber);
                user.setIsBlocked(true);
                user.setConnection(false);
                return ResponseEntity.ok("User success added");
            } else if (!file.isEmpty() && files == null && request != null) {
                List<Images> images = new ArrayList<>();
                Images image = new Images();
                image.setFileName(file.getName());
                image.setFileSize(file.getSize());
                image.setContentType(file.getContentType());
                String originalName = file.getOriginalFilename();
                String[] split = originalName.split("\\.");
                String actualName = UUID.randomUUID() + "." + split[split.length - 1];
                image.setActualName(actualName);
                images.add(image);
                Images save = imageRepository.save(image);
                file.transferTo(new File(upload + actualName));

                user.setUserName(request.getUserName());
                user.setImages(images);

                Random random = new Random();
                long min = 100_000L;
                long max = 999_999L;
                long randomSixDigitNumber = random.nextLong() % (max - min + 1) + min;

                if (randomSixDigitNumber < 0) {
                    randomSixDigitNumber = -randomSixDigitNumber;
                }
                user.setRandomCode(randomSixDigitNumber);
                user.setIsBlocked(true);
                user.setConnection(true);
                User userSave = userRepository.save(user);
                return ResponseEntity.ok(userSave);
            } else {
                return ResponseEntity.ok("Bad request");
            }
        } catch (IOException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @Override
    public HttpEntity<?> getAllUserRequest(GetAllUserRequest request) {

        try {
            if (request != null) {
                Page<User> all = userRepository.findAll(PageRequest.of(request.getPageNumber(), request.getPageSize()));
                List<User> userList = all.stream().map(r ->
                        new User(r.getId(),
                                r.getUserName(),
                                r.getRandomCode(),
                                r.getConnection(),
                                r.getDevice(),
                                r.getIsBlocked(),
                                r.getImages()
                        )).collect(Collectors.toList());
                return ResponseEntity.ok(userList);
            }
            else {
                return ResponseEntity.ok().body("Request Error");
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }



    @Override
    public HttpEntity<?> updateUser(UserDto userDto, Long id) {
        if (id != null & userDto != null) {
            Optional<User> byId = userRepository.findById(id);
            User user = byId.get();
            user.setId(id);
            user.setUserName(userDto.getUserName());

            if (userDto.getTokens() != null && !userDto.getTokens().isEmpty()) {
                List<Images> images = new ArrayList<>();
                for (String token : userDto.getTokens()) {
                    Optional<Images> imagesOptional = imageRepository.findByToken(UUID.fromString(token));
                    imagesOptional.ifPresent(images::add);
                }
                user.setImages(images);
            }
            userRepository.save(user);
        }
        return null;
    }
}


//D:\UzLITINEFTGAZ\SpringBootDownloadGetAPIDtaInExcelFile-master\attend\\uploadImage