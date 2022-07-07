/*
 * Copyright (C) 2022 Project Kaleidoscope
 * Copyright (C) 2022 riceDroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.android.settings.laboratory;

import android.content.Context;
import android.os.SystemProperties;

import com.android.settings.core.TogglePreferenceController;
import com.android.settings.R;
import com.android.settingslib.development.SystemPropPoker;

public class SkiaglPreferenceController extends TogglePreferenceController {

    private static final String PROPERTY_RENDERER = "persist.sys.hwui.renderer";
    private static final String PROPERTY_RENDERER_ENGINE = "persist.sys.renderengine.backend";
    private static final String PROPERTY_SKIA_OPS = "persist.sys.renderthread.skia.reduceopstasksplitting";
    private static final String OPENGL_THREADED = "threaded";
    private static final String SKIA_THREADED = "skiaglthreaded";
    private static final String OPENGL = "opengl";
    private static final String SKIAGL = "skiagl";

    public SkiaglPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isChecked() {
        String renderer = SystemProperties.get(PROPERTY_RENDERER);
        if ("skiagl".equals(renderer))
            return true;
        else
            return SKIAGL.equals(renderer);
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        SystemProperties.set(PROPERTY_RENDERER, isChecked ? SKIAGL : OPENGL);
        SystemProperties.set(PROPERTY_RENDERER_ENGINE, isChecked ? SKIA_THREADED : OPENGL_THREADED);
        SystemProperties.set(PROPERTY_SKIA_OPS, isChecked ? "true" : "false");
        SystemPropPoker.getInstance().poke();
        return true;
    }

    @Override
    public int getSliceHighlightMenuRes() {
        return NO_RES;
    }
}
