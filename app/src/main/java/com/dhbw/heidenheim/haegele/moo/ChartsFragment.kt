package com.dhbw.heidenheim.haegele.moo

import android.annotation.SuppressLint
import android.content.res.Resources
import android.content.res.Resources.Theme
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.data.domain.Item
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentChartsBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.launch


class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    @SuppressLint("StringFormatInvalid")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChartsBinding.inflate(inflater, container, false)

        // chart
        var lineData: LineData

        val syncRealmController = SyncRealmController()
        val repository = syncRealmController.getRepo()

        lifecycleScope.launch {
            var cardList = repository.getCardList(7)
            if (cardList != null) {
                if (!cardList.isEmpty()) {
                    cardList.reverse()

                    lineData = getData(cardList)
                    val chart: LineChart = binding.chart

                    setUpLineChart(chart)
                    chart.data = lineData

                    // most common mood & number of happy-days
                    val topMood: Int
                    val moodColor: Int
                    val moodCounts: MutableMap<String, Int> = HashMap()
                    cardList.forEach { item ->
                        moodCounts[item.mood] = moodCounts.getOrDefault(item.mood, 0) + 1
                    }
                    val max = moodCounts.maxBy { it.value }
                    when (max.key) {
                        "happy" -> {
                            topMood = R.drawable.ic_happy
                            moodColor = R.color.ic_green
                        }
                        "neutral" -> {
                            topMood = R.drawable.ic_neutral
                            moodColor = R.color.ic_yellow
                        }
                        "unhappy" -> {
                            topMood = R.drawable.ic_unhappy
                            moodColor = R.color.ic_red
                        }
                        else -> {
                            topMood = R.drawable.ic_neutral
                            moodColor = R.color.ic_yellow
                        }
                    }

                    binding.chartsDepth.text = getString(R.string.duration, cardList.size)
                    binding.chartsTopMood.setImageResource(topMood)
                    binding.chartsTopMood.drawable.setTint(MooApp.res.getColor(moodColor, null))
                    binding.chartsNumHappyDays.text = moodCounts["happy"].toString()

                } else {
                    binding.chartsDepth.text = getString(R.string.statistics_no_data, cardList.size)
                    binding.chartsTopMood.setImageResource(R.drawable.ic_neutral)
                    binding.chartsTopMood.drawable.setTint(MooApp.res.getColor(R.color.grey, null))
                    binding.chartsNumHappyDays.text = "-"
                }
            }
        }

        return binding.root
    }

    private fun setUpLineChart(chart: LineChart) {
        with(chart) {
//            val typedVal = TypedValue()
//            val theme = context.theme
//            theme.resolveAttribute(R.attr.secondaryColor, typedVal, true)
//            theme.resolveAttribute(R.attr.primaryVariantColor, typedVal, true)
//            @ColorInt val secondaryColor = typedVal.data
//            @ColorInt val primaryVariantColor = typedVal.data

            val colorDark = MaterialColors.getColor(context, com.google.android.material.R.attr.colorPrimary, Color.BLACK)
            val colorLight = MaterialColors.getColor(context, com.google.android.material.R.attr.colorSecondary, Color.GRAY)
            val typeface = MooApp.res.getFont(R.font.arial_rounded_bold)

            animateX(1200, Easing.EaseInSine)
            description.isEnabled = false

            val indexFormatter = IndexAxisValueFormatter()
            indexFormatter.values = arrayOf("1", "2", "3", "4", "5", "6", "7")

            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1F
            xAxis.textSize = 15f
            xAxis.textColor = colorLight
            xAxis.typeface = typeface
            xAxis.valueFormatter = indexFormatter

            axisRight.isEnabled = false
            extraRightOffset = 30f

            axisLeft.granularity = 1F
            axisLeft.textSize = 15f
            axisLeft.textColor = colorLight
            axisLeft.typeface = typeface

            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.textSize = 13F
            legend.form = Legend.LegendForm.LINE
            legend.typeface = typeface
            legend.textColor = colorDark

            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
        }
    }

    private fun getData(list : List<Item>): LineData {
        val values: ArrayList<Entry> = ArrayList()
        list.forEachIndexed { i, item ->
            val value: Float = when (item.mood) {
                "happy" -> 3f
                "neutral" -> 2f
                "unhappy" -> 1f
                else -> 2f
            }
            values.add(Entry(i.toFloat(), value))
        }

        val primaryColor = MaterialColors.getColor(requireContext(), com.google.android.material.R.attr.colorPrimary, Color.GRAY)
        val darkColor = MaterialColors.getColor(requireContext(), com.google.android.material.R.attr.colorPrimaryVariant, Color.BLACK)

        val dataset = LineDataSet(values, "Moods (3: Happy, 2: Neutral, 1: Unhappy)")
        dataset.lineWidth = 3f
        dataset.mode = LineDataSet.Mode.CUBIC_BEZIER
        dataset.color = primaryColor
        dataset.valueTextColor = darkColor
        dataset.enableDashedLine(20F, 10F, 0F)
        dataset.circleColors = mutableListOf(primaryColor)
        dataset.setDrawValues(false)

        return LineData(dataset)
    }

}