package com.janpschwietzer.calpal.presentation.views.product.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSettingsViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val _product = MutableStateFlow<ProductModel?>(null)
    val product: StateFlow<ProductModel?> = _product

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
                _isLoading.value = false
            }
        }
    }
}