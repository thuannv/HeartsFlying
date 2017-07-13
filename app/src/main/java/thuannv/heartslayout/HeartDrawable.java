package thuannv.heartslayout;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

/**
 * @author thuannv
 * @since 13/07/2017
 */

public class HeartDrawable {

    private Drawable mDrawable;

    private Transformation mTransformation;

    private Animation mAnimation;

    private boolean mVisible;

    public HeartDrawable(Drawable drawable) {
        mDrawable = drawable;
        mTransformation = new Transformation();
        mVisible = true;
    }

    public void setAnimation(Animation anim) {
        mAnimation = anim;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        if (mDrawable != null) {
            mDrawable.setBounds(left, top, right, bottom);
        }
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    public void draw(Canvas canvas) {
        if (isVisible()) {
            Drawable d = getDrawable();
            if (d != null) {
                int savedCount = canvas.save();
                if (mAnimation != null) {
                    mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), mTransformation);
                    canvas.concat(mTransformation.getMatrix());
                }
                d.draw(canvas);
                canvas.restoreToCount(savedCount);
            }
        }
    }
}
