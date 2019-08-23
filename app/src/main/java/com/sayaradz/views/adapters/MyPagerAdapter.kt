package com.sayaradz.views.adapters


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sayaradz.R
import com.sayaradz.views.fragments.FollowedModelFragment
import com.sayaradz.views.fragments.FollowedVersionFragment

class MyPagerAdapter(fm: FragmentManager, var context: Context) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FollowedVersionFragment()
            }
            else -> {
                return FollowedModelFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> context.getString(R.string.versions_title)
            else -> {
                return context.getString(R.string.models_title)
            }
        }
    }
}