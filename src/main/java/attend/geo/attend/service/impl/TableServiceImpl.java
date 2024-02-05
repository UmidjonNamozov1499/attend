package attend.geo.attend.service.impl;

import attend.geo.attend.entity.UserAttendance;
import attend.geo.attend.repository.UserAttendanceRepository;
import attend.geo.attend.service.TableService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Component
public class TableServiceImpl implements TableService {
    private final UserAttendanceRepository userAttendanceRepository;
    Calendar calendar = Calendar.getInstance();
    public HttpEntity<?> getUserTable() throws IOException {
        String uploadExcel29 = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadExcel\\employee29.xlsx";
        String uploadExcel30 = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadExcel\\employee30.xlsx";
        String uploadExcel31 = "D:\\UzLITINEFTGAZ\\SpringBootDownloadGetAPIDtaInExcelFile-master\\attend\\uploadExcel\\employee31.xlsx";

        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 29) {
            updateFile(uploadExcel29);
            return ResponseEntity.ok(uploadExcel29);
        }
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 30){
            updateFile(uploadExcel30);
            return ResponseEntity.ok(uploadExcel30);
        }
        if (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) == 31){
            updateFile(uploadExcel31);
            return ResponseEntity.ok(uploadExcel31);
        }else {
            return ResponseEntity.ok().body("Date error");
        }

    }
    public HttpEntity<?> updateFile(String uploadFile) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(uploadFile)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheet("Users");

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 10; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    sheet.removeRow(row);
                }
            }

            try {
                List<UserAttendance> allUsers = userAttendanceRepository.findAll();
                Set<String> userTable = new TreeSet<>();
                List<UserAttendance> tableUsers = new ArrayList<>();

                for (UserAttendance allUser : allUsers) {
                    String uniqueUser = allUser.getUserName();
                    if (!userTable.contains(uniqueUser)) {
                        userTable.add(uniqueUser);
                    }
                }
                int rowNum = 9;
                int count = 1;
                for (String users : userTable) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(count++);
                    int cellNumber = 1;
                    if (users != null) {
                        row.createCell(cellNumber++).setCellValue(users);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                        Date start = sdf.parse(startDate);
                        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
                        Date end = sdf.parse(endDate);
                        List<UserAttendance> byUserNameOrderByDateDesc = userAttendanceRepository.findByUserNameAndDateBetweenOrderByDateAsc(users, start, end);
                        tableUsers.add((UserAttendance) byUserNameOrderByDateDesc);
                        for (UserAttendance userAttendance : byUserNameOrderByDateDesc) {
                            if (userAttendance.getWorkingHours() != null) {
                                row.createCell(cellNumber++).setCellValue(userAttendance.getWorkingHours());
                            } else {
                                row.createCell(cellNumber++).setCellValue(0);
                            }
                        }
                        return ResponseEntity.ok().body("Cell update");
                    } else {
                        row.createCell(cellNumber).setCellValue(0);
                        return ResponseEntity.ok().body("Cell update");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.ok().body(e.getMessage());
            }
            try (FileOutputStream outputStream = new FileOutputStream(uploadFile)) {
                workbook.write(outputStream);
                return ResponseEntity.ok().body("Success file save");
            } catch (IOException e) {
                return ResponseEntity.ok().body(e.getMessage());
            }
        }
    }
}

