package max.keils.habitmate

import android.app.Application
import max.keils.habitmate.di.DaggerApplicationComponent

class HabitMateApp : Application() {

    val component = DaggerApplicationComponent.create()

}