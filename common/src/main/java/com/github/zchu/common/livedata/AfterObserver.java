package com.github.zchu.common.livedata;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

public abstract class AfterObserver<T> implements Observer<T> {

    private boolean isFirstChange = true;

    @Override
    public final void onChanged(@Nullable T t) {
        if (isFirstChange) {
            isFirstChange = false;
            return;
        }
        onAfterChanged(t);
    }

    public abstract void onAfterChanged(@Nullable T t);

}
