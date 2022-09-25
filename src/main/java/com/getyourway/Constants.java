package com.getyourway;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {

    public static final String LAT_MIN = "-90.0";
    public static final String LAT_MAX = "90.0";
    public static final String LON_MIN = "-180.0";
    public static final String LON_MAX = "180.0";
    public static final int SECONDS_IN_DAY = 60 * 60 * 24;

    // User Constants
    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final ArrayList<String> ROLES = new ArrayList<>(Arrays.asList(USER, ADMIN));

}
