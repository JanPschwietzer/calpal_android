package com.janpschwietzer.calpal.presentation.views.product.add

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.EatenProductRepository
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.util.enums.MealTime
import com.janpschwietzer.calpal.util.enums.PortionUnit
import com.janpschwietzer.calpal.util.extensions.LocalDateConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddEatenProductViewModel @Inject constructor(
    private val eatenProductRepository: EatenProductRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _product = MutableStateFlow<ProductModel?>(null)
    val product: StateFlow<ProductModel?> = _product

    private val _eatenProduct = MutableStateFlow(
        EatenProductModel(
            id = null,
            barcode = 0,
            date = LocalDateConverter.toTimestamp(LocalDate.now()) ?: 0,
            meal = MealTime.BREAKFAST,
            amount = 1.0,
            unit = PortionUnit.PORTION
        )
    )
    val eatenProduct: StateFlow<EatenProductModel> = _eatenProduct

    private val _amount = MutableStateFlow<String?>("1")
    val amount: StateFlow<String?> = _amount

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadProduct(barcode: String?) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val barcodeLong = barcode?.toLongOrNull()

            if (barcodeLong == null) {
                _isLoading.value = false
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
            amount = _amount.value?.replace(",", ".")?.toDoubleOrNull() ?: 0.0
        )
    }

    fun setEatenProductAmount(amount: String) {
        _amount.value = amount
        _eatenProduct.value = _eatenProduct.value.copy(amount = (amount.replace(",", ".").toDoubleOrNull() ?: 0.0))
    }

    fun setEatenProductMealTime(mealTime: MealTime) {
        _eatenProduct.value = _eatenProduct.value.copy(meal = mealTime)
    }

    fun setEatenProductPortionUnit(portionUnit: PortionUnit) {
        setEatenProductAmount(_amount.value.toString())
        _eatenProduct.value = _eatenProduct.value.copy(unit = portionUnit)
    }

    fun favoriteProduct() {
        val product = product.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.toggleFavorite(product)
            productRepository.getProduct(product.barcode)?.let {
                _product.value = it
            }
        }
    }

    fun buildPortionUnitString(context: Context): String {
        if (product.value?.portionSize == null) {
            return "${context.getString(R.string.amount)}: ${product.value?.portionUnit?: "g"}"
        }
        return "${context.getString(R.string.amount)}: (${product.value?.portionSize}${product.value?.portionUnit?: "g"})"
    }

    fun saveEatenProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            eatenProductRepository.saveEatenProduct(eatenProduct.value)
            if (product.value?.barcode != null) {
                productRepository.incrementTimesAdded(product.value?.barcode?: 0)
            }
        }
    }
}