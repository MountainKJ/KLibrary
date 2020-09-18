package com.kj.klibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kj.klibrary.databinding.ActivityMainBinding
import com.kj.klibrary.event.EventLearnActivity
import com.kj.klibrary.model.TitleItem
import com.kj.klibrary.receiver.DPMTestActivity
import com.kj.klibrary.room.RoomLearnActivity

class MainActivity : AppCompatActivity(), OnItemClickListener {
    companion object{
        private const val ITEM_ID_ROOM = 101
        private const val ITEM_ID_EVENT = 102
        private const val ITEM_DEVICE_MANAGER_TEST = 103
    }

    private val mAdapter =  MainAdapter()
    private val itemList = mutableListOf(
        TitleItem("Room 数据库", ITEM_ID_ROOM),
        TitleItem("Event事件浅析", ITEM_ID_EVENT),
        TitleItem("DeviceMangerTest", ITEM_DEVICE_MANAGER_TEST)
    )

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
            ITEM_ID_EVENT -> startActivity(EventLearnActivity.createIntent(this))
            ITEM_DEVICE_MANAGER_TEST -> startActivity(DPMTestActivity.createIntent(this))
        }
    }

    class MainAdapter: BaseQuickAdapter<TitleItem, BaseViewHolder>(R.layout.item_activity_main_layout) {
        override fun convert(holder: BaseViewHolder, item: TitleItem) {
            holder.setText(R.id.item_activity_main_title_tv, item.title)
        }
    }
}