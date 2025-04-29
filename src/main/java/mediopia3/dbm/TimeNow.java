package mediopia3.dbm;

/**
 * <p>Title: 학습객체관리시스템</p>
 * <p>Description: SCORM기반의 학습객체관리시스템</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: (주)메디오피아</p>
 * @author 나재열
 * @version 1.0
 */
import java.text.*;
import java.util.*;

public class TimeNow {

  public TimeNow() {
  }

  static public String getDate() {
    String format = "yyyyMMdd-HHmmssSSS";
    String strDate = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    //dateFormat = (SimpleDateFormat)DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.MEDIUM,Locale.KOREAN);
    SimpleTimeZone timeZone = new SimpleTimeZone(9*60*60*1000,"KST");
    dateFormat.setTimeZone(timeZone);
    //        System.out.println("Current Date = "+dateFormat.format(new Date(System.currentTimeMillis())));
    strDate = dateFormat.format(new Date(System.currentTimeMillis()));
  return strDate;
  }
}


