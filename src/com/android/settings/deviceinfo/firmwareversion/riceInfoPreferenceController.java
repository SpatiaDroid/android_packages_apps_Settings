/*
 * Copyright (C) 2020 Wave-OS
 * Copyright (C) 2022 Project Rice
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.deviceinfo.firmwareversion;

import android.content.Context;
import android.os.Build;
import android.os.SystemProperties;
import android.widget.TextView;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;

public class riceInfoPreferenceController extends AbstractPreferenceController {

    private static final String KEY_RICE_INFO = "rice_info";
    private static final String KEY_RICE_MAINTAINER = "rice_maintainer";
    private static final String KEY_RICE_RELEASE = "rice_releasetype";
    private static final String KEY_RICE_DEVICE = "rice_device";
    private static final String KEY_RICE_VERSION = "rice_version";

    private static final String PROP_RICE_VERSION = "ro.rice.version";
    private static final String PROP_RICE_VERSION_CODE = "ro.rice.code";
    private static final String PROP_RICE_RELEASETYPE = "ro.rice.releasetype";
    private static final String PROP_RICE_MAINTAINER = "ro.rice.maintainer";
    private static final String PROP_RICE_DEVICE = "ro.rice.device";
    private static final String PROP_RICE_BUILD_TYPE = "ro.rice.packagetype";

    public riceInfoPreferenceController(Context context) {
        super(context);
    }

    private String getDeviceName() {
        String device = SystemProperties.get(PROP_RICE_DEVICE, "");
        if (device.equals("")) {
            device = Build.MANUFACTURER + " " + Build.MODEL;
        }
        return device;
    }

    private String getriceVersion() {
        final String version = SystemProperties.get(PROP_RICE_VERSION,
                this.mContext.getString(R.string.device_info_default));
        final String versionCode = SystemProperties.get(PROP_RICE_VERSION_CODE,
                this.mContext.getString(R.string.device_info_default));
        final String buildType = SystemProperties.get(PROP_RICE_BUILD_TYPE,
                this.mContext.getString(R.string.device_info_default));

        return version + " | " + versionCode + " | " + buildType;
    }

    private String getriceReleaseType() {
        final String releaseType = SystemProperties.get(PROP_RICE_RELEASETYPE,
                this.mContext.getString(R.string.device_info_default));

        return releaseType.substring(0, 1).toUpperCase() +
                 releaseType.substring(1).toLowerCase();
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final Preference arcVerPref = screen.findPreference(KEY_RICE_VERSION);
        final Preference arcMainPref = screen.findPreference(KEY_RICE_MAINTAINER);
        final Preference arcRelPref = screen.findPreference(KEY_RICE_RELEASE);
        final Preference arcDevPref = screen.findPreference(KEY_RICE_DEVICE);
        final String riceVersion = getriceVersion();
        final String riceDevice = getDeviceName();
        final String riceReleaseType = getriceReleaseType();
        final String riceMaintainer = SystemProperties.get(PROP_RICE_MAINTAINER,
                this.mContext.getString(R.string.device_info_default));
        arcVerPref.setSummary(riceVersion);
        arcDevPref.setSummary(riceDevice);
        arcRelPref.setSummary(riceReleaseType);
        arcMainPref.setSummary(riceMaintainer);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return KEY_RICE_INFO;
    }
}
