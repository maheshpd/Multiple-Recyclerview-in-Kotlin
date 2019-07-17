package com.arfeenkhan.multiplerecyclerviewinkotlin


import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arfeenkhan.multiplerecyclerviewinkotlin.Interface.IFirebaseLoaderListener
import com.arfeenkhan.multiplerecyclerviewinkotlin.adapter.MyGroupAdapter
import com.arfeenkhan.multiplerecyclerviewinkotlin.adapter.MyItemAdapter
import com.arfeenkhan.multiplerecyclerviewinkotlin.model.ItemData
import com.arfeenkhan.multiplerecyclerviewinkotlin.model.ItemGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IFirebaseLoaderListener {
    override fun onFirebaseLoadSuccess(itemGroupList: List<ItemGroup>) {
      val adapter = MyGroupAdapter(this@MainActivity,itemGroupList)
        my_recycler_view.adapter = adapter
        dialog.dismiss()
    }

    override fun onFirebaseLoadFailed(message: String) {
       dialog.dismiss()
        Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
    }

    lateinit var dialog: ProgressDialog
    lateinit var iFirebaseLoaderListener: IFirebaseLoaderListener
    lateinit var myData: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = ProgressDialog(this)
        dialog.setMessage("Please wait...")
        myData = FirebaseDatabase.getInstance().getReference("MyData")
        iFirebaseLoaderListener = this

        //View
        my_recycler_view.setHasFixedSize(true)
        my_recycler_view.layoutManager = LinearLayoutManager(this)
        getFirebaseData()
    }

    private fun getFirebaseData() {
        dialog.show()
        myData.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                iFirebaseLoaderListener.onFirebaseLoadFailed(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                val itemGroups = ArrayList<ItemGroup>()
                for (myDataSnapShot in p0.children) {
                    val itemGroup = ItemGroup()
                    itemGroup.headerTitle = myDataSnapShot.child("headerTitle")
                        .getValue(true).toString()
                    val t = object : GenericTypeIndicator<ArrayList<ItemData>>() {}
                    itemGroup.listItem = myDataSnapShot.child("listitem").getValue(t)
                    itemGroups.add(itemGroup)
                }

                iFirebaseLoaderListener.onFirebaseLoadSuccess(itemGroups)
            }

        })
    }
}
