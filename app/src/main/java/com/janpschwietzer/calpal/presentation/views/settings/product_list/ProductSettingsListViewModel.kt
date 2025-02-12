package com.janpschwietzer.calpal.presentation.views.settings.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSettingsListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> = _products

    private val _filterString = MutableStateFlow("")
    val filterString: StateFlow<String> = _filterString

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.value = productRepository.getAllProducts()
        }
    }

    fun filterProducts(input: String) {
        _filterString.value = input

        if (input.isEmpty()) {
            loadProducts()
            return
        }

        _products.value = _products.value.filter {
            it.name?.contains(input, ignoreCase = true) == true
        }
    }
}