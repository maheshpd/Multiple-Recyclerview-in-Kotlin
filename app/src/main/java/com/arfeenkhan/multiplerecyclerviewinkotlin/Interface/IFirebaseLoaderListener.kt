package com.arfeenkhan.multiplerecyclerviewinkotlin.Interface

import com.arfeenkhan.multiplerecyclerviewinkotlin.model.ItemGroup

interface IFirebaseLoaderListener {
    fun onFirebaseLoadSuccess(itemGroupList:List<ItemGroup>)
    fun onFirebaseLoadFailed(message:String)
}