package com.zinno.mceconf.samples;

public enum Samples {
    BUTTON_GAME(R.string.button_game_sample_name, R.string.button_game_sample_icon),
    ACCELEROMETER(R.string.accelerometer_sample_name, R.string.accelerometer_sample_icon);

    int nameId, iconId;

    Samples(int nameId, int iconId) {
        this.nameId = nameId;
        this.iconId = iconId;
    }
}
