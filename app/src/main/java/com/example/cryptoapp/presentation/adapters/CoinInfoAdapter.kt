package com.example.cryptoapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.entity.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    ListAdapter<CoinInfo, CoinInfoAdapter.CoinInfoViewHolder>(CoinInfoDiffCallback()) {

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val itemBinding =
            ItemCoinInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinInfoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = getItem(position)
        with(holder) {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price
                tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivLogoCoin)
                itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    inner class CoinInfoViewHolder(viewBinding: ItemCoinInfoBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        val ivLogoCoin = viewBinding.ivLogoCoin
        val tvSymbols = viewBinding.tvSymbols
        val tvPrice = viewBinding.tvPrice
        val tvLastUpdate = viewBinding.tvLastUpdate
    }

    interface OnCoinClickListener {
        fun onCoinClick(coinInfo: CoinInfo)
    }
}
