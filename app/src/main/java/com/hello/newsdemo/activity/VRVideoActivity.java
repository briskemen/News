package com.hello.newsdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;
import com.hello.zhbj52.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ============================================================
 * Copyright：${TODO}有限公司版权所有 (c) 2017
 * Author：   AllenIverson
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * GitBook：  https://www.gitbook.com/@alleniverson
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：News
 * Package_Name：com.hello.newsdemo.activity
 * Version：1.0
 * time：2017/4/11 1:37
 * des ：${TODO}
 * gitVersion：2.12.0.windows.1
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 */
public class VRVideoActivity extends AppCompatActivity {

    private static final String TAG = VRVideoActivity.class.getSimpleName();

    @BindView(R.id.tv_title)
    public TextView tv_title;

    @BindView(R.id.tv_summary)
    public TextView tv_summary;

    @BindView(R.id.tv_time)
    public TextView tv_time;

    @BindView(R.id.tv_username)
    public TextView tv_username;

    @BindView(R.id.tv_toolbartitle)
    public TextView tv_toolbartitle;

    @BindView(R.id.seek_bar)
    public SeekBar seekBar;

    @BindView(R.id.volume_toggle)
    public ImageButton volumeToggle;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.vr_video)
    public VrVideoView mVrVideoView;

    private VideoLoaderTask backgroundVideoLoaderTask;
    private VrVideoView.Options videoOptions = new VrVideoView.Options();


    private String url;
    public static final  int     LOAD_VIDEO_STATUS_UNKNOWN = 0;
    public static final  int     LOAD_VIDEO_STATUS_SUCCESS = 1;
    public static final  int     LOAD_VIDEO_STATUS_ERROR   = 2;
    private              int     loadVideoStatus           = LOAD_VIDEO_STATUS_UNKNOWN;
    private static final String  STATE_VIDEO_DURATION      = "videoDuration";
    private static final String  STATE_IS_PAUSED           = "isPaused";
    private static final String  STATE_PROGRESS_TIME       = "progressTime";
    private              boolean isPaused                  = false;
    private boolean isMuted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        setListener();
        loadVideo();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        loadVideo();
    }

    private void initViews() {
        setContentView(R.layout.activity_vrvideo);
        ButterKnife.bind(this);
        loadVideoStatus = LOAD_VIDEO_STATUS_UNKNOWN;
        mVrVideoView.setInfoButtonEnabled(false);
        setSupportActionBar(mToolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVrVideoView != null) {
            mVrVideoView.pauseRendering();
        }
        isPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVrVideoView != null) {
            mVrVideoView.resumeRendering();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVrVideoView != null) {
            mVrVideoView.shutdown();
        }
    }

    private void setListener() {
        mVrVideoView.setEventListener(new ActivityEventListener());

        seekBar.setOnSeekBarChangeListener(new SeekBarListener());

        volumeToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setIsMuted(!isMuted);
            }
        });
    }

    private void loadVideo() {
        Intent intent = getIntent();
        tv_summary.setText(intent.getStringExtra("summary"));
        tv_time.setText(intent.getStringExtra("time"));
        tv_title.setText(intent.getStringExtra("title"));
        tv_toolbartitle.setText(intent.getStringExtra("title"));
        tv_username.setText(intent.getStringExtra("name"));
        url = intent.getStringExtra("mp4url");

        videoOptions.inputType = VrVideoView.Options.TYPE_MONO;
        videoOptions.inputFormat = VrVideoView.Options.FORMAT_DEFAULT;

        if (backgroundVideoLoaderTask != null) {
            backgroundVideoLoaderTask.cancel(true);
        }
        backgroundVideoLoaderTask = new VideoLoaderTask();
        backgroundVideoLoaderTask.execute(Pair.create(Uri.parse(url), videoOptions));

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(STATE_PROGRESS_TIME, mVrVideoView.getCurrentPosition());
        savedInstanceState.putLong(STATE_VIDEO_DURATION, mVrVideoView.getDuration());
        savedInstanceState.putBoolean(STATE_IS_PAUSED, isPaused);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        long progressTime = savedInstanceState.getLong(STATE_PROGRESS_TIME);
        mVrVideoView.seekTo(progressTime);
        seekBar.setMax((int) savedInstanceState.getLong(STATE_VIDEO_DURATION));
        seekBar.setProgress((int) progressTime);

        isPaused = savedInstanceState.getBoolean(STATE_IS_PAUSED);
        if (isPaused) {
            mVrVideoView.pauseVideo();
        }
    }

    private void setIsMuted(boolean isMuted) {
        this.isMuted = isMuted;
        volumeToggle.setImageResource(isMuted ? R.mipmap.volume_off : R.mipmap.volume_on);
        mVrVideoView.setVolume(isMuted ? 0.0f : 1.0f);
    }

    public boolean isMuted() {
        return isMuted;
    }

    class VideoLoaderTask extends AsyncTask<Pair<Uri, VrVideoView.Options>, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Pair<Uri, VrVideoView.Options>... fileInformation) {
            try {
                if (fileInformation == null || fileInformation.length < 1
                        || fileInformation[0] == null || fileInformation[0].first == null) {
                    // No intent was specified, so we default to playing the local
                    // stereo-over-under video.

                    Toast.makeText(VRVideoActivity.this, "No intent was specified", Toast
                            .LENGTH_LONG).show();

                    /*VrVideoView.Options options = new VrVideoView.Options();
                    options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
                    mVrVideoView.loadVideoFromAsset("congo.mp4", options);*/
                } else {
                    mVrVideoView.loadVideo(fileInformation[0].first, fileInformation[0].second);
                }
            } catch (IOException e) {
                loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
                mVrVideoView.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(VRVideoActivity.this, "Error opening file. ",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return true;
        }
    }

    private class ActivityEventListener extends VrVideoEventListener {

        @Override
        public void onLoadSuccess() {
            loadVideoStatus = LOAD_VIDEO_STATUS_SUCCESS;
            seekBar.setMax((int) mVrVideoView.getDuration());
        }

        @Override
        public void onLoadError(String errorMessage) {
            loadVideoStatus = LOAD_VIDEO_STATUS_ERROR;
            Toast.makeText(VRVideoActivity.this, "Error loading video: " + errorMessage,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onClick() {
            togglePause();
        }


        @Override
        public void onNewFrame() {
            seekBar.setProgress((int) mVrVideoView.getCurrentPosition());
        }

        @Override
        public void onCompletion() {
            mVrVideoView.seekTo(0);
        }
    }

    private void togglePause() {
        if (isPaused) {
            mVrVideoView.playVideo();
        } else {
            mVrVideoView.pauseVideo();
        }
        isPaused = !isPaused;
    }

    private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mVrVideoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
