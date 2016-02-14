package com.roomkeeper.splashscreen;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.roomkeeper.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreen extends FrameLayout {

    @Bind(R.id.logo)
    ImageView logo;


    public SplashScreen(Context context) {
        super(context);
    }

    public SplashScreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SplashScreen(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void startAnimation() {
        logo.setTranslationX(1500);
        logo.setAlpha(0f);
        logo.setRotation(5f);
        logo.animate()
                .setInterpolator(new OvershootInterpolator())
                .translationX(0)
                .alpha(1).setDuration(800).rotationX(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                logo.animate().setStartDelay(1000).scaleX(4f).scaleY(4f).setDuration(800).start();
                SplashScreen.this.animate().setStartDelay(1000).setDuration(800).alpha(0).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).setStartDelay(100).start();

    }

}
