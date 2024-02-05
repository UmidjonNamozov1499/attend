package attend.geo.attend.service.impl;

import attend.geo.attend.dto.*;
import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.payload.ApiResponse;
import attend.geo.attend.payload.Payload;
import attend.geo.attend.repository.UserAttendanceRepository;
import attend.geo.attend.service.DavomatService;
import attend.geo.attend.specification.UserAttendanceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class DavomatServiceImpl implements DavomatService {
    private final UserAttendanceRepository userAttendanceRepository;

    @Override
    public ResponseEntity<?> getAllUsersDavomat(Date start, Date endDate, GetAllUserRequest request) {
try {
    Specification<UserAttendance> dateRangeSpecification = UserAttendanceSpecification.findByDateRange(start, endDate);

    Page<UserAttendance> userAttendancePage = userAttendanceRepository.findAll(dateRangeSpecification, PageRequest.of(request.getPageNumber(), request.getPageSize()));

    List<UserAttendance> users = userAttendancePage.stream().map(r ->
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
    PageResponse response = new PageResponse(request.getPageSize(), request.getPageNumber());
    AllUserDavomatDto allUserDavomatDto = new AllUserDavomatDto(users, response);
    return new ResponseEntity<>(allUserDavomatDto, HttpStatus.OK);
}catch (Exception e){
    return ResponseEntity.ok().body(e.getMessage());
}
    }
}
