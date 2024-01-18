package id.muhammadfaisal.mcommerce

import dagger.Component
import id.muhammadfaisal.mcommerce.activity.CartActivity
import id.muhammadfaisal.mcommerce.bottomsheet.PlaceOrderBottomSheetDialogFragment
import id.muhammadfaisal.mcommerce.module.NetworkModule

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(fragment: PlaceOrderBottomSheetDialogFragment)
    fun inject(fragment: CartActivity)
}