package com.arthurnagy.droidcon.feature

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.arthurnagy.droidcon.MainBinding
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.SharedPreferencesManager
import com.arthurnagy.droidcon.feature.agenda.MyAgendaFragment
import com.arthurnagy.droidcon.feature.info.InfoFragment
import com.arthurnagy.droidcon.feature.schedule.SchedulePagerFragment
import com.arthurnagy.droidcon.util.replace
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<MainBinding>(this, R.layout.activity_main)
        val lastBottomNavTab = sharedPreferencesManager.getLastSelectedTab()
        binding.bottomNavigation.selectedItemId = if (lastBottomNavTab == 0) R.id.my_agenda else lastBottomNavTab
        navigateToFragment(binding.bottomNavigation.selectedItemId)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { navigationItem ->
            navigateToFragment(navigationItem.itemId)
            sharedPreferencesManager.setLastSelectedTab(navigationItem.itemId)
            true
        }
        binding.bottomNavigation.setOnNavigationItemReselectedListener {
            // TODO refresh current screen content
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    private fun navigateToFragment(bottomNavigationTabId: Int) {
        supportFragmentManager.replace(R.id.fragment_container, when (bottomNavigationTabId) {
            R.id.my_agenda -> MyAgendaFragment()
            R.id.schedule -> SchedulePagerFragment()
            R.id.info -> InfoFragment()
            else -> MyAgendaFragment()
        })
    }
}
