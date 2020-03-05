package com.rupak.project.test.di.component

import com.rupak.project.test.di.module.ApplicationModule
import com.rupak.project.test.di.module.DataModule
import com.rupak.project.test.di.module.NetModule
import com.rupak.project.test.di.module.search.SearchModule
import com.rupak.project.test.di.module.search.SearchSubComponent
import dagger.Component
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
@Component(modules = [
    (ApplicationModule::class),
    (NetModule::class),
    (DataModule::class)])
interface MainAppComponent {
    fun plus(searchModule: SearchModule): SearchSubComponent
}