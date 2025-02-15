package com.besome.sketch;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class SketchApplication extends Application {

    private static Context mApplicationContext;
    private Tracker mTracker;

    public static Context getContext() {
        return mApplicationContext;
    }

    public synchronized Tracker a() {
        if (mTracker == null) {
            mTracker = GoogleAnalytics.getInstance(this).newTracker("UA-80718117-1");
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        mApplicationContext = getApplicationContext();
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("error", Log.getStackTraceString(throwable));
            ((AlarmManager) getSystemService(Context.ALARM_SERVICE))
                    .set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            1000,
                            PendingIntent.getActivity(getApplicationContext(), 11111, intent,
                                    PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE));
            Process.killProcess(Process.myPid());
            System.exit(1);
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        });
        super.onCreate();
    }
}
