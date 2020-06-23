package jp.dhc.supplement.core.presentation.base


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import androidx.lifecycle.ViewModelProvider

import androidx.annotation.LayoutRes



abstract class BaseBindingFragment<DB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int? = null,
    @LayoutRes private val viewModelVariableId: Int? = null,
    private val modelClass: Class<VM>?
) : BaseFragment() {

    protected lateinit var viewModel : VM

    abstract fun onFragmentCreatedView(dataBinder: DB)


    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinder = initial(inflater, container)
        dataBinder?.let {
          return it.root
        }
        return null
    }

    private fun initial(inflater: LayoutInflater, container: ViewGroup?) : DB? {
        layoutResId?.let { layoutId ->
            val dataBinder : DB = DataBindingUtil.inflate(inflater, layoutId, container, false)
            viewModelVariableId?.let { variableId ->
                createViewModel()
                dataBinder.setVariable(variableId, viewModel)
            }
            dataBinder.lifecycleOwner = this
            lifecycle.addObserver(viewModel)
            onFragmentCreatedView(dataBinder)
            return dataBinder
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val modelClass = modelClass ?: BaseViewModel::class.java
        viewModel = ViewModelProvider(this).get(modelClass) as VM
    }

}

