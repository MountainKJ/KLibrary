package com.kj.klibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kj.klibrary.databinding.ActivityMainBinding
import com.kj.klibrary.model.TitleItem
import com.kj.klibrary.room.RoomLearnActivity

class MainActivity : AppCompatActivity(), OnItemClickListener {
    companion object{
        private const val ITEM_ID_ROOM = 101
    }

    private val mAdapter =  MainAdapter()
    private val itemList = mutableListOf(TitleItem("Room 数据库", ITEM_ID_ROOM))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        mAdapter.setOnItemClickListener(this)
        viewBinding.activityMainRv.adapter = mAdapter
        mAdapter.addData(itemList)

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when(mAdapter.getItem(position).itemId) {
            ITEM_ID_ROOM -> startActivity(RoomLearnActivity.createIntent(this))
        }
    }

    class MainAdapter: BaseQuickAdapter<TitleItem, BaseViewHolder>(R.layout.item_activity_main_layout) {
        override fun convert(holder: BaseViewHolder, item: TitleItem) {
            holder.setText(R.id.item_activity_main_title_tv, item.title)
        }
    }
}