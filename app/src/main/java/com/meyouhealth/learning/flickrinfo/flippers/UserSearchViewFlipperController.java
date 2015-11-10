package com.meyouhealth.learning.flickrinfo.flippers;

import android.app.Activity;
import android.os.Looper;
import android.widget.ViewFlipper;

public class UserSearchViewFlipperController {

    public static final int POSITION_INITIAL = 0;
    public static final int POSITION_WAITING = 1;
    public static final int POSITION_SHOW_USER = 2;
    public static final int POSITION_NO_USERS_FOUND = 3;

    private ViewFlipper mFlipper;
    private Activity mActivity;

    public UserSearchViewFlipperController(ViewFlipper flipper, Activity activity) {
        mFlipper = flipper;
        mActivity = activity;
    }

    public void show(final int position) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            // On UI thread.
            while (mFlipper.getDisplayedChild() != position) {
            mFlipper.showNext();
        }
        } else {
            // Not on UI thread.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    show(position);
                }
            }).start();
        }

    }
}
