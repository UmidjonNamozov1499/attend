package attend.geo.attend.controller;

import attend.geo.attend.dto.SettingsDto;
import attend.geo.attend.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@ComponentScan("controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/attendance/settings")
public class SettingsController {

    private final SettingsService settingsService;

    @PostMapping(value = "/addSettings")
    public HttpEntity<?> addSettings(@RequestBody SettingsDto settingsDto) {
        return settingsService.addSettings(settingsDto);
    }
    @GetMapping(value = "/getAllSettings")
    public HttpEntity<?> getAllSettings(){
        return settingsService.getAllSettings();
    }
}
