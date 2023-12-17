package com.example.silapor_v2.ui_mhs

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.silapor_v2.api.viewmodel.ActivitiesViewModel
import com.example.silapor_v2.databinding.FragmentDashboard3Binding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
class Dashboard : Fragment() {
    private var _binding : FragmentDashboard3Binding? = null
    private val binding get() = _binding!!
    private lateinit var activitiesViewModel: ActivitiesViewModel
    lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDashboard3Binding.inflate(inflater, container, false)
        v = binding.root
        activitiesViewModel = ViewModelProvider(this).get(ActivitiesViewModel::class.java)

        activitiesViewModel.getChart().observe(requireActivity(), Observer { data ->
            val barEntries = ArrayList<BarEntry>()
            for (i in data.indices) {
                barEntries.add(BarEntry(i.toFloat(), data[i].count.toFloat(), data[i].name))
            }
            setupBarChart(barEntries)
        })

        binding.btnYoutube.setOnClickListener {
            startActivity(android.content.Intent(context, WebView::class.java))
        }
        binding.btnMaps.setOnClickListener {
            startActivity(android.content.Intent(context, Maps::class.java))
        }

        return v
    }

    private fun setupBarChart(barEntries: List<BarEntry>) {
        val dataSet = BarDataSet(barEntries, "Data Set")
        dataSet.setColors(*ColorTemplate.PASTEL_COLORS)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(dataSet)

        val data = BarData(dataSets)

        binding.barChart.data = data
        binding.barChart.description.isEnabled = false
        binding.barChart.xAxis.setDrawGridLines(false)
        binding.barChart.xAxis.setDrawAxisLine(true)
        binding.barChart.xAxis.setDrawLabels(true)
        binding.barChart.xAxis.labelCount = barEntries.size

        val labels = ArrayList<String>()
        for (entry in barEntries) {
            labels.add(entry.data as String)
        }
        val xAxis = binding.barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f

        binding.barChart.axisRight.isEnabled = false
        binding.barChart.legend.isEnabled = false
        binding.barChart.animateY(1000, Easing.EaseInOutCubic)

        binding.barChart.invalidate()
    }
}