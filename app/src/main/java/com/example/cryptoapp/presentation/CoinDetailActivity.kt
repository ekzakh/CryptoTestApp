package com.example.cryptoapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.cryptoapp.R
import com.example.cryptoapp.app.CoinApplication
import com.example.cryptoapp.data.mappers.CoinsMapper
import com.example.cryptoapp.data.repository.CoinsRepositoryImpl
import com.example.cryptoapp.databinding.ActivityCoinDetailBinding
import com.example.cryptoapp.domain.usecases.GetCoinInfoListUseCase
import com.example.cryptoapp.domain.usecases.GetCoinInfoUseCase
import com.example.cryptoapp.domain.usecases.LoadDataUseCase
import com.squareup.picasso.Picasso

class CoinDetailActivity : AppCompatActivity() {

    private val viewBinding by viewBinding(ActivityCoinDetailBinding::bind)

    private lateinit var viewModel: CoinViewModel
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
        setContentView(R.layout.activity_coin_detail)
        if (!intent.hasExtra(EXTRA_FROM_SYMBOL)) {
            finish()
            return
        }
        val fromSymbol = intent.getStringExtra(EXTRA_FROM_SYMBOL)
            ?: throw IllegalStateException("fromSymbol is required $this")
        viewModel =
            ViewModelFactory(loadDataUseCase, getCoinInfoListUseCase, getCoinInfoUseCase).create(
                CoinViewModel::class.java
            )
        viewModel.getDetailInfo(fromSymbol).observe(this, Observer {
            with(viewBinding) {
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.hightDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        })
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"

        fun newIntent(context: Context, fromSymbol: String): Intent {
            val intent = Intent(context, CoinDetailActivity::class.java)
            intent.putExtra(EXTRA_FROM_SYMBOL, fromSymbol)
            return intent
        }
    }
}
