package attend.geo.attend.service;

import attend.geo.attend.dto.SettingsDto;
import attend.geo.attend.entity.Settings;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface SettingsService {
    HttpEntity<?> addSettings(SettingsDto settings);
    HttpEntity<?> getAllSettings();
}
