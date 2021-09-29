package com.rewards.program.util;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public final class RewardsUtil {

    private static Logger log = LogManager.getLogger(RewardsUtil.class);

    public static Long calculatePoints(Long amount){
        log.info("[calculatePoints] start - amount: {}", amount);
        Long points = 0L;
        Long over100 = amount - 100;

        if (over100 > 0) {
            points += (over100 * 2);
          }    
          if (amount > 50) {
            points += 50;      
          }
        log.info("[calculatePoints] end - points: {}", points);
        return points;
    }

    public static LocalDate stringToLocalDate (String date){
        log.info("[stringToLocalDate] start");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy", Locale.US);
        LocalDate localDate = LocalDate.parse(date, formatter);
        log.info("[stringToLocalDate] end");
       return localDate;
       
   }

    public static boolean isWithinLastThreeMonths(String dateString){
        log.info("[isWithinLastThreeMonths] dateString: {}", dateString);
        LocalDate date = stringToLocalDate(dateString);
        YearMonth monthDate = YearMonth.from(date);
        YearMonth thisMonth = YearMonth.from(LocalDate.now());

        if(monthDate.compareTo(thisMonth) == 0
            || monthDate.compareTo(thisMonth.minusMonths(1L)) == 0
                || monthDate.compareTo(thisMonth.minusMonths(2L)) == 0){
            return true;
        } else{
            return false;
        }
    }

    public static YearMonth stringToYearMonth (String date){
        log.info("[stringToYearMonth]");
        return YearMonth.from(stringToLocalDate(date));
    }


    public static boolean isDateValid(final String date) {
        log.info("[isDateValid] date: {}", date);
        boolean valid = false;
        try {
            stringToLocalDate(date);
            valid = true;
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            valid=false;
        }

        log.info("[isDateValid] date: {}, valid: {}", date, valid);
        return valid;
    }

}