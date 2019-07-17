package com.arfeenkhan.multiplerecyclerviewinkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arfeenkhan.multiplerecyclerviewinkotlin.R
import com.arfeenkhan.multiplerecyclerviewinkotlin.model.ItemGroup

class MyGroupAdapter(
    private val context: Context,
    private val dataList: List<ItemGroup>?
) : RecyclerView.Adapter<MyGroupAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_group, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemTitle.text = dataList!![position].headerTitle
        var items = dataList[position].listItem

        val itemListAdapter = MyItemAdapter(context, items)
        holder.recycler_view_list.setHasFixedSize(true)
        holder.recycler_view_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        holder.recycler_view_list.adapter = itemListAdapter
        holder.recycler_view_list.isNestedScrollingEnabled = false  //Important
        holder.btnMore.setOnClickListener {
            Toast.makeText(context, "Btn More" + dataList!![position].headerTitle, Toast.LENGTH_SHORT).show()
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTitle: TextView
        var recycler_view_list: RecyclerView
        var btnMore: Button

        init {
            itemTitle = view.findViewById(R.id.itemTitle) as TextView
            btnMore = view.findViewById(R.id.btnMore) as Button
            recycler_view_list = view.findViewById(R.id.recycler_view_list) as RecyclerView
        }
    }


}