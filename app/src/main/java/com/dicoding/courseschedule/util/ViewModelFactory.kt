package com.dicoding.courseschedule.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.add.AddCourseViewModel
import com.dicoding.courseschedule.ui.home.HomeViewModel

class ViewModelFactory private constructor(private val repo: DataRepository) :
    ViewModelProvider.Factory{
    companion object {
        @Volatile
        private var inst: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            inst ?: synchronized(this) {
                inst ?: ViewModelFactory(
                    DataRepository.getInstance(context)!!
                )
            }
    }
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repo) as T
            }

            modelClass.isAssignableFrom(AddCourseViewModel::class.java) -> {
                AddCourseViewModel(repo) as T
            }

            else -> throw Throwable("Unknown ViewModel class ")
        }
}