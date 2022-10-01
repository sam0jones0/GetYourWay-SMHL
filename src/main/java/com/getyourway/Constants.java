package com.getyourway;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    // Weather API
    public static final String BASE_HOST = "api.openweathermap.org";
    public static final String BASE_PATH = "data/3.0/onecall";

    public static final float LAT_MIN = -90.0f;
    public static final float LAT_MAX = 90.0f;
    public static final float LON_MIN = -180.0f;
    public static final float LON_MAX = 180.0f;
    public static final String ERR_MSG_LAT = "Latitude must be between -90 and 90";
    public static final String ERR_MSG_LON = "Latitude must be between -180 and 180";
    public static final int MIN_FORECAST_DAYS = 1;
    public static final int MAX_FORECAST_DAYS = 14;
    public static final String ERR_MSG_TIMESPAN = "Historical forecast timespan must be between 1 and 14 days";

    // User Constants
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final ArrayList<String> ROLES = new ArrayList<>(Arrays.asList(USER, ADMIN));

    // Misc
    public static final int DAYS_IN_WEEK = 7;
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;

}
