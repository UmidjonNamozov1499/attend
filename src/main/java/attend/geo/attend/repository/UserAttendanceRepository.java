package attend.geo.attend.repository;

import attend.geo.attend.entity.UserAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface UserAttendanceRepository extends JpaRepository<UserAttendance, Long>,JpaSpecificationExecutor<UserAttendance>, PagingAndSortingRepository<UserAttendance,Long> {


    List<UserAttendance> findByUserNameAndDateBetweenOrderByDateAsc(String userName, Date startDate, Date endDate);

    Page<UserAttendance> findAllByDateBetweenOrderByDateAsc(Date start, Date endDate, Pageable pageable);


}
