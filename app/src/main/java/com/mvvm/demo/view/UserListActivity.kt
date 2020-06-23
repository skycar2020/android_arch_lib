package com.mvvm.demo.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.mvvm.demo.BR
import com.mvvm.demo.R
import com.mvvm.demo.databinding.ActivityUserBinding
import com.mvvm.demo.core.http.ApiResponse
import com.mvvm.demo.core.presentation.*
import com.mvvm.demo.data.entity.User
import com.mvvm.demo.viewmodel.UserViewModel
import jp.dhc.supplement.core.presentation.base.BaseBindingActivity
import kotlinx.android.synthetic.main.activity_user.*


class UserListActivity : BaseBindingActivity<ActivityUserBinding,UserViewModel>(
    R.layout.activity_user,
    BR.userViewModel) {

    private val imageUrl = "https://miro.medium.com/max/1400/1*Yeuu42LPdQjt9zIjhkYnqQ.jpeg"
    private var mSimepleItemListAdapter: SimpleItemListAdapter<User>? = null
    private var mMultiTypeLayoutItemListAdapter: MultiTypeItemListAdapter<User>? = null

    override fun onActivityCreated(dataBinder: ActivityUserBinding){

        //UI
        val layoutManager = LinearLayoutManager(this)
        dataBinder.recyclerView.layoutManager = layoutManager
        dataBinder.recyclerView.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        //binding
        dataBinder.user = User("default", 1, "account name", imageUrl)

        //setup adapter
        var isSimpleLayout = false
        setupItemListAdapter(isSimpleLayout)

        //fetch data
        var needObserve = true
        fetchUsers(needObserve, isSimpleLayout)
    }

    private enum class LayoutType(var typeId : Int){
        One(1),
        Two(2),
    }

    private fun setupItemListAdapter(isSimpleLayout: Boolean){
        if(isSimpleLayout){
            //adapter
            mSimepleItemListAdapter = SimpleItemListAdapter(R.layout.recycler_view_item, BR.user)
            recycler_view.adapter = mSimepleItemListAdapter

        } else {
            //config
            val configList = listOf(
                CellLayoutConfig(LayoutType.One.typeId, R.layout.recycler_view_item, BR.user),
                CellLayoutConfig(LayoutType.Two.typeId,R.layout.recycler_view_item_other_ui, BR.otherUser)
            )

//            //adapter
//            mMultiTypeLayoutItemListAdapter = MultiTypeItemListAdapter<User>(configList){
//
//                when(it % 2){
//                    0 -> LayoutType.One.typeId
//                    else -> LayoutType.Two.typeId
//                }
//
//            }


            mMultiTypeLayoutItemListAdapter = MultiTypeItemListAdapter(configList, object : CellLayoutTypeDelegate {

                override fun getCellLayoutType(rowIndex: Int): Int {
                    return if (rowIndex % 2 == 0) LayoutType.One.typeId else LayoutType.Two.typeId
                }
//                override fun transformCellData(layoutType: Int, rowIndex: Int, entity: Any): Any {
//                    if(layoutType == 1){
//                        var user =  entity as User
//                        user.login = "ClassType1"
//                        return user
//                    } else {
//                        var user =  entity as User
//                        //user.login = "ClassType2"
//                        //return user
//                    }
//                    return entity
//                }

            })


            recycler_view.adapter = mMultiTypeLayoutItemListAdapter

        }
    }

    private fun fetchUsers(needObserve: Boolean, isSimpleLayout: Boolean) {

        if(needObserve){
            viewModel.getUsers(10).observe(this, Observer<List<User>> { itemList ->
                if (isSimpleLayout) {
                    mSimepleItemListAdapter!!.addDataList(itemList)
                } else {
                    mMultiTypeLayoutItemListAdapter!!.addDataList(itemList)
                }
            })
        } else {
            viewModel.fetchDataList(10)
        }

    }

    private fun fetchUsersResponseWrapper(needObserve: Boolean) {
        viewModel.getUsersResponseWrapper(10).observe(this, Observer<ApiResponse<List<User>>> { apiResponse ->
            mSimepleItemListAdapter!!.addDataList(apiResponse.data)
        })
    }

    private fun getTestDataUsers():MutableList<User>{
        val userList = mutableListOf<User>()
        for (i in 1..6){
            var  user= User("content", i, "bbb", imageUrl)
            userList.add(user)
        }
        return userList
    }


}
