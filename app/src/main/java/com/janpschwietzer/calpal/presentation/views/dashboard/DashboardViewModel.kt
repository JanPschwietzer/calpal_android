package com.janpschwietzer.calpal.presentation.views.dashboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.repository.EatenProductRepository
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.data.repository.UserRepository
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.enums.PortionUnit
import com.janpschwietzer.calpal.util.helpers.CalculationHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val eatenProductRepository: EatenProductRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _maxCalories = MutableStateFlow<Int?>(null)
    val maxCalories: StateFlow<Int?> = _maxCalories

    private val _eatenCalories = MutableStateFlow(0)
    val eatenCalories: StateFlow<Int> = _eatenCalories

    private val _eatenProducts = MutableStateFlow<List<EatenProductModel>>(emptyList())
    val eatenProducts: StateFlow<List<EatenProductModel>> = _eatenProducts

    private val _eatenMeals = mutableMapOf(
        MealTime.BREAKFAST to MutableStateFlow(0),
        MealTime.LUNCH to MutableStateFlow(0),
        MealTime.DINNER to MutableStateFlow(0),
        MealTime.SNACK to MutableStateFlow(0)
    )
    val totalEatenFlow = combine(
        _eatenMeals[MealTime.BREAKFAST] ?: MutableStateFlow(0),
        _eatenMeals[MealTime.LUNCH] ?: MutableStateFlow(0),
        _eatenMeals[MealTime.DINNER] ?: MutableStateFlow(0),
        _eatenMeals[MealTime.SNACK] ?: MutableStateFlow(0)
    ) { breakfast, lunch, dinner, snack ->
        mapOf(
            MealTime.BREAKFAST to breakfast,
            MealTime.LUNCH to lunch,
            MealTime.DINNER to dinner,
            MealTime.SNACK to snack
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())



    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _maxCalories.value = userRepository.getUser()?.kcalGoal
            _eatenProducts.value = eatenProductRepository.getEatenProducts(LocalDate.now())

            _eatenCalories.value = 0
            _eatenMeals[MealTime.BREAKFAST]?.value = 0
            _eatenMeals[MealTime.LUNCH]?.value = 0
            _eatenMeals[MealTime.DINNER]?.value = 0
            _eatenMeals[MealTime.SNACK]?.value = 0

            _eatenProducts.value.forEach { value ->
                val prod = productRepository.getProduct(value.barcode)

                if (prod?.kcal != null) {
                    val amount = CalculationHelper.calculateCalories(value, prod)
                    _eatenCalories.value += amount

                    if (_eatenMeals[value.meal]?.value == null) {
                        _eatenMeals[value.meal]?.value = amount
                    } else {
                        _eatenMeals[value.meal]?.value = _eatenMeals[value.meal]?.value?.plus(amount)!!
                    }
                }
            }

            Log.d("DashboardViewModel", "loadData: ${_eatenProducts.value}")
        }
    }
}
