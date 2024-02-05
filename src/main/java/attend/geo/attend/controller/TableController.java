package attend.geo.attend.controller;

import attend.geo.attend.service.TableService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/attendance/table")
public class TableController {
    private final TableService tableService;
    @GetMapping(value = "/getUserTable")
    public HttpEntity<?> getUserTable() throws ParseException, IOException {
        return tableService.getUserTable();
    }
}
