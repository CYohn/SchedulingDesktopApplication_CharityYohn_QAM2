package utilities;

import java.time.*;
import java.util.TimeZone;


/**
 * This class handles the time conversions for the application.
 */
public class TimezoneConversion {

    /**
     * The converted time for the business start of the day.
     */
    private static LocalDateTime businessStartConverted;
    /**
     * The converted time for the business end of the day.
     */
    private static LocalDateTime businessEndConverted;

    /**
     * Gets the converted business start time and date.
     *
     * @return The converted business start time and date.
     */
    public static LocalDateTime getBusinessStartConverted() {
        return businessStartConverted;
    }

    /**
     * Sets the converted business start date and time.
     *
     * @param businessStartConverted The converted start time
     */
    public static void setBusinessStartConverted(LocalDateTime businessStartConverted) {
        TimezoneConversion.businessStartConverted = businessStartConverted;
    }

    /**
     * Gets the converted business end time.
     *
     * @return The converted business end date and time.
     */
    public static LocalDateTime getBusinessEndConverted() {
        return businessEndConverted;
    }

    /**
     * Sets the converted business end date and time.
     *
     * @param businessEndConverted The converted end time.
     */
    public static void setBusinessEndConverted(LocalDateTime businessEndConverted) {
        TimezoneConversion.businessEndConverted = businessEndConverted;
    }

    /**
     * Converts the business start time from eastern to the user's local time.
     *
     * @return The converted date and time.
     */
    public static LocalDateTime convertBusinessStartFromEstToLocalTime() {
        //create a ZonedDatetime for today at 8:00am EST converted to UTC
        ZoneId estZoneId = ZoneId.of("America/New_York");
        LocalTime estBusinessStart = LocalTime.of(8, 00, 00, 00);
        LocalDate today = LocalDate.now();
        ZonedDateTime estZonesBusinessStart = ZonedDateTime.of(today, estBusinessStart, estZoneId);
        //ZonedDateTime businessStartUTC = estZonesBusinessStart.withZoneSameInstant(ZoneId.of("UTC"));

        //Convert the UTC time to the user's time
        String userTimeZone = TimeZone.getDefault().getID();
        ZonedDateTime businessStartToUser = estZonesBusinessStart.withZoneSameInstant(ZoneId.of(userTimeZone));
        LocalDateTime userBusinessStartLocal = businessStartToUser.toLocalDateTime();
        setBusinessStartConverted(userBusinessStartLocal);
        return userBusinessStartLocal;
    }

    /**
     * Converts the business end date and time from eastern to the user's local time.
     *
     * @return The converted time to user local time and date
     */
    public static LocalDateTime convertBusinessEndFromEstToLocalTime() {
        //create a ZonedDatetime for today at 10:00pm EST converted to UTC
        ZoneId estZoneId = ZoneId.of("America/New_York");
        LocalTime estBusinessEnd = LocalTime.of(22, 00, 00, 00);
        LocalDate today = LocalDate.now();
        ZonedDateTime estZonesBusinessEnd = ZonedDateTime.of(today, estBusinessEnd, estZoneId);
        //ZonedDateTime businessEndUTC = estZonesBusinessEnd.withZoneSameInstant(ZoneId.of("UTC"));

        //Convert the UTC time to the user's time
        String userTimeZone = TimeZone.getDefault().getID();
        ZonedDateTime businessEndToUser = estZonesBusinessEnd.withZoneSameInstant(ZoneId.of(userTimeZone));
        LocalDateTime userBusinessEndLocal = businessEndToUser.toLocalDateTime();
        setBusinessEndConverted(userBusinessEndLocal);
        return userBusinessEndLocal;
    }

    /**
     * Converts the user's selected start date and time to UTC for database storage.
     *
     * @param userStartDateTime The user's selected start time.
     * @return Converted start date and time.
     */
    public static LocalDateTime convertUserStartTimeToUTC(LocalDateTime userStartDateTime) {
        String userTimeZone = TimeZone.getDefault().getID();
        ZoneId userZoneID = ZoneId.of(userTimeZone);
        ZonedDateTime timeInUserTimezone = ZonedDateTime.of(userStartDateTime, userZoneID);
        ZonedDateTime timeInUTC = timeInUserTimezone.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime startTimeConvertedToUTC = timeInUTC.toLocalDateTime();
        return startTimeConvertedToUTC;
    }

    /**
     * Converts the user's selected end time from eastern to UTC.
     *
     * @param userEndDateTime The user's selected end time.
     * @return the end time converted to UTC
     */
    public static LocalDateTime convertUserEndTimeToUTC(LocalDateTime userEndDateTime) {
        String userTimeZone = TimeZone.getDefault().getID();
        ZoneId userZoneID = ZoneId.of(userTimeZone);
        ZonedDateTime timeInUserTimezone = ZonedDateTime.of(userEndDateTime, userZoneID);
        ZonedDateTime timeInUTC = timeInUserTimezone.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime endTimeConvertedToUTC = timeInUTC.toLocalDateTime();
        return endTimeConvertedToUTC;
    }

    /**
     * Converts UTC time from the database to the user's local time.
     *
     * @param UTCTimeFromDB The UTC time from the database.
     * @return The converted time and date to the user's local time.
     */
    public static LocalDateTime convertUTCToUserTime(LocalDateTime UTCTimeFromDB) {
        String userTimeZone = TimeZone.getDefault().getID();
        ZoneId UTC = ZoneId.of("UTC");
        ZonedDateTime timeInUTC = ZonedDateTime.of(UTCTimeFromDB, UTC);
        ZonedDateTime userTimeConverted = timeInUTC.withZoneSameInstant(ZoneId.of(userTimeZone));
        LocalDateTime userLocalDateTime = userTimeConverted.toLocalDateTime();
        return userLocalDateTime;
    }
}