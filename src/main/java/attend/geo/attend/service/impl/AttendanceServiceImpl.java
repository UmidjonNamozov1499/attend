package attend.geo.attend.service.impl;

import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.repository.UserAttendanceRepository;
import attend.geo.attend.service.AttendanceService;
import attend.geo.attend.specification.UserAttendanceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AttendanceServiceImpl implements AttendanceService {

    private final UserAttendanceRepository userAttendanceRepository;
    @Override
    public HttpEntity<?> readAllAttendance(GetAllUserRequest request, Date start, Date end) {
        try {
            if (request != null && start != null && end != null) {
                Specification<UserAttendance> specification = UserAttendanceSpecification.findByDateRange(start, end);
                Page<UserAttendance> all = userAttendanceRepository.findAll(specification, PageRequest.of(request.getPageNumber(),request.getPageSize()));
                List<UserAttendance> users = all.stream().map(r->
                        new UserAttendance(r.getId(),
                                r.getUserName(),
                                r.getStartDate(),
                                r.getEndDate(),
                                r.getWorkingHours(),
                                r.getItIsWork(),
                                r.getDate(),
                                r.getDeviceName(),
                                r.getIsReason()
                                )).collect(Collectors.toList());

                return ResponseEntity.ok(users);
            }else {
                return ResponseEntity.ok().body("Request error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
