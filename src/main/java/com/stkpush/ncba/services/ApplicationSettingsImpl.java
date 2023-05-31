package com.stkpush.ncba.services;

import com.stkpush.ncba.models.ApplicationSettings;

public interface ApplicationSettingsImpl {
    ApplicationSettings getValue(String name);
}
