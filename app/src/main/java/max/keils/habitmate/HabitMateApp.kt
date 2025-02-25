package max.keils.habitmate

import android.app.Application
import max.keils.habitmate.di.ApplicationComponent
import max.keils.habitmate.di.ApplicationScope
import max.keils.habitmate.di.DaggerApplicationComponent

@ApplicationScope
class HabitMateApp : Application() {

    val component: ApplicationComponent = DaggerApplicationComponent.create()

}