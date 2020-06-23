package jp.dhc.supplement.core.presentation.base



import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


open class BaseViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {

}