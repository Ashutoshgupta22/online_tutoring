package com.aspark.clapingoassignment.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aspark.clapingoassignment.repository.Repository
import com.aspark.clapingoassignment.model.Teacher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private var _teacherData = MutableLiveData<Teacher>()
    val teacherData: LiveData<Teacher> = _teacherData


    fun getTeacherData() {

        val repo = Repository()

        viewModelScope.launch(Dispatchers.Default) {

            repo.getTeacherData {

                _teacherData.postValue(it)
            }
        }

    }

}