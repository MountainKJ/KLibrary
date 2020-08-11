package com.kj.klibrary.room

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.kj.core.base.vm.NoViewModel
import com.kj.klibrary.R
import com.kj.klibrary.SupperActivity
import com.kj.klibrary.databinding.ActivityRoomLearnBinding
import kotlin.random.Random

class RoomLearnActivity : SupperActivity<NoViewModel>(), View.OnClickListener {
    companion object {
        fun createIntent(context: Context) = Intent(context, RoomLearnActivity::class.java)
    }

    private lateinit var mBinding: ActivityRoomLearnBinding

    override fun getContentViewV(): View {
        mBinding = ActivityRoomLearnBinding.inflate(layoutInflater)
        return mBinding.root
    }

    private val mAdapter = RoomLearnAdapter()
    override fun doOnCreate(savedInstanceState: Bundle?) {
        super.doOnCreate(savedInstanceState)
        mBinding.activityRoomLearnInsertTv.setOnClickListener(this)
        mBinding.activityRoomLearnDeleteTv.setOnClickListener(this)
        mBinding.activityRoomLearnUpdateTv.setOnClickListener(this)
        mBinding.activityRoomLearnQueryTv.setOnClickListener(this)
        mBinding.activityRoomLearnRv.adapter = mAdapter
        notifyAdapterData()
    }

    override fun onClick(v: View?) = when(v) {
        mBinding.activityRoomLearnInsertTv -> insertUser()
        mBinding.activityRoomLearnDeleteTv -> {}
        mBinding.activityRoomLearnUpdateTv -> {}
        mBinding.activityRoomLearnQueryTv -> {}
        else -> {}
    }

    private fun insertUser() {
        launchStart({
            val uid = (1..1000).random()
            RoomHelper.get().db.userDao().insertAll(User(uid,"许", lastName = "大锋", sex = 1, age = 27, address = "重庆梁平"))
            true
        }, {
            notifyAdapterData()
            ToastUtils.showShort("插入成功")
        }, {
            LogUtils.e("插入异常:${it.message}")
        })
    }

    private fun notifyAdapterData() {
        mAdapter.setNewInstance(arrayListOf())
        launchStart({
            RoomHelper.get().db.userDao().getAll()
        }, {
            it?.let { userList ->
                mAdapter.addData(userList)
            }
        })
    }

    inner class RoomLearnAdapter : BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_room_learn_layout) {
        override fun convert(holder: BaseViewHolder, item: User) {
            holder.setText(R.id.item_room_learn_tv, item.toString())
        }
    }
}