package com.api.documentmanagementservice.commons;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
    public static LocalDateTime getCurrentTime() {
        return LocalDateTime.now(ZoneId.of("UTC")); // Adjust the time zone as needed
    }
}
