package com.roomkeeper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tools {

    static SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());


    public static String formatTime(long time) {
        return sdf.format(new Date(time));
    }

}
