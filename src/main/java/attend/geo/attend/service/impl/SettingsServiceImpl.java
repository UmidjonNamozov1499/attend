package attend.geo.attend.service.impl;

import attend.geo.attend.dto.SettingsDto;
import attend.geo.attend.entity.Settings;
import attend.geo.attend.repository.SettingsRepository;
import attend.geo.attend.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settingsRepository;

    @Override
    public HttpEntity<?> addSettings(SettingsDto settings) {
        try {
            if (settings != null) {
                Settings settings1 = new Settings();
                settings1.setName(settings.getName());
                settings1.setIP(settings.getIP());
                settings1.setIsActive(settings.getIsActive());
                Settings save = settingsRepository.save(settings1);
                return ResponseEntity.ok().body("Success added");
            }else {
                return ResponseEntity.ok().body("Request error");
            }
        } catch (Exception e) {
            return ResponseEntity.ok().body(e.getMessage());
        }
    }

    @Override
    public HttpEntity<?> getAllSettings() {
        try {
            List<Settings> all = settingsRepository.findAll();
            if (!all.isEmpty()){
                return ResponseEntity.ok(all);
            }else {
                return ResponseEntity.ok().body("Settings not found");
            }
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
