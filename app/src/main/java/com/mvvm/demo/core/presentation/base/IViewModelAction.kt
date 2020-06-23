package jp.dhc.supplement.core.presentation.base


interface ILoadingAction {
    fun startLoading()
    fun startLoading(message: String)
    fun dismissLoading()
    fun showToast(message: String)
}

interface IDialogAction {

    fun showDialog(message: String)
}


interface IViewModelAction : ILoadingAction, IDialogAction{

}