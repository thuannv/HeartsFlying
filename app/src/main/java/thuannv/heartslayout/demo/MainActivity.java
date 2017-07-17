package thuannv.heartslayout.demo;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import thuannv.heartslayout.HeartDrawable;
import thuannv.heartslayout.HeartsLayout;
import thuannv.heartslayout.demo.R;

public class MainActivity extends Activity {

    private Random mRandom = new Random();

    private List<Drawable> mDrawables = new ArrayList<>();

    private ImageView mImageView;

    private HeartsLayout mHeartsLayout;

    private volatile boolean mStop = false;

    private Handler mUIHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mHeartsLayout = (HeartsLayout) findViewById(R.id.heartsLayout);

        mUIHandler = new Handler();

        initDrawables();

        setImage();
    }

    private void setImage() {
        Point outSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(outSize);
        Picasso.with(this).load(R.drawable.self).fit().into(mImageView);
    }

    private void initDrawables() {
        int[] resIds = {
                R.drawable.hand_blue,
                R.drawable.hand_red,
                R.drawable.hand_yellow,
                R.drawable.heart_blue,
                R.drawable.heart_red,
                R.drawable.heart_yellow
        };

        Bitmap bitmap;
        Drawable drawable;
        Resources res = getResources();
        for (int id : resIds) {
            bitmap = BitmapFactory.decodeResource(res, id);
            drawable = new BitmapDrawable(res, bitmap);
            mDrawables.add(drawable);
        }

        startReceivingHearts();
    }

    private void startReceivingHearts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mStop) {
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int idx = mRandom.nextInt(mDrawables.size());
                            Drawable drawable = mDrawables.get(idx);
                            mHeartsLayout.add(new HeartDrawable(drawable));
                        }
                    });

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // ignored
                    }
                }
            }
        }).start();
    }

    private void requestStop() {
        mStop  = true;
    }

    @Override
    protected void onStop() {
        requestStop();
        super.onStop();
    }
}
