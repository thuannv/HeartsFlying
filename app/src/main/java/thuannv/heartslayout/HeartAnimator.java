package thuannv.heartslayout;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author thuannv
 * @since 13/07/2017
 */

public class HeartAnimator {

    public int mInitX;

    public int mInitY;

    public int mXRand;

    public int mAnimLengthRand;

    public int mBezierFactor;

    public int mXPointFactor;

    public int mAnimLength;

    public int mAnimDuration;

    private final Random mRandom;

    public HeartAnimator(Resources resources, TypedArray array) {
        mRandom = new Random();
        init(resources, array);
    }

    private void init(Resources resources, TypedArray array) {
        mInitX = (int) array.getDimension(R.styleable.HeartLayout_initX, resources.getDimensionPixelOffset(R.dimen.heart_anim_init_x));
        mInitY = (int) array.getDimension(R.styleable.HeartLayout_initY, resources.getDimensionPixelOffset(R.dimen.heart_anim_init_y));
        mXRand = (int) array.getDimension(R.styleable.HeartLayout_xRand, resources.getDimensionPixelOffset(R.dimen.heart_anim_bezier_x_rand));
        mAnimLength = (int) array.getDimension(R.styleable.HeartLayout_animLength, resources.getDimensionPixelOffset(R.dimen.heart_anim_length));
        mAnimLengthRand = (int) array.getDimension(R.styleable.HeartLayout_animLengthRand, resources.getDimensionPixelOffset(R.dimen.heart_anim_length_rand));
        mBezierFactor = array.getInteger(R.styleable.HeartLayout_bezierFactor, resources.getInteger(R.integer.heart_anim_bezier_factor));
        mXPointFactor = (int) array.getDimension(R.styleable.HeartLayout_xPointFactor, resources.getDimensionPixelOffset(R.dimen.heart_anim_x_point_factor));

        mAnimDuration = array.getInteger(R.styleable.HeartLayout_anim_duration, resources.getInteger(R.integer.anim_duration));
    }

    public float randomRotation() {
        return mRandom.nextFloat() * 28.6F - 14.3F;
    }

    public Path createPath(AtomicInteger counter, int viewHeight, int factor) {
        int x1, x2, y1, y2, y3;

        // y1 > y2 > y3
        // x1, x2 random
        x1 = mRandom.nextInt(mXRand * 2);
        x2 = mRandom.nextInt(mXRand);
        y1 = viewHeight - mInitY;
        y2 = counter.intValue() * 15 + mAnimLength * factor + mRandom.nextInt(mAnimLengthRand);
        factor = y2 / mBezierFactor;
        x1 = x1 - (mXRand - 50);
        x2 = mXPointFactor + x2;
        y3 = y1 - y2;
        y2 = y1 - y2 / 2;

        Path p = new Path();
        p.moveTo(mInitX, y1);
        p.cubicTo(mInitX, y1, x1, y1 - 100, x2, y2);
        p.moveTo(x1, y2);
        p.cubicTo(x1, y2 - factor, x2, y3 + factor, x2, y3);

        return p;
    }

    public Animation createAnimation(AtomicInteger counter, HeartsLayout heartLayout) {
        PathAnimation anim = new PathAnimation(createPath(counter, heartLayout.getHeight(), 2), randomRotation());
        anim.setInterpolator(new AccelerateInterpolator());
        return anim;
    }
}
