package de.rki.coronawarnapp.ui.settings

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import de.rki.coronawarnapp.util.viewmodel.CWAViewModel
import de.rki.coronawarnapp.util.viewmodel.CWAViewModelFactory
import de.rki.coronawarnapp.util.viewmodel.CWAViewModelKey

@Module
abstract class SettingsResetModule {
    @Binds
    @IntoMap
    @CWAViewModelKey(SettingsResetViewModel::class)
    abstract fun settingsResetVM(
        factory: SettingsResetViewModel.Factory
    ): CWAViewModelFactory<out CWAViewModel>

    @ContributesAndroidInjector
    abstract fun settingsResetFragment(): SettingsResetFragment
}
