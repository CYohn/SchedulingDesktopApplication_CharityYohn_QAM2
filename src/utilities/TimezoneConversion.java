package utilities;

import controllers.AddAppointmentController;

import java.sql.Time;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class TimezoneConversion {

    private static LocalDateTime businessStartConverted;
    private static LocalDateTime businessEndConverted;
    LocalDateTime appointmentStartOrigin = AddAppointmentController.getStartDate();
    LocalDateTime appointmentEndOrigin = AddAppointmentController.getEndDate();
    ZonedDateTime originZonedDateTime;
    ZonedDateTime targetZonedDateTimeUTC; //With (ZoneSameInstant())
    LocalDateTime targetDateTimeUTC;
    LocalDateTime targetZonedDateTimeEst;
    LocalDateTime targetDateTimeEst;

    public static LocalDateTime getBusinessStartConverted() {
        return businessStartConverted;
    }

    public static void setBusinessStartConverted(LocalDateTime businessStartConverted) {
        TimezoneConversion.businessStartConverted = businessStartConverted;
    }

    public static LocalDateTime getBusinessEndConverted() {
        return businessEndConverted;
    }

    public static void setBusinessEndConverted(LocalDateTime businessEndConverted) {
        TimezoneConversion.businessEndConverted = businessEndConverted;
    }

    public LocalDateTime convertAppointmentStartToUTC() {
        ZonedDateTime startZonedDateTime = appointmentStartOrigin.atZone(ZoneId.of(TimeZone.getDefault().getID()));
        ZonedDateTime startTimeUTC = startZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime appointmentStartUTC = startTimeUTC.toLocalDateTime();
        return appointmentStartUTC;
    }

    public LocalDateTime convertAppointmentEndToUTC() {
        ZonedDateTime endZonedDateTime = appointmentEndOrigin.atZone(ZoneId.of(TimeZone.getDefault().getID()));
        ZonedDateTime endTimeUTC = endZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime appointmentEndUTC = endTimeUTC.toLocalDateTime();
        return appointmentEndUTC;
    }

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

}
