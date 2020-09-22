package de.rki.coronawarnapp.ui.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import de.rki.coronawarnapp.storage.interoperability.InteroperabilityRepository

/**
 * ViewModel for everything Interoperability related
 *
 * @see InteroperabilityRepository
 */
class InteroperabilityViewModel : ViewModel() {

    init {
        InteroperabilityRepository.getAllCountries()
    }

    val allCountries = InteroperabilityRepository.countryList
    val isCountryListEmpty = Transformations.map(allCountries) { it.isNullOrEmpty() }

    val interoperabilityWasShown = InteroperabilityRepository.interoperabilityWasShown()

    fun saveInteroperabilityUsed() {
        InteroperabilityRepository.saveInteroperabilityUsed()
    }
}
