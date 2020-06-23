package jp.dhc.supplement.core.presentation.base


import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider


import java.lang.reflect.ParameterizedType


abstract class BaseBindingActivity<DB: ViewDataBinding, VM: BaseViewModel>
    (@LayoutRes private val layoutResId: Int? = null,
     @LayoutRes private val viewModelVariableId: Int? = null
): BaseActivity() {

    protected lateinit var viewModel: VM
    abstract fun onActivityCreated(dataBinder: DB)

    final override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initial()
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initial()
    }

    private fun initial() {
        layoutResId?.let { layoutId ->
            val dataBinder = DataBindingUtil.setContentView<DB>(this, layoutId)

            viewModelVariableId?.let { variableId ->
                createViewModel()
                dataBinder.setVariable(variableId, viewModel)
            }
            dataBinder.lifecycleOwner = this
            lifecycle.addObserver(viewModel)
            onActivityCreated(dataBinder)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[1]
            val modelClass = tp as? Class<VM> ?: BaseViewModel::class.java
            viewModel = ViewModelProvider(this).get(modelClass) as VM
        }
    }

}

