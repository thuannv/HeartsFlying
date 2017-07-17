package thuannv.heartslayout;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * This source code file reference from class <a href="https://github.com/tyrantgit/HeartLayout/blob/master/heartlayout/src/main/java/tyrantgit/widget/PathAnimator.java">PathAnimator</a> in HeartLayout project of tyrantgit on GitHub
 */
public class PathAnimation extends Animation {

    private float mLength;

    private float mRotation;

    private PathMeasure mPathMeasure;

    public PathAnimation(Path path, float rotation) {
        mPathMeasure = new PathMeasure(path, false);
        mLength = mPathMeasure.getLength();
        mRotation = rotation;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();
        float scale = 1F;
        if (3000.0F * interpolatedTime < 200.0F) {
            scale = scale(interpolatedTime, 0.0D, 0.06666667014360428D, 0.20000000298023224D, 1.100000023841858D);
        } else if (3000.0F * interpolatedTime < 300.0F) {
            scale = scale(interpolatedTime, 0.06666667014360428D, 0.10000000149011612D, 1.100000023841858D, 1.0D);
        }

        mPathMeasure.getMatrix(mLength * interpolatedTime, matrix, PathMeasure.POSITION_MATRIX_FLAG);
        matrix.preScale(scale, scale);
        matrix.postRotate(mRotation * interpolatedTime);
        t.setAlpha(1.0F - interpolatedTime);
    }

    private float scale(double a, double b, double c, double d, double e) {
        return (float) ((a - b) / (c - b) * (e - d) + d);
    }
}
