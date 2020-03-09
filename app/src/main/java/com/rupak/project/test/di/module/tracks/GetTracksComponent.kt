package com.rupak.project.test.di.module.tracks

import com.rupak.project.test.ui.track_list.TrackListActivity
import dagger.Subcomponent

/**
 * Created By Rupak Adhikari
 */
@TrackScope
@Subcomponent(modules = [TracksModule::class])
interface GetTracksComponent {
    fun inject(getTrackListActivity: TrackListActivity)
}