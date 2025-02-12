package com.janpschwietzer.calpal.presentation.views.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.janpschwietzer.calpal.data.model.EatenProductModel
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.EatenProductRepository
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.util.enums.MealTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val eatenProductRepository: EatenProductRepository,
    private val productRepository: ProductRepository
) : ViewModel() {
    private val _products = MutableStateFlow<List<ProductModel>>(emptyList())
    val products: StateFlow<List<ProductModel>> = _products

    private val _eatenProducts = MutableStateFlow<List<EatenProductModel>>(emptyList())
    val eatenProducts: StateFlow<List<EatenProductModel>> = _eatenProducts

    fun getProducts(mealTime: MealTime) {
        viewModelScope.launch(Dispatchers.IO) {
            _eatenProducts.value = eatenProductRepository.getEatenProducts(LocalDate.now(), mealTime = mealTime)
            _products.value = productRepository.getAllProducts()
        }
    }

    fun RemoveEatenItem(eatenProduct: EatenProductModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _eatenProducts.value = _eatenProducts.value.filter { it != eatenProduct }
            eatenProductRepository.removeEatenProduct(eatenProduct)
        }
    }

}