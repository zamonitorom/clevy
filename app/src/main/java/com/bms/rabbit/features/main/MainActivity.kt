package com.bms.rabbit.features.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

import com.bms.rabbit.R
import com.bms.rabbit.RabbitApp
import com.bms.rabbit.Router
import com.bms.rabbit.databinding.ActivityMainBinding
import com.bms.rabbit.entities.ScreenData
import com.bms.rabbit.features.profile.PaymentService
import com.bms.rabbit.features.task.FinishFragment


class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy { DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main) }
    private val rootViewModel: RootViewModel by lazy {
        RootViewModel((applicationContext as RabbitApp).baseComponent.router,
                (applicationContext as RabbitApp).baseComponent.authDbDataSource,
                PaymentService(this),
                (applicationContext as RabbitApp).baseComponent.messenger)
    }
    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router = (applicationContext as RabbitApp).baseComponent.router
        activityMainBinding.fragmentContainer.alpha = 1f
        router.setActivity(this)
        activityMainBinding.viewModel = rootViewModel

        if (intent != null) {
            if (intent.hasExtra("screen")) {
                val screenData = ScreenData(intent.getStringExtra("screen"), intent.getIntExtra("id",-1))
                startSession(screenData)
            } else {
                startSession()
            }
        } else {
            startSession()
        }
    }

    fun startSession(screenData: ScreenData = ScreenData("", 0)) {
        router.setActivity(this)
        rootViewModel.resolveScreen(screenData)
    }

    override fun onResume() {
        super.onResume()
        router.setActivity(this)
    }

    override fun onPause() {
        router.releaseActivity()
        super.onPause()
    }

    override fun onBackPressed() {
        Log.d("onBackPressed", supportFragmentManager.backStackEntryCount.toString())
        if (supportFragmentManager.backStackEntryCount > 1) {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (fragment !is FinishFragment) {
                super.onBackPressed()
            }
        } else {
            finish()
        }
    }
}
