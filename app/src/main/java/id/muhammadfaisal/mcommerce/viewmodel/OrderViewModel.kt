package id.muhammadfaisal.mcommerce.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.muhammadfaisal.mcommerce.api.repository.PlaceOrderRepository
import id.muhammadfaisal.mcommerce.api.request.OrderItem
import id.muhammadfaisal.mcommerce.api.request.PlaceOrderRequest
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailResponse
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponse
import id.muhammadfaisal.mcommerce.model.Cart
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val repository: PlaceOrderRepository) : ViewModel() {
    private val TAG = OrderViewModel::class.simpleName
    private val _name = MutableLiveData<String>()
    private val _productId = MutableLiveData<Long>()
    private val _quantity = MutableLiveData<String>()
    private val _amount = MutableLiveData<Long>()
    private val _invoice = MutableLiveData<String>()

    private val _loading = MutableLiveData(false)
    private val _error = MutableLiveData<String>()

    private val _placeOrderResponse = MutableLiveData<PlaceOrderResponse>()
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    private val _invoiceDetailResponse = MutableLiveData<InvoiceDetailResponse>()
    private val _cart = MutableLiveData<Cart>()

    val name: MutableLiveData<String>
        get() = _name

    val productId: MutableLiveData<Long>
        get() = _productId

    val quantity: MutableLiveData<String>
        get() = _quantity

    val amount: MutableLiveData<Long>
        get() = _amount

    val loading: MutableLiveData<Boolean>
        get() = _loading

    val error: MutableLiveData<String>
        get() = _error

    val placeOrderResponse: MutableLiveData<PlaceOrderResponse>
        get() = _placeOrderResponse

    val orderItems: MutableLiveData<List<OrderItem>>
        get() = _orderItems

    val invoice: MutableLiveData<String>
        get() = _invoice

    val invoiceDetailResponse: MutableLiveData<InvoiceDetailResponse>
        get() = _invoiceDetailResponse

    val cart: MutableLiveData<Cart>
        get() = _cart

    fun addOrderItem(isPurchaseProduct: Boolean, inventoryResponse: InventoryResponse) = viewModelScope.launch {
        Log.i(TAG, "addOrderItem() called")
        _quantity.value?.let {
            if (it.isEmpty()) {
                _error.postValue("Quantity must be filled")
                return@launch
            }

            if (Integer.parseInt(it) <= 0) {
                _error.postValue("Quantity must be greater than 0")
                return@launch
            }

            val orderItem = OrderItem(
                name = inventoryResponse.name,
                productId = inventoryResponse.id.toLong(),
                quantity = Integer.parseInt(it),
                amount = inventoryResponse.price.toLong() * Integer.parseInt(it)
            )
            Log.i(TAG, "addOrderItem() called with: orderItem = $orderItem")
            val orderItems = _orderItems.value?.toMutableList() ?: mutableListOf()
            Log.i(TAG, "addOrderItem() called with: orderItems = $orderItems")
            orderItems?.add(orderItem)
            orderItems?.let {
                Log.i(TAG, "addOrderItem() called with: it = $it")
                _orderItems.postValue(it)
            }

            if (isPurchaseProduct) {
                //delay 2seconds
                Log.i(TAG, "addOrderItem() delay 2 seconds")
                CompositeDisposable().add(
                    io.reactivex.Observable.just(true)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            placeOrder()
                        }
                )
            } else {
                //Add to cart
                Log.i(TAG, "addOrderItem() add to cart")
                _cart.value = (Cart(
                    name = inventoryResponse.name,
                    productId = inventoryResponse.id.toLong(),
                    quantity = Integer.parseInt(it),
                    amount = inventoryResponse.price.toLong(),
                    image = inventoryResponse.image
                ))
            }
        }
    }

    fun purchaseFromCart(carts: List<Cart>) = viewModelScope.launch {
        Log.i(TAG, "purchaseFromCart() called with: carts = $carts")
        val orderItems = mutableListOf<OrderItem>()
        carts.forEach {
            orderItems.add(OrderItem(
                name = it.name,
                productId = it.productId,
                quantity = it.quantity,
                amount = it.amount
            ))
        }
        _orderItems.value = orderItems
        placeOrder()

    }

    fun placeOrder() = viewModelScope.launch {
        _loading.postValue(true)
        Log.i(TAG, "placeOrder() called with: _orderItems = ${_orderItems.value}")
        orderItems.value?.let {
            CompositeDisposable().add(
                repository
                    .placeOrder(PlaceOrderRequest(it))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it2 ->
                        Log.i(TAG, "placeOrder() called with: it2 = $it2")
                        _loading.postValue(false)
                        _placeOrderResponse.postValue(it2)
                    }, { it2 ->
                        Log.e(TAG, "placeOrder() called with: it2 = $it2")
                        _loading.postValue(false)
                        _error.postValue(it2.message)
                    })
            )
        }
    }

    fun findInvoice() = viewModelScope.launch {
        _loading.postValue(true)
        Log.i(TAG, "findInvoice() called with: _invoice = ${_invoice.value}")
        invoice.value?.let {
            CompositeDisposable().add(
                repository
                    .getDetailOrder(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it2 ->
                        Log.i(TAG, "findInvoice() called with: it2 = $it2")
                        _loading.postValue(false)
                        _invoiceDetailResponse.postValue(it2)
                    }, { it2 ->
                        Log.e(TAG, "findInvoice() called with: it2 = $it2")
                        _loading.postValue(false)
                        _error.postValue(it2.message)
                    })
            )
        }
    }
}