package com.stkpush.ncba.services;

import com.stkpush.ncba.models.ApplicationSettings;
import com.stkpush.ncba.models.ApplicationSettingsModel;
import com.stkpush.ncba.repositories.ApplicationSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationSettingsService implements ApplicationSettingsImpl {
    @Autowired
    ApplicationSettingsRepository applicationSettingsRepository;

    @Override
    public ApplicationSettings getValue(String name) {
        try {
            ApplicationSettings _ApplicationSettings = new ApplicationSettings();
            ApplicationSettingsModel settings = applicationSettingsRepository.getValue(name);
            if (settings != null) {
                String en = settings.getValue();
                Cipher _cipher=new Cipher();
                _ApplicationSettings.setValue(_cipher.EncryptDecrypt("decrypt", en));
                _ApplicationSettings.setId(settings.getId());
                _ApplicationSettings.setName(settings.getName());
                _ApplicationSettings.setHashed(settings.getHashed());
                _ApplicationSettings.setCreated_at(settings.getCreated_at());
                _ApplicationSettings.setStatus(settings.getStatus());
            }
            return _ApplicationSettings;
        } catch (Exception e) {
            return null;
        }

    }
}
