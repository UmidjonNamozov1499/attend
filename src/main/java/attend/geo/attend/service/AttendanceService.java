package attend.geo.attend.service;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.dto.GetStartEndAndEndDate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface AttendanceService {
    HttpEntity<?> readAllAttendance(GetAllUserRequest request, Date start, Date end);
}
