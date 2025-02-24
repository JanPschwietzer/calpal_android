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

    private val _kcal = MutableStateFlow<String?>(null)
    val kcal: StateFlow<String?> = _kcal

    private val _carbs = MutableStateFlow<String?>(null)
    val carbs: StateFlow<String?> = _carbs

    private val _sugar = MutableStateFlow<String?>(null)
    val sugar: StateFlow<String?> = _sugar

    private val _fat = MutableStateFlow<String?>(null)
    val fat: StateFlow<String?> = _fat

    private val _saturatedFat = MutableStateFlow<String?>(null)
    val saturatedFat: StateFlow<String?> = _saturatedFat

    private val _protein = MutableStateFlow<String?>(null)
    val protein: StateFlow<String?> = _protein

    private val _fiber = MutableStateFlow<String?>(null)
    val fiber: StateFlow<String?> = _fiber

    private val _portionSize = MutableStateFlow<String?>(null)
    val portionSize: StateFlow<String?> = _portionSize

    private val _salt = MutableStateFlow<String?>(null)
    val salt: StateFlow<String?> = _salt

    fun loadProduct(barcode: Long?) {
        if (barcode == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val product = productRepository.getProduct(barcode)
            if (product != null) {
                _product.value = product

                _kcal.value = product.kcal?.toString()
                _carbs.value = product.carbs?.toString()
                _sugar.value = product.sugar?.toString()
                _fat.value = product.fat?.toString()
                _saturatedFat.value = product.saturatedFat?.toString()
                _protein.value = product.protein?.toString()
                _fiber.value = product.fiber?.toString()
                _portionSize.value = product.portionSize?.toString()
                _salt.value = product.salt?.toString()
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
        _kcal.value = kcal
        _product.value = _product.value.copy(kcal = kcal.toIntOrNull())
    }

    fun updateCarbs(s: String) {
        _carbs.value = s
        _product.value = _product.value.copy(carbs = s.replace(",", ".").toDoubleOrNull())
    }

    fun updatePortionSize(s: String) {
        _portionSize.value = s
        _product.value = _product.value.copy(portionSize = s.toIntOrNull())
    }

    fun updateSugar(s: String) {
        _sugar.value = s
        _product.value = _product.value.copy(sugar = s.replace(",", ".").toDoubleOrNull())
    }

    fun updateFat(s: String) {
        _fat.value = s
        _product.value = _product.value.copy(fat = s.replace(",", ".").toDoubleOrNull())
    }

    fun updateSaturatedFat(s: String) {
        _saturatedFat.value = s
        _product.value = _product.value.copy(saturatedFat = s.replace(",", ".").toDoubleOrNull())
    }

    fun updateProtein(s: String) {
        _protein.value = s
        _product.value = _product.value.copy(protein = s.replace(",", ".").toDoubleOrNull())
    }

    fun updateFiber(s: String) {
        _fiber.value = s
        _product.value = _product.value.copy(fiber = s.replace(",", ".").toDoubleOrNull())
    }

    fun updatePortionUnit(s: String?) {
        _product.value = _product.value.copy(portionUnit = s)
    }

    fun updateSalt(s: String) {
        _salt.value = s
        _product.value = _product.value.copy(salt = s.replace(",", ".").toDoubleOrNull())
    }

    fun saveProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.saveProduct(_product.value)
        }
    }
}