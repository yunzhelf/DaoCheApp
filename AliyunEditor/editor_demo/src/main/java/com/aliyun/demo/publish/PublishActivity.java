package com.aliyun.demo.publish;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aliyun.common.global.AliyunTag;
import com.aliyun.demo.editor.R;
import com.aliyun.qupai.editor.AliyunICompose;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.lang.ref.WeakReference;

/**
 * Created by macpro on 2017/11/6.
 */

public class PublishActivity extends Activity implements View.OnClickListener {

    public static final String KEY_PARAM_CONFIG = "project_json_path";
    public static final String KEY_PARAM_THUMBNAIL = "svideo_thumbnail";

    private View mActionBar;
    private ImageView mIvLeft;
    private ProgressBar mProgress;
    private ImageView mCoverImage, mCoverBlur;
    private EditText mVideoDesc;
//    private View mCoverSelect;
    private View mComposeProgressView;
    private TextView mComposeProgress;
    private View mComposeIndiate;
    private TextView mComposeStatusText, mComposeStatusTip;
    //    private TextView mDescCount;
    private TextView mPublish;

    private String mConfig;
    private String mOutputPath = Environment.getExternalStorageDirectory() + File.separator + "output_compose_video.mp4";
    private String mThumbnailPath;
    private long mDuration; //视频时长
    private AliyunICompose mCompose;
    private boolean mComposeCompleted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aliyun_svideo_activity_publish);
        initView();
        mConfig = getIntent().getStringExtra(KEY_PARAM_CONFIG);
        mThumbnailPath = getIntent().getStringExtra(KEY_PARAM_THUMBNAIL);
        mDuration = getIntent().getLongExtra("videoDuration",0);
        mCompose = ComposeFactory.INSTANCE.getInstance();
        mCompose.init(this);
        mCompose.compose(mConfig, mOutputPath, mCallback);
        View root = (View) mActionBar.getParent();
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) getApplication()
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager.isActive()) {
                    inputManager.hideSoftInputFromWindow(mVideoDesc.getWindowToken(), 0);
                }
            }
        });
        new MyAsyncTask(this).execute(mThumbnailPath);
    }

    private void initThumbnail(Bitmap thumbnail) {
        mCoverBlur.setImageBitmap(thumbnail);
        mCoverImage.setImageBitmap(thumbnail);
    }

    static class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private WeakReference<PublishActivity> ref;
        private float maxWidth;

        MyAsyncTask(PublishActivity activity) {
            ref = new WeakReference<>(activity);
//            int screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
//            maxWidth = screenWidth * 0.75f;
            maxWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    240, activity.getResources().getDisplayMetrics());
        }

        @Override
        protected Bitmap doInBackground(String... thumbnailPaths) {
            String path = thumbnailPaths[0];
            if (TextUtils.isEmpty(path)) {
                return null;
            }
            File thumbnail = new File(path);
            if (!thumbnail.exists()) {
                return null;
            }
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opt);
            float bw = opt.outWidth;
            float bh = opt.outHeight;
            float scale;
            if (bw > bh) {
                scale = bw / maxWidth;
            } else {
                scale = bh / maxWidth;
            }
            boolean needScaleAfterDecode = scale != 1;
            opt.inJustDecodeBounds = false;
            Bitmap bmp = BitmapFactory.decodeFile(path, opt);
            if (needScaleAfterDecode) {
                bmp = scaleBitmap(bmp, scale);
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null && ref != null && ref.get() != null) {
                ref.get().initThumbnail(bitmap);
            }
        }
    }

    private static Bitmap scaleBitmap(Bitmap bmp, float scale) {
        Matrix mi = new Matrix();
        mi.setScale(1 / scale, 1 / scale);
        Bitmap temp = bmp;
        bmp = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), mi, false);
        temp.recycle();
        return bmp;
    }

    private void initView() {
        mActionBar = findViewById(R.id.action_bar);
        mActionBar.setBackgroundColor(
                getResources().getColor(R.color.action_bar_bg_50pct));
        mPublish = (TextView) findViewById(R.id.tv_right);
        mIvLeft = (ImageView) findViewById(R.id.iv_left);
        mIvLeft.setOnClickListener(this);
        mIvLeft.setImageResource(R.mipmap.aliyun_svideo_icon_back);
        mPublish.setText(R.string.publish);
        mIvLeft.setVisibility(View.VISIBLE);
        mPublish.setVisibility(View.VISIBLE);
        mProgress = (ProgressBar) findViewById(R.id.publish_progress);
        mComposeProgressView = findViewById(R.id.compose_progress_view);
        mCoverBlur = (ImageView) findViewById(R.id.publish_cover_blur);
        mCoverImage = (ImageView) findViewById(R.id.publish_cover_image);
        mVideoDesc = (EditText) findViewById(R.id.publish_desc);
        mComposeIndiate = findViewById(R.id.image_compose_indicator);
//        mDescCount = (TextView) findViewById(R.id.publish_desc_count);
        mPublish.setEnabled(mComposeCompleted);
        mPublish.setOnClickListener(this);
//        mCoverSelect = findViewById(R.id.publish_cover_select);
//        mCoverSelect.setEnabled(mComposeCompleted);
//        mCoverSelect.setOnClickListener(this);
        mComposeProgress = (TextView) findViewById(R.id.compose_progress_text);
        mComposeStatusText = (TextView) findViewById(R.id.compose_status_text);
        mComposeStatusTip = (TextView) findViewById(R.id.compose_status_tip);
        mVideoDesc.addTextChangedListener(new TextWatcher() {

            private int start;
            private int end;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                start = mVideoDesc.getSelectionStart();
                end = mVideoDesc.getSelectionEnd();

                int count = count(s.toString());
                // 限定EditText只能输入10个数字
                if (count > 20 && start > 0) {
                    Log.d(AliyunTag.TAG, "超过10个以后的数字");

                    s.delete(start - 1, end);
                    mVideoDesc.setText(s);
                    mVideoDesc.setSelection(s.length());
                }
            }
        });
    }

    private int count(String text) {
        int len = text.length();
        int skip;
        int letter = 0;
        int chinese = 0;
//		int count = 0;
//		int sub = 0;
        for (int i = 0; i < len; i += skip) {
            int code = text.codePointAt(i);
            skip = Character.charCount(code);
            if (code == 10) {
                continue;
            }
            String s = text.substring(i, i + skip);
            if (isChinese(s)) {
                chinese++;
            } else {
                letter++;
            }

        }
        letter = letter % 2 == 0 ? letter / 2 : (letter / 2 + 1);
        int result = chinese + letter;
        return result;
    }

    // 完整的判断中文汉字和符号
    private boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    private boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == mPublish) {
            mPublish.setEnabled(false);
            Class act = null;
            try {
                act = Class.forName("com.yifactory.daocheapp.biz.my_function.activity.MyLecturerUploadVideoActivity");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this,act);
            intent.putExtra("vpath", mOutputPath);
            intent.putExtra("vthum", mThumbnailPath);
            intent.putExtra("videoDuration",mDuration);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            onBackPressed();
//        } else if (v == mCoverSelect) {
//            Intent intent = new Intent(this, CoverEditActivity.class);
//            intent.putExtra(CoverEditActivity.KEY_PARAM_VIDEO, mOutputPath);
//            startActivityForResult(intent, 0);
        } else if (v == mIvLeft) {
            onBackPressed();
        }
    }

    private String convertDuration2Text(long duration) {
        int sec = Math.round(((float) duration) / 1000);
        int hour = sec / 3600;
        int min = (sec % 3600) / 60;
        sec = (sec % 60);
        return String.format("%1$02d:%2$02d:%3$02d",
                hour,
                min,
                sec);
    }

    @Override
    public void onBackPressed() {
        if (mComposeCompleted) {
            super.onBackPressed();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final AlertDialog dialog = builder.setTitle(R.string.video_composeing_cancel_or_go)
                    .setNegativeButton(R.string.goback_to_editor, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mComposeCompleted) {
                                finish();
                            } else {
                                mCompose.cancelCompose();
                                finish();
                            }
                        }
                    })
                    .setPositiveButton(R.string.go_ahead_compose, null).create();
            dialog.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            mThumbnailPath = data.getStringExtra(CoverEditActivity.KEY_PARAM_RESULT);
            new MyAsyncTask(this).execute(mThumbnailPath);
        }
    }

    private final AliyunICompose.AliyunIComposeCallBack mCallback = new AliyunICompose.AliyunIComposeCallBack() {
        @Override

        public void onComposeError(int errorCode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mComposeProgress.setVisibility(View.GONE);

                    mComposeIndiate.setVisibility(View.VISIBLE);
                    mComposeIndiate.setActivated(false);
                    mComposeStatusTip.setText(R.string.backtoeditorandtryagain);
                    mComposeStatusText.setText(R.string.compose_failed);
                }
            });
        }

        @Override
        public void onComposeProgress(final int progress) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mComposeProgress.setText(progress + "%");
                    mProgress.setProgress(progress);
                }
            });
        }

        @Override
        public void onComposeCompleted() {
            mComposeCompleted = true;
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(mOutputPath);
            Bitmap bmp = mmr.getFrameAtTime(0);
            float w = bmp.getWidth();
            float h = bmp.getHeight();
            int maxWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    240, getResources().getDisplayMetrics());
            float scale;
            if (w > h) {
                scale = w / maxWidth;
            } else {
                scale = h / maxWidth;
            }
            final Bitmap thumbnail = scaleBitmap(bmp, scale);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mCoverImage.setVisibility(View.VISIBLE);
                    initThumbnail(thumbnail);
                    mPublish.setEnabled(mComposeCompleted);
                    mProgress.setVisibility(View.GONE);
                    mComposeProgress.setVisibility(View.GONE);

                    mComposeIndiate.setVisibility(View.VISIBLE);
                    mComposeIndiate.setActivated(true);
                    mComposeStatusTip.setVisibility(View.GONE);
                    mComposeStatusText.setText(R.string.compose_success);
                    mComposeProgressView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mComposeProgressView.setVisibility(View.GONE);
//                            mCoverSelect.setVisibility(View.VISIBLE);
//                            mCoverSelect.setEnabled(mComposeCompleted);
                        }
                    }, 2000);
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCompose.release();
    }
}
