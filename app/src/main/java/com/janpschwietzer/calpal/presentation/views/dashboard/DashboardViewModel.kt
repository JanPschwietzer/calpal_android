package com.janpschwietzer.calpal.presentation.views.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.EatenProductRepository
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _eatenCalories = MutableStateFlow<Int?>(null)
    val eatenCalories: StateFlow<Int?> = _eatenCalories

    private val _eatenProducts = MutableStateFlow<List<EatenProductModel>>(emptyList())
    val eatenProducts: StateFlow<List<EatenProductModel>> = _eatenProducts

    fun loadData() {
        viewModelScope.launch {
            _maxCalories.value = userRepository.getUser()?.kcalGoal
            _eatenProducts.value = eatenProductRepository.getEatenProducts(LocalDate.now())

            _eatenCalories.value = _eatenProducts.value.sumOf {
                val kcal = productRepository.getProduct(it.barcode)?.kcal
                if (kcal == null) 0
                else kcal / 100 * it.amount
            }
        }
    }
}
