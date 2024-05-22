package org.example.diploma_jwt.services.model;

import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.repositories.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;

    public Settings saveSettings(Settings settings) {
        return settingsRepository.save(settings);
    }
}
