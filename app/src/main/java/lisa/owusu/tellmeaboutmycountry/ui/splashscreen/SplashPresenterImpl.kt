package lisa.owusu.tellmeaboutmycountry.ui.splashscreen

class SplashPresenterImpl(var splashView: SplashView) : SplashPresenter {

    override fun onAnimationComplete() {
        splashView.startHomeActivity()
    }

    override fun onUserTouchScreen() {
        splashView.startHomeActivity()
    }

}