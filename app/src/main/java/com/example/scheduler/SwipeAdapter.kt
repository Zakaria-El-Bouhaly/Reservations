package com.example.scheduler

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter


class SwipeAdapter(val frgmtList: ArrayList<Fragment>, val fm: FragmentManager, val lc: Lifecycle) :

    FragmentStateAdapter(fm, lc) {
    override fun getItemCount(): Int {
        return frgmtList.size
    }

    override fun createFragment(position: Int): Fragment {
        return frgmtList[position]
    }

}
