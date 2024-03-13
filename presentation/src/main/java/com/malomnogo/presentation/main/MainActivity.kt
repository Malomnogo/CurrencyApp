package com.malomnogo.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.malomnogo.presentation.core.UpdateUi
import dagger.hilt.android.AndroidEntryPoint
import ru.easycode.presentation.R
import ru.easycode.presentation.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationObserver: UpdateUi<Screen>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationObserver = object : UpdateUi<Screen> {
            override fun updateUi(uiState: Screen) {
                uiState.show(R.id.container, supportFragmentManager)
            }
        }
        viewModel.init(isFirstRun = savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(navigation = navigationObserver)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }
}