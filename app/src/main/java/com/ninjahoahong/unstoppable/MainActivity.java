package com.ninjahoahong.unstoppable;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.ninjahoahong.unstoppable.question.views.QuestionViewKey;
import com.ninjahoahong.unstoppable.utils.ForceUpdateChecker;
import com.zhuinden.simplestack.HistoryBuilder;
import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.navigator.DefaultStateChanger;
import com.zhuinden.simplestack.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements ForceUpdateChecker.OnUpdateNeededListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.views_container)
    FrameLayout appContainer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();

        setSupportActionBar(toolbar);
        Navigator.configure().setStateChanger(DefaultStateChanger.create(this, appContainer))
                .install(this, appContainer, HistoryBuilder.single(QuestionViewKey.create()));
    }

    @Override
    public void onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed();
        }
    }

    private void replaceHistory(Object rootKey) {
        Navigator.getBackstack(this)
                .setHistory(HistoryBuilder.single(rootKey),
                        StateChange.REPLACE);
    }

    public void navigateTo(Object key) {
        Navigator.getBackstack(this).goTo(key);
    }

    public static MainActivity get(Context context) {
        // noinspection ResourceType
        return (MainActivity) context.getSystemService(TAG);
    }

    @Override
    public Object getSystemService(@NonNull String name) {
        if (TAG.equals(name)) {
            return this;
        }
        return super.getSystemService(name);
    }

    @Override
    public void onUpdateNeeded(String updateUrl) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue reposting.")
                .setPositiveButton("Update",
                        (dialog1, which) -> redirectStore(updateUrl)).setNegativeButton("No, thanks",
                        (dialog12, which) -> finish()).create();
        dialog.show();
    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
