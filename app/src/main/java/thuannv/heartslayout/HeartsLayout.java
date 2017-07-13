package thuannv.heartslayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author thuannv
 * @since 13/07/2017
 */

public class HeartsLayout extends View {

    private HeartAnimator mAnimator;

    private final int FPS = 30;

    private final AtomicInteger mCounter = new AtomicInteger(0);

    private List<HeartDrawable> mHearts;

    private List<HeartDrawable> mDrawingHearts;

    public int mHeartWidth = -1;

    public int mHeartHeight = -1;

    public long mAnimDuration = -1L;

    private final Object mSync = new Object();

    public HeartsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHearts = new LinkedList<>();
        mDrawingHearts = new LinkedList<>();

        Resources res = context.getResources();
        final int defaultWidth = res.getDimensionPixelOffset(R.dimen.heart_size_width);
        final int defaultHeight = res.getDimensionPixelOffset(R.dimen.heart_size_height);
        final int defaultDuration = res.getInteger(R.integer.anim_duration);

        mHeartWidth = defaultWidth;
        mHeartHeight = defaultHeight;
        mAnimDuration = defaultDuration;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HeartLayout);
        try {
            mAnimator = new HeartAnimator(res, a);
            mHeartWidth = (int) a.getDimension(R.styleable.HeartLayout_heart_width, defaultWidth);
            mHeartHeight = (int) a.getDimension(R.styleable.HeartLayout_heart_height, defaultHeight);
            mAnimDuration = a.getInteger(R.styleable.HeartLayout_anim_duration, defaultDuration);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10000; i++) {
//                    mUIHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            add(new HeartDrawable(mDrawable));
//                        }
//                    });
//
//                    try {
//                        Thread.sleep(200);
//                    } catch (InterruptedException e) {
//                    }
//                }
//            }
//        }).start();
    }

    public void add(final HeartDrawable heart) {
        if (heart != null) {
            heart.setBounds(0, 0, mHeartWidth, mHeartHeight);
            Animation animation = mAnimator.createAnimation(mCounter, HeartsLayout.this);
            animation.setDuration(mAnimDuration);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mCounter.incrementAndGet();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCounter.decrementAndGet();
                    synchronized (mSync) {
                        mHearts.remove(heart);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });


            heart.setAnimation(animation);

            animation.startNow();

            synchronized (mSync) {
                mHearts.add(heart);
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int savedCount = canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        synchronized (mSync) {
            if (mHearts != null) {
                mDrawingHearts.addAll(mHearts);
            }
        }

        if (mDrawingHearts != null) {
            for (HeartDrawable hd : mDrawingHearts) {
                hd.draw(canvas);
            }
            mDrawingHearts.clear();
        }

        canvas.restoreToCount(savedCount);
        postInvalidateDelayed(1000L / FPS);
    }
}
