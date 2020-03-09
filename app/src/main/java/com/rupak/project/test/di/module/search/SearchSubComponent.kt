package com.rupak.project.test.di.module.search
import com.rupak.project.test.ui.main.fragments.search.SearchFragment
import dagger.Subcomponent

/**
 * Created by Rupak Adhikari.
 */
@SearchScope
@Subcomponent(modules = [SearchModule::class])
interface SearchSubComponent {
    fun inject(searchFragment: SearchFragment)
}