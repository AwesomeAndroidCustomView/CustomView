package com.example.fangsf.customview.coordinatorlayout25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fangsf.customview.R
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.CommonAdapter
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.RecyclerCommonAdapter
import com.example.fangsf.customview.recyclerview_23.commonAdapter2.common.ViewHolder

class CoordinatorScrollActivity : AppCompatActivity() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }

    private val list by lazy {
        val list = mutableListOf<String>()
        repeat(30) {
            it.toString()
            list.add(it.toString())
        }
        list
    }

    private val recyclerViewAdapter = lazy {
        RecyclerViewAdapter(R.layout.item_textview, list).apply {
            recyclerView.layoutManager = LinearLayoutManager(this@CoordinatorScrollActivity)
            recyclerView.adapter = this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_scroll)


        if (!recyclerViewAdapter.isInitialized()) {
            recyclerViewAdapter.value
        }
    }


}

class RecyclerViewAdapter(res: Int, list: List<String>) : RecyclerCommonAdapter<String>(res, list) {


    override fun convert(holder: ViewHolder?, item: String?) {


    }

}