package attend.geo.attend.service;

import attend.geo.attend.dto.AllUserDavomatDto;
import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.GetAllUsersPageRequest;
import attend.geo.attend.dto.UserAttendanceResponse;
import attend.geo.attend.entity.UserAttendance;
import org.apache.commons.collections4.Get;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface DavomatService {
    ResponseEntity<?> getAllUsersDavomat(Date start, Date endDate, GetAllUserRequest request);
}
