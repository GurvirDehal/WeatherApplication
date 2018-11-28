package Time;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Observable;

public class Clock extends Observable implements ActionListener {

    public static Timer timer;

    public Clock(){
        this.startClock();
    }

    public String getTime(){
        String time = "";

        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(GregorianCalendar.HOUR_OF_DAY);
        int minutes = calendar.get(GregorianCalendar.MINUTE);
        int seconds = calendar.get(GregorianCalendar.SECOND);
        // This block of code is to ensure that the time is formatted correctly.
        time += ((hour < 10) ? "0" : "") + hour + ":";
        time += ((minutes < 10) ? "0" : "") + minutes + ":";
        time += ((seconds < 10) ? "0" : "") + seconds;

        return time;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setChanged();
        notifyObservers(this.getTime());
    }

    public void startClock() {
        if (timer == null) { // if timer is null then, it needs to be started
            timer = new javax.swing.Timer(1000, this);
            timer.setInitialDelay(0);
            timer.start();
        }
    }
}