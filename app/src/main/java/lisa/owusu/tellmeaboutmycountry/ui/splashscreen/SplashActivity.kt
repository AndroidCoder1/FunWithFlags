package lisa.owusu.tellmeaboutmycountry.ui.splashscreen

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import lisa.owusu.tellmeaboutmycountry.R
import lisa.owusu.tellmeaboutmycountry.ui.homescreen.HomeActivity


class SplashActivity : AppCompatActivity(), SplashView, Animator.AnimatorListener {

    var lottieAvImage: LottieAnimationView? = null
    var presenter: SplashPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenterImpl(this)

        lottieAvImage = findViewById(R.id.lottie_av_image)
        lottieAvImage?.addAnimatorListener(this)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)

        /**
         * End Animation and start next activity if screen is touched
         */
        presenter?.onUserTouchScreen()
        return true
    }


    override fun onAnimationRepeat(animation: Animator?) {

    }

    override fun onAnimationEnd(animation: Animator?) {
        presenter?.onAnimationComplete()
    }

    override fun onAnimationCancel(animation: Animator?) {
        presenter?.onAnimationComplete()
    }

    override fun onAnimationStart(animation: Animator?) {

    }

    override fun startHomeActivity() {
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }
}