package com.wms.utils;

import com.wms.models.UserData;

public class SessionManager {
    private static UserData userData;

    // Private constructor to prevent instantiation
    private SessionManager() {}

    // Static method to set the userData
    public static void setUserData(UserData data) {
        userData = data;
    }

    // Static method to get the userData
    public static UserData getUserData() {
        return userData;
    }
}
