package com.janpschwietzer.calpal.presentation.views.product.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.extensions.LocalDateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEatenProductViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val _product = MutableStateFlow<ProductModel?>(null)
    val product: StateFlow<ProductModel?> = _product

    private val _eatenProduct = MutableStateFlow(
        EatenProductModel(
            barcode = 0,
            date = LocalDateConverter.toTimestamp(LocalDate.now()) ?: 0,
            meal = MealTime.BREAKFAST,
            amount = 0,
            unit = ""
        )
    )
    val eatenProduct: StateFlow<EatenProductModel> = _eatenProduct

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadProduct(barcode: String?) {
        _isLoading.value = true
        viewModelScope.launch {
            val barcodeLong = barcode?.toLongOrNull()

            if (barcodeLong == null) {
                _isLoading.value = false
                return@launch
            } else {
                _product.value = productRepository.getProduct(barcodeLong)
                setupEatenProductValues()
                _isLoading.value = false
            }
        }
    }

    private fun setupEatenProductValues() {
        _eatenProduct.value = _eatenProduct.value.copy(
            barcode = product.value?.barcode ?: 0,
            amount = product.value?.portionSize ?: 0,
            unit = product.value?.portionUnit ?: ""
        )
    }

    fun setEatenProductAmount(amount: String) {
        _eatenProduct.value = _eatenProduct.value.copy(amount = amount.toIntOrNull()?: 0)
    }

    fun setEatenProductMealTime(mealTime: MealTime) {
        _eatenProduct.value = _eatenProduct.value.copy(meal = mealTime)
    }
}