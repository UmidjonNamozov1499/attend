package attend.geo.attend.controller;

import attend.geo.attend.dto.AllUserDavomatDto;
import attend.geo.attend.dto.GetAllUserRequest;
import attend.geo.attend.service.DavomatService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/attendance/user/davomat")
public class DavomatController {
    private final DavomatService davomatService;

    @GetMapping(value = "/getAllUserDavomat")
    public ResponseEntity<?> getAllUsersDavomat(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date end,
            @RequestBody GetAllUserRequest request){

        return davomatService.getAllUsersDavomat(start,end,request);
    }
}
