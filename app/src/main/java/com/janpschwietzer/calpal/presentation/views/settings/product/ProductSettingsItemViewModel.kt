package com.janpschwietzer.calpal.presentation.views.settings.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.util.enums.PortionUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProductSettingsItemViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _product = MutableStateFlow(ProductModel(
        barcode = 1337
    ))
    val product: StateFlow<ProductModel> = _product

    fun loadProduct(barcode: Long?) {
        if (barcode == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val product = productRepository.getProduct(barcode)
            if (product != null) {
                _product.value = product
            }
        }
    }

    fun updateName(name: String) {
        _product.value = _product.value.copy(name = name)
    }

    fun updateBrand(brand: String) {
        _product.value = _product.value.copy(brand = brand)
    }

    fun updateKcal(kcal: String) {
        _product.value = _product.value.copy(kcal = kcal.toIntOrNull())
    }

    fun updateCarbs(s: String) {
        _product.value = _product.value.copy(carbs = s.toDoubleOrNull())
    }

    fun updatePortionSize(s: String) {
        _product.value = _product.value.copy(portionSize = s.toIntOrNull())
    }

    fun updateSugar(s: String) {
        _product.value = _product.value.copy(sugar = s.toDoubleOrNull())
    }

    fun updateFat(s: String) {
        _product.value = _product.value.copy(fat = s.toDoubleOrNull())
    }

    fun updateSaturatedFat(s: String) {
        _product.value = _product.value.copy(saturatedFat = s.toDoubleOrNull())
    }

    fun updateProtein(s: String) {
        _product.value = _product.value.copy(protein = s.toDoubleOrNull())
    }

    fun updateFiber(s: String) {
        _product.value = _product.value.copy(fiber = s.toDoubleOrNull())
    }

    fun updatePortionUnit(s: String?) {
        _product.value = _product.value.copy(portionUnit = s)
    }

    fun updateSalt(s: String) {
        _product.value = _product.value.copy(salt = s.toDoubleOrNull())
    }

    fun saveProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.saveProduct(_product.value)
        }
    }
}