package attend.geo.attend.specification;

import attend.geo.attend.entity.UserAttendance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAttendanceSpecification {
    public static Specification<UserAttendance> dateBetween(Date startDate, Date endDate) {
        return (Root<UserAttendance> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) ->
                criteriaBuilder.between(root.get("date"), startDate, endDate);
    }
    public static Specification<UserAttendance> findByDateRange(Date start, Date endDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (start != null && endDate != null) {
                predicates.add(criteriaBuilder.between(root.get("date"), start, endDate));
            } else if (start != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), start));
            } else if (endDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public static Specification<UserAttendance> findByStartDate(Date start) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("date"), start);
    }

    public static Specification<UserAttendance> findByEndDate(Date end) {
        return (root, query, criteriaBuilder) -> end != null ?
                criteriaBuilder.equal(root.get("date"), end) : query.getGroupRestriction();
    }

//    public static Specification<Long> findByNameAndLastName(Date date, String name, String lastName) {
//        return (Root<Long> root,CriteriaQuery<?> query,CriteriaBuilder criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("workingHours"),date,name,lastName);
//    }
}
