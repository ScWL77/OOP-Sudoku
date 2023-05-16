package sudoku;

import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private static String time;
    public Clock(){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;
            @Override
            public void run() {
                int hours = secondsPassed / 3600;
                int minutes = secondsPassed / 60 - hours*60;
                int seconds = secondsPassed - hours*3600 - minutes * 60;

                String timeElapsed = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                time = timeElapsed;
                secondsPassed++;
            }
        };
        timer.schedule(task, 1000, 1000); // starts the task after 1 second and repeats every 1 second
    }

    public String getTime(){
        return this.time;
    }
}