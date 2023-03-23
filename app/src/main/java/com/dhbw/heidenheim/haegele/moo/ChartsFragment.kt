package com.dhbw.heidenheim.haegele.moo

import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentChartsBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChartsBinding.inflate(inflater, container, false)

        val chart: LineChart = binding.chart

//        Typeface.createFromAsset(context?.assets, "font/arial_rounded_bold.ttf")
        val mTf: Typeface? = ResourcesCompat.getFont(requireContext(), R.font.arial_rounded_bold)

        val lineData: LineData = getData(14, 3f)
        lineData.setValueTypeface(mTf)
        lineData.setValueTextColor(R.color.dark_brown)

        setupChart(chart, lineData, R.color.transparent);
        chart.legend.typeface = mTf

        return binding.root
    }

    private fun setupChart(chart: LineChart, data: LineData, color: Int) {
        (data.getDataSetByIndex(0) as LineDataSet).circleHoleColor = R.color.brown

        // no description text
        chart.description.isEnabled = false

        //
        // enable / disable grid background
        chart.setDrawGridBackground(false)

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setBackgroundColor(Color.WHITE)

        // set custom chart offsets (automatic offset calculation is hereby disabled)
        chart.setViewPortOffsets(10f, 0f, 10f, 0f)

        chart.axisLeft.isEnabled = true
//        chart.axisLeft.spaceTop = 40f
//        chart.axisLeft.spaceBottom = 40f
        chart.axisRight.isEnabled = false
        chart.xAxis.isEnabled = true
//        chart.xAxis.enableGridDashedLine(10f, 10f, 0f)

//        chart.axisLeft.enableGridDashedLine(10f, 10f, 0f)
        chart.axisLeft.axisMaximum = 3f
        chart.axisLeft.axisMinimum = 1f

        // add data
        chart.data = data



        // get the legend (only possible after setting data)
        val l = chart.legend
        l.isEnabled = false
        l.textSize = 9f

        // animate calls invalidate()...
        chart.animateX(2500)
    }

    private fun getData(count: Int, range: Float): LineData {
        val values: ArrayList<Entry> = ArrayList()
        for (i in 0 until count) {
            val value = (Math.random() * range).toFloat() + 3
            values.add(Entry(i.toFloat(), value))
        }

        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")
        set1.fillAlpha = 110;
        set1.lineWidth = 1.75f
        set1.circleRadius = 5f
        set1.circleHoleRadius = 2.5f
        set1.colors = ColorTemplate.createColors(intArrayOf(R.color.brown))
//        set1.setCircleColor(R.color.brown)
//        set1.highLightColor = R.color.brown
        set1.setDrawValues(false)

        // create a data object with the data sets
        return LineData(set1)
    }

}