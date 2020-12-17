package mytunes.bll.util;

public class TimeConverter {

    public String convertToString(int timeInMillis)
    {
        int timeInSec = timeInMillis/1000;
        if(timeInSec==0)
            return "00:00";

        //if the time is bigger or equal ti an hour
        if(timeInSec>=60*60)
        {
            //when downcasting  to int 4.9 we get 4
            //format HH:mm::ss
            int hours = (int) timeInSec/(60*60);
            int minutes = (int) (timeInSec - hours*60*60)/60;
            int seconds = (int) (timeInSec - minutes*60);

            if(seconds>9)
                return hours + ":" + minutes + ":" + seconds;
            else
                return hours + ":" + minutes + ":0" + seconds;
        }
        //if a time is smaller than an hour
        else
        {
            int minutes = (int) timeInSec/60;
            int seconds = (int) (timeInSec - minutes*60);

            return minutes + ":" + seconds;
        }
    }
}
