package com.janpschwietzer.calpal.presentation.views.settings.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.ProductRepository
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

    fun updateName(name: String) {
        _product.value = _product.value.copy(name = name)
    }

    fun loadProduct(barcode: Long?) {
        if (barcode == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val product = productRepository.getProduct(barcode)
            if (product != null) {
                _product.value = product
            }
        }
    }
}