package nl.tue.besportive.utils;

import androidx.lifecycle.LiveData;

import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class DurationLiveData extends LiveData<String> {
    private final Date startDate;
    private Timer timer;

    public DurationLiveData(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new DurationTimerTask(), 0, 1000);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class DurationTimerTask extends java.util.TimerTask {

        @Override
        public void run() {
            long duration = new Date().getTime() - startDate.getTime();
            long seconds = (duration / 1000) % 60;
            long minutes = (duration / (1000 * 60)) % 60;
            long hours = (duration / (1000 * 60 * 60)) % 24;

            postValue(String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds));
        }
    }
}
