package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.cryptoapp.R
import com.example.cryptoapp.app.CoinApplication
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.repository.CoinsRepositoryImpl
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.domain.entity.CoinInfo
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {

    private lateinit var viewModel: CoinViewModel
    private val viewBinding by viewBinding(ActivityCoinPrceListBinding::bind)

    private val db by lazy {
        (application as CoinApplication).database
    }
    private val repository by lazy {
        CoinsRepositoryImpl(db.coinPriceInfoDao(), CoinsMapper(), application)
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
            override fun onCoinClick(coinInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        viewBinding.rvCoinPriceList.adapter = adapter
        viewBinding.rvCoinPriceList.itemAnimator = null
        viewModel =
            ViewModelFactory(loadDataUseCase, getCoinInfoListUseCase, getCoinInfoUseCase).create(
                CoinViewModel::class.java
            )
        viewModel.coinInfoList.observe(this, Observer {
            adapter.submitList(it)
        })
    }
}
