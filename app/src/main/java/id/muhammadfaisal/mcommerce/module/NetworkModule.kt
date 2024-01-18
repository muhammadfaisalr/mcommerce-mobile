package id.muhammadfaisal.mcommerce.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.muhammadfaisal.mcommerce.api.ApiServices
import id.muhammadfaisal.mcommerce.api.repository.InventoryRepository
import id.muhammadfaisal.mcommerce.api.repository.PlaceOrderRepository
import id.muhammadfaisal.mcommerce.util.RetrofitBuilder

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideApiService(): ApiServices {
        return RetrofitBuilder.getRetrofit().create(ApiServices::class.java)
    }

    @Provides
    fun provideInventoryRepository(): InventoryRepository {
        return InventoryRepository(provideApiService())
    }

    @Provides
    fun providePlaceOrderRepository(): PlaceOrderRepository {
        return PlaceOrderRepository(provideApiService())
    }
}