package ir.erfansn.appcomponentsplayground

import android.app.Application
import android.os.Build
import android.os.StrictMode

class AppComponentsPlayground : Application() {

    override fun onCreate() {
        super.onCreate()
        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .also {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        it.detectUnsafeIntentLaunch()
                    }
                }
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }
}