package dev.stukalo.asteroiddetails

import android.util.Log
import dev.stukalo.platform.BaseFragment

class FragmentAsteroidDetails : BaseFragment(R.layout.fragment_asteroid_details) {
    override fun configureUi() {
        super.configureUi()
        val a = arguments?.getString("arg_one").toString()
        Log.d("ARGONE_", a)
    }
}
