package com.kj.core.base

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.blankj.utilcode.util.ToastUtils
import com.kj.core.R
import com.kj.core.base.vm.BaseViewModel
import com.kj.core.base.vm.Message
import java.lang.reflect.ParameterizedType

abstract class BaseDialogFragment<VM: BaseViewModel> : DialogFragment(){

    companion object{
        const val TAG = "BaseDialogFragment"
    }

    //设置tag
    protected open fun getFragmentTag(): String =
        TAG

    //设置是否可取消
    protected open fun getCancelOutside(): Boolean = true

    //背景透明度
    protected open fun getDimAmount() = 0.6F

    //宽度
    protected open fun getWidth() = WindowManager.LayoutParams.MATCH_PARENT

    //高度
    protected open fun getHeight() = WindowManager.LayoutParams.MATCH_PARENT

    //是否使用dialog的高度
    protected open fun useDialogHeight() = false

    //位置
    protected open fun getGravity() = Gravity.BOTTOM

    @LayoutRes
    protected abstract fun getContentView(): Int

    private lateinit var mFragmentManager: FragmentManager

    protected fun setFragmentManager(manager: FragmentManager) {
        this.mFragmentManager = manager
    }

     fun show(fragmentManager: FragmentManager = mFragmentManager) {
        show(fragmentManager, getFragmentTag())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, getStyle())
    }

    protected open lateinit var rootView: View
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        lifecycle.addObserver(viewModel)
        //注册 UI事件
        registerDefUIChange()
        initView(view)
        rootView = view
    }

    @StyleRes
    protected open fun getStyle(): Int = R.style.SideFromTopDialog

    private var dismissListener: DialogInterface.OnDismissListener? = null
    fun makeDismissListener(dismissListener: DialogInterface.OnDismissListener) : BaseDialogFragment<VM> {
        this.dismissListener = dismissListener
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismissListener?.let {
            it.onDismiss(dialog)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.let {
            val window = it.window
            window?.let { wd ->
                wd.requestFeature(Window.FEATURE_NO_TITLE)
                wd.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            it.setCanceledOnTouchOutside(getCancelOutside())
        }
        return inflater.inflate(getContentView(), container, false)
    }

    protected open fun initView(rootView: View) {}

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            val params = attributes
            params.dimAmount = getDimAmount()
            params.width = getWidth()
            if(useDialogHeight()) {
                val dialogHeight = getContentRect(activity)
                params.height = dialogHeight
            }else {
                params.height = getHeight()
            }

            params.gravity = getGravity()
            attributes = params
        }
    }

    private fun getContentRect(activity: Activity?): Int {
        val outRect = Rect()
        activity?.window?.decorView?.getWindowVisibleDisplayFrame(outRect)
        return outRect.height()
    }

    protected open fun overrideShow() = false
    override fun show(manager: FragmentManager, tag: String?) {
        if(!overrideShow()) {
            super.show(manager, tag)
        }else {
            try {
                try {
                    val c = Class.forName("androidx.fragment.app.DialogFragment")
                    val con = c.getConstructor()
                    val obj = con.newInstance()
                    val dismissed = c.getDeclaredField("mDismissed")
                    dismissed.isAccessible = true
                    dismissed[obj] = false
                    val shownByMe = c.getDeclaredField("mShownByMe")
                    shownByMe.isAccessible = true
                    shownByMe[obj] = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                manager.beginTransaction().add(this, tag)
                manager.beginTransaction().commitAllowingStateLoss()
            } catch (e: Exception) {
                e.printStackTrace()
                super.show(manager, tag)
            }
        }
    }

    //===============================================

    protected open lateinit var viewModel: VM

    /**
     * 创建 ViewModel
     */
    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(this, createCustomViewModelFactory()).get(tClass) as VM
        }
    }
    protected open fun createCustomViewModelFactory() : ViewModelProvider.Factory = ViewModelProvider.NewInstanceFactory()

    /**注册UI事件*/
    private fun registerDefUIChange() {
        viewModel.defUI.showDialog.observe(viewLifecycleOwner, Observer {
            showLoading()
        })
        viewModel.defUI.dismissDialog.observe(viewLifecycleOwner, Observer {
            dismissLoading()
        })
        viewModel.defUI.toastEvent.observe(viewLifecycleOwner, Observer {
            ToastUtils.showShort(it)
        })
        viewModel.defUI.msgEvent.observe(viewLifecycleOwner, Observer {
            handleEvent(it)
        })
    }

    private var loadingDialog: MaterialDialog? = null
    /** 打开等待框*/
    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = context?.let {
                MaterialDialog(it)
                    .cancelable(false)
                    .cornerRadius(8f)
                    .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
                    .lifecycleOwner(this)
                    .maxWidth(R.dimen.dialog_width)
            }
        }
        loadingDialog?.show()
    }

    /** 关闭等待框*/
    private fun dismissLoading() {
        loadingDialog?.run { if (isShowing) dismiss() }
    }


    open fun handleEvent(msg: Message) {}
}
