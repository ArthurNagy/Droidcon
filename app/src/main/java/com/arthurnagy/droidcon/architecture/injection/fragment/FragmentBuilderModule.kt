package com.arthurnagy.droidcon.architecture.injection.fragment

import com.arthurnagy.droidcon.feature.agenda.MyAgendaFragment
import com.arthurnagy.droidcon.feature.info.InfoFragment
import com.arthurnagy.droidcon.feature.schedule.SchedulePagerFragment
import com.arthurnagy.droidcon.feature.schedule.list.ScheduleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMyAgendaFragment(): MyAgendaFragment

    @ContributesAndroidInjector
    abstract fun contributeSchedulePagerFragment(): SchedulePagerFragment

    @ContributesAndroidInjector
    abstract fun contributeScheduleFragment(): ScheduleFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): InfoFragment

}