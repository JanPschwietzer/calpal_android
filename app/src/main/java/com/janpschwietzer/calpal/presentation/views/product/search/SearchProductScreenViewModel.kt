package com.janpschwietzer.calpal.presentation.views.product.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.janpschwietzer.calpal.R
import com.janpschwietzer.calpal.data.model.ProductModel
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchEatenProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _textFieldValue = MutableStateFlow("")
    val textFieldValue: StateFlow<String> = _textFieldValue

    fun setTextFieldValue(value: String?) {
        _textFieldValue.value = value?: ""
    }

    private val _favoriteProducts = MutableStateFlow<List<ProductModel>>(emptyList())
    val favoriteProducts: StateFlow<List<ProductModel>> = _favoriteProducts
    private val _mostAddedProducts = MutableStateFlow<List<ProductModel>>(emptyList())
    val mostAddedProducts: StateFlow<List<ProductModel>> = _mostAddedProducts

    private val _selectedTabIndex = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex

    val tabs = listOf(R.string.favorites, R.string.often_added)

    fun onTabSelected(index: Int) {
        _selectedTabIndex.value = index
    }

    fun navigateToAddProductView(navController: NavHostController) {
        navController.navigate(Screen.AddProduct.createRoute(_textFieldValue.value)){ launchSingleTop = true }
    }

    fun loadFavoriteProducts() {
        viewModelScope.launch {
            _favoriteProducts.value = productRepository.getFavoriteProducts()
        }
    }

    fun loadMostAddedProducts() {
        viewModelScope.launch {
            _mostAddedProducts.value = productRepository.getMostAddedProducts()
        }
    }


}