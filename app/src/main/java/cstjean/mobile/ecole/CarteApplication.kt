package cstjean.mobile.ecole
import android.app.Application
class CarteApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        CarteFideliteRepository.initialize(this)
    }
}