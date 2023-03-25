package com.dhbw.heidenheim.haegele.moo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.data.domain.Item
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentHistoryBinding
import com.yuyakaido.android.cardstackview.*

class HistoryFragment : Fragment(), CardStackListener {

    private var fragmentContext: Context? = null

    private var _binding: FragmentHistoryBinding? = null

    private val cardStackView by lazy { _binding!!.cardStackView}
    private val manager by lazy { CardStackLayoutManager(fragmentContext, this) }
    private val adapter by lazy { CardStackAdapter(createCards()) }

    private val syncRealmController = SyncRealmController()
    private val repository = syncRealmController.getRepo()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater)
        setupCardStackView()
        return _binding!!.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("CardStackView", "onCardDragging: d = ${direction.name}, r = $ratio")
    }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_date)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.item_date)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getCards()
        val new = createCards()
        val callback = CardDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setCards(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun createCards(): List<Item> {
        val cards: ArrayList<Item>
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val depth = sp.getInt("history_depth", 4)

        cards = repository.getCardList(depth)!!

        return cards
    }
}