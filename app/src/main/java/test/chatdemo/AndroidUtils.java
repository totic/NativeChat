package test.chatdemo;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by federico on 3/16/17.
 */

public class AndroidUtils {

    private static final SimpleDateFormat allDay = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat fullDate = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    private static final SimpleDateFormat dayDate  = new SimpleDateFormat("MM/dd hh:mm a");
    private static final SimpleDateFormat time     = new SimpleDateFormat("hh:mm a");

    public static String getDate(Date date){
        Date today = new Date();
        if(sameDay(today, date)){
            return time.format(date);
        }else if(sameYear(today, date)){
            return dayDate.format(date);
        }else{
            return fullDate.format(date);
        }
    }

    public static String getDayFormat(Date date){
        return allDay.format(date);
    }


    public static boolean sameDay(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

    }

    public static boolean sameYear(Date date1, Date date2){
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

    }
}
