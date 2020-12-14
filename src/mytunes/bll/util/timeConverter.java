package mytunes.bll.util;

public class timeConverter {

    public String convertToString(int timeInMillis)
    {
        int timeInSec = timeInMillis/1000;
        if(timeInSec>=60*60)
        {
            //when downcasting  to int 4.9 we get 4
            //format HH:mm::ss
            int hours = (int) timeInSec/(60*60);
            int minutes = (int) (timeInSec - hours*60*60)/60;
            int seconds = (int) (timeInSec - minutes*60);

            return hours + ":" + minutes + ":" + seconds;
        }
    }
}