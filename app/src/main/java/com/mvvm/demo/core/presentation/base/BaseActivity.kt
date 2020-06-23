package jp.dhc.supplement.core.presentation.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

open class BaseActivity : AppCompatActivity()  {

    fun addFragment(fragmentId: Int, fragment: Fragment){
        supportFragmentManager.commit {
            add(fragmentId,fragment)
        }
    }

    fun replaceTopFragment(fragmentId: Int, fragment: Fragment){
        supportFragmentManager.commit {
            replace(fragmentId, fragment)
            addToBackStack(null)
        }
    }

}


inline fun FragmentManager.commit(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}
