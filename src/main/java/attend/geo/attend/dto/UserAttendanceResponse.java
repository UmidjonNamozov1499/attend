package attend.geo.attend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAttendanceResponse {
    private Long id;
    private String userName;
    private Date date;
    private Long workingHours;
}
