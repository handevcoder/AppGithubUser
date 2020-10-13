package com.iwansyy.appgithub.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.iwansyy.appgithub.fragment.FollowersFragment
import com.iwansyy.appgithub.fragment.FollowingFragment

class SectionsPagerAdapter(private val tab: Array<String>, private val username: String, fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        when (position){
            0 -> fragment = FollowersFragment().apply {
                followers = username
            }
            1 -> fragment = FollowingFragment().apply {
                following = username
            }
        }
        return fragment
    }

    override fun getCount(): Int = tab.size
    override fun getPageTitle(position: Int): CharSequence? = tab[position]
}