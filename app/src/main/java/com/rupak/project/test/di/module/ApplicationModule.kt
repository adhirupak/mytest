package com.rupak.project.test.di.module

import android.content.Context
import com.rupak.project.test.utils.ImageLoader
import com.rupak.project.test.utils.PicassoImageLoader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Module
class ApplicationModule constructor(context: Context) {
    private val appContext = context.applicationContext

     @Singleton
     @Provides
     fun provideContext():Context{
         return appContext
     }

    @Singleton
    @Provides
    fun provideImageLoader(context: Context) : ImageLoader {
        return PicassoImageLoader(Picasso.with(context))
    }


}