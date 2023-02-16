package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.cryptoapp.R
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.data.network.models.CoinInfoDto
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.cryptoapp.data.app.CoinApplication
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.repository.CoinsRepositoryImpl
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private val viewBinding by viewBinding(ActivityCoinPrceListBinding::bind)

    private val db by lazy {
        (application as CoinApplication).database
    }
    private val repository by lazy {
        CoinsRepositoryImpl(db, CoinsMapper())
    }
    private val loadDataUseCase by lazy {
        LoadDataUseCase(repository)
    }
    private val getCoinInfoUseCase by lazy {
        GetCoinInfoUseCase(repository)
    }
    private val getCoinInfoListUseCase by lazy {
        GetCoinInfoListUseCase(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_prce_list)
        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinInfo: CoinInfoDto) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        viewBinding.rvCoinPriceList.adapter = adapter
        viewModel =
            ViewModelFactory(loadDataUseCase, getCoinInfoListUseCase, getCoinInfoUseCase).create(
                CoinViewModel::class.java
            )
        viewModel.coinInfoList.observe(this, Observer {
            adapter.coinInfoList = emptyList() //TODO
        })
    }
}
