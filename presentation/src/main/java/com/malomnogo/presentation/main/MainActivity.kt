package com.malomnogo.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.malomnogo.presentation.core.CustomViewModel
import com.malomnogo.presentation.core.ProvideViewModel
import com.malomnogo.presentation.core.UpdateUi
import ru.easycode.presentation.R
import ru.easycode.presentation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ProvideViewModel {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigationObserver: UpdateUi<Screen>
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = viewModel(MainViewModel::class.java)

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

    override fun <T : CustomViewModel> viewModel(clazz: Class<T>) =
        (application as ProvideViewModel).viewModel(clazz)
}