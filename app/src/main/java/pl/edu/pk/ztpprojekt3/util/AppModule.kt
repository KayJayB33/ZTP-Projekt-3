package pl.edu.pk.ztpprojekt3.util

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.edu.pk.ztpprojekt3.api.RetrofitInstance
import pl.edu.pk.ztpprojekt3.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideProductRepository(): ProductRepository {
        return ProductRepository(RetrofitInstance.productService)
    }
}