package de.rki.coronawarnapp.storage.interoperability

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import de.rki.coronawarnapp.service.applicationconfiguration.ApplicationConfigurationService
import de.rki.coronawarnapp.storage.LocalData
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.util.Locale

object InteroperabilityRepository {
    private val TAG: String? = InteroperabilityRepository::class.simpleName

    private val _countryList: MutableLiveData<List<String>> = MutableLiveData(listOf())
    val countryList = Transformations.distinctUntilChanged(_countryList)

    fun interoperabilityWasShown(): Boolean = LocalData.interoperabilityWasShown()

    fun saveInteroperabilityUsed() {
        LocalData.saveInteroperabilityUsed()
    }

    /**
     * Gets all countries from @see ApplicationConfigurationService.asyncRetrieveApplicationConfiguration
     * and filters out the CURRENT_COUNTRY from @see DiagnosisKeyConstants. Also changes every country code
     * to lower case
     */
    fun getAllCountries() {
        runBlocking {
            try {
                val countryList =
                    ApplicationConfigurationService.asyncRetrieveApplicationConfiguration()
                        .supportedCountriesList
                        ?.map { it.toLowerCase(Locale.ROOT) } ?: listOf()
                Timber.d("Countrylist: ${TextUtils.join(System.lineSeparator(), countryList)}")
                _countryList.postValue(countryList)
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
