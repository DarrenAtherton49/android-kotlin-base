package com.atherton.sample.presentation.util.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

internal class FragmentViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragmentIdList = mutableListOf<Long>()
    private val fragmentTitleList = mutableListOf<String>()
    private val fragmentList = mutableListOf<Fragment>()

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.count()

    override fun getPageTitle(position: Int): CharSequence = fragmentTitleList[position]

    override fun getItemId(position: Int): Long = fragmentIdList[position]

    override fun getItemPosition(item: Any): Int = fragmentList.indexOf(item)

    fun addFragment(id: Long, title: String, fragment: Fragment) {
        fragmentIdList.add(id)
        fragmentTitleList.add(title)
        fragmentList.add(fragment)
    }

    fun addFragmentToStart(id: Long, title: String, fragment: Fragment) {
        fragmentIdList.add(0, id)
        fragmentTitleList.add(0, title)
        fragmentList.add(0, fragment)
    }

    fun clear() {
        fragmentIdList.clear()
        fragmentTitleList.clear()
        fragmentList.clear()
    }
}
