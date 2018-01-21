package com.travelsloth.tripplanner

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView



class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val mRecyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)
//
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true)
//
//        // use a linear layout manager
//        val mLayoutManager = LinearLayoutManager(this)
//        mRecyclerView.layoutManager = mLayoutManager
//
//        // specify an adapter (see also next example)
//        val data = arrayOf("January", "February", "March")
//        val mAdapter = MyAdapter(data)
//        mRecyclerView.adapter = mAdapter

    }
}
