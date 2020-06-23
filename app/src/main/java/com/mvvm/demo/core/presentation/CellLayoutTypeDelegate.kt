package com.mvvm.demo.core.presentation

interface CellLayoutTypeDelegate {

    fun getCellLayoutType(rowIndex: Int): Int
    fun transformCellData(layoutType: Int, rowIndex: Int, entity: Any): Any {
        return entity
    }
}