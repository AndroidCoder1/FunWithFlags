package lisa.owusu.tellmeaboutmycountry.ui.splashscreen


/**
 * Presenter to acts a middle man for Model and View
 */
interface SplashPresenter {

    /**
     * Called when screen is touched
     */
    fun onUserTouchScreen()

    /**
     * Called when animation is completed
     */
    fun onAnimationComplete()
}