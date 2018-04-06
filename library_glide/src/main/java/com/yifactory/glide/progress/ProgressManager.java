package com.yifactory.glide.progress;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressManager {

    private static List<WeakReference<OnProgressListener>> listeners = Collections.synchronizedList(new ArrayList<WeakReference<OnProgressListener>>());

    private ProgressManager() {
    }

    public static void addProgressListener(OnProgressListener progressListener) {
        if (progressListener == null) return;

        if (findProgressListener(progressListener) == null) {
            listeners.add(new WeakReference<>(progressListener));
        }
    }

    public static void removeProgressListener(OnProgressListener progressListener) {
        if (progressListener == null) return;

        WeakReference<OnProgressListener> listener = findProgressListener(progressListener);
        if (listener != null) {
            listeners.remove(listener);
        }
    }

    private static WeakReference<OnProgressListener> findProgressListener(OnProgressListener listener) {
        if (listener == null) return null;
        if (listeners == null || listeners.size() == 0) return null;

        for (int i = 0; i < listeners.size(); i++) {
            WeakReference<OnProgressListener> progressListener = listeners.get(i);
            if (progressListener.get() == listener) {
                return progressListener;
            }
        }
        return null;
    }
}
