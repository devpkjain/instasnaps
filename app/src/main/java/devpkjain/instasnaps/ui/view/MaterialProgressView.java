package devpkjain.instasnaps.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import devpkjain.instasnaps.R;

public class MaterialProgressView extends View {
    private static final int DEFAULT_SIZE_IN_DP = 3;

    private CircularProgressDrawable drawable;

    public MaterialProgressView(Context context) {
        this(context, null);
    }

    public MaterialProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawable = new CircularProgressDrawable(context.getResources().getColor(R.color.primary), dpToPx(DEFAULT_SIZE_IN_DP));
        drawable.setCallback(this);
        drawable.start();
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        drawable.setBounds(0, 0, w, h);
    }

    @Override public void draw(Canvas canvas) {
        super.draw(canvas);
        drawable.draw(canvas);
    }

    public void setColor(int color) {
        drawable.setColor(color);
    }

    @Override protected boolean verifyDrawable(Drawable who) {
        return who == drawable || super.verifyDrawable(who);
    }

    private int dpToPx(float dps) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dps, getResources().getDisplayMetrics());
    }
}