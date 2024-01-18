package id.muhammadfaisal.mcommerce.viewmodel

import android.text.TextWatcher
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhammadfaisal.mcommerce.api.repository.InventoryRepository
import id.muhammadfaisal.mcommerce.api.request.AddProductRequest
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(private val repository: InventoryRepository) :
    ViewModel() {

    private val TAG = InventoryViewModel::class.simpleName

    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData<String>()
    private val _inventories = MutableLiveData<List<InventoryResponse>>()
    private var lastInventories: List<InventoryResponse>? = null
    private val _search = MutableLiveData<String>()

    /*START: SECTION ADD INVENTORY*/
    private val _productName = MutableLiveData<String>()
    private val _productPrice = MutableLiveData<String>()
    private val _productStock = MutableLiveData<String>()
    private val _productDescription = MutableLiveData<String>()
    private val _productImage = MutableLiveData<String>()
    private val _isUploadSuccess = MutableLiveData(false)

    val isUploadSuccess: MutableLiveData<Boolean>
        get() = _isUploadSuccess

    val productImage: MutableLiveData<String>
        get() = _productImage

    val productDescription: MutableLiveData<String>
        get() = _productDescription

    val productStock: MutableLiveData<String>
        get() = _productStock

    val productPrice: MutableLiveData<String>
        get() = _productPrice

    val productName: MutableLiveData<String>
        get() = _productName
    /*END: SECTION ADD INVENTORY*/

    val loading: MutableLiveData<Boolean>
        get() = _loading

    val error: MutableLiveData<String>
        get() = _error

    val inventories: MutableLiveData<List<InventoryResponse>>
        get() = _inventories

    val search: MutableLiveData<String>
        get() = _search

    fun search(query: String) {
        Log.i(TAG, "search() called with: query = $query")
        _inventories.postValue(_inventories.value?.filter {
            it.name.contains(query, true)
        })

        if (query.isEmpty()) {
            _inventories.postValue(lastInventories?.toList())
        }
    }

    fun getInventories() = viewModelScope.launch {
        Log.i(TAG, "getInventories() called")
        _loading.postValue(true)
        CompositeDisposable().add(
            repository
                .getInventories()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ it ->
                    Log.i(TAG, "getInventories() $it")
                    _loading.postValue(false)
                    if (it.isEmpty()) _error.postValue("Data is empty")
                    _inventories.postValue(it)
                    lastInventories = it
                }, {
                    Log.e(TAG, "getInventories() error: ${it.message}")
                    _loading.postValue(false)
                    _error.postValue(it.message)
                })
        )
    }

    fun addInventory() = viewModelScope.launch {
        Log.i(TAG, "addInventory() called")
        _loading.postValue(true)
        CompositeDisposable().add(
            repository
                .addInventory(
                    AddProductRequest(
                        name = _productName.value.toString(),
                        price = _productPrice.value.toString().toLong(),
                        stock = _productStock.value.toString().toInt(),
                        description = _productDescription.value.toString(),
                        image = _productImage.value.toString()
                    )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ it ->
                    Log.i(TAG, "addInventory() $it")
                    _loading.postValue(false)
                    if (it != null) {
                        _isUploadSuccess.postValue(true)
                    } else {
                        _isUploadSuccess.postValue(false)
                    }
                }, {
                    Log.e(TAG, "addInventory() error: ${it.message}")
                    _loading.postValue(false)
                    _isUploadSuccess.postValue(false)
                    _error.postValue(it.message)
                })
        )
    }
}