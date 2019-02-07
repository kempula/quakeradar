package fi.kempula.quakeradar.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseRxActivity extends AppCompatActivity {

    private CompositeDisposable disposables;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disposables = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    protected void addSubscription(Disposable disposable) {
        disposables.add(disposable);
    }

}
