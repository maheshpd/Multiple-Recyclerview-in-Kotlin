package com.arfeenkhan.multiplerecyclerviewinkotlin.Interface

import android.view.View

interface IItemClickListener {
    fun onItemClickListener(view:View, position:Int)
}