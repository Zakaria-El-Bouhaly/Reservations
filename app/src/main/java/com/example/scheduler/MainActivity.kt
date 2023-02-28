package com.example.scheduler

import ZoomAnimation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.scheduler.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var fragList: ArrayList<Fragment>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewPager = binding.pager
        fragList = ArrayList()
        fragList.add(LoginFragment())
        fragList.add(RegisterFragment())

        val pagerAdapter = SwipeAdapter(fragList, supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter
        viewPager.setPageTransformer(ZoomAnimation())
    }


}