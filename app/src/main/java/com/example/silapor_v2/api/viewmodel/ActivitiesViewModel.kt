package com.example.silapor_v2.api.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.silapor_v2.api.repository.ActivitiesRepository
import com.example.silapor_v2.models.ActivitiesAdpModel
import com.example.silapor_v2.models.ActivitiesModel
import com.example.silapor_v2.models.ChartModel

class ActivitiesViewModel : ViewModel() {
    private val activitiesRepository = ActivitiesRepository()

    fun create(data: ActivitiesModel, uri: Uri?) : LiveData<Boolean> {
        return activitiesRepository.create(data, uri)
    }

    fun getAll() : LiveData<List<ActivitiesAdpModel>> {
        return activitiesRepository.getAll()
    }

    fun getByMhs(mhs: String) : LiveData<List<ActivitiesAdpModel>> {
        return activitiesRepository.getByMhs(mhs)
    }

    fun getChart() : LiveData<List<ChartModel>> {
        return activitiesRepository.getChart()
    }
}