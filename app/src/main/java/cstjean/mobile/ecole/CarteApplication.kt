package cstjean.mobile.ecole
import android.app.Application

/**
 * Classe de base pour l'application.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
class CarteApplication : Application() {

    /**
     * Méthode appelée lors de la création de l'application.
     */
    override fun onCreate() {
        super.onCreate()
        CarteFideliteRepository.initialize(this)
    }
}