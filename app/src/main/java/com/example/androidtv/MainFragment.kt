package com.example.androidtv

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import com.example.androidtv.cards.presenters.CardPresenterSelector
import com.example.androidtv.model.Card
import com.example.androidtv.model.CardRow
import com.example.androidtv.utils.Utils
import com.google.gson.Gson

/**
 * Loads a grid of cards with menu to browse.
 */
class MainFragment : BrowseSupportFragment() {

    private var mRowsAdapter: ArrayObjectAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUIElements()
        setupRowAdapter()
        setupEventListeners()
    }

    private fun setupRowAdapter() {
        mRowsAdapter = ArrayObjectAdapter(ListRowPresenter())
        createRows()
        adapter = mRowsAdapter
    }

    private fun createRows() {
        val json = Utils
            .inputStreamToString(resources.openRawResource(R.raw.launcher_cards))
        val rows = Gson().fromJson(
            json,
            Array<CardRow>::class.java
        )
        for (row in rows) {
            mRowsAdapter!!.add(createCardRow(row))
        }
    }

    private fun createCardRow(cardRow: CardRow): ListRow? {
        val presenterSelector: PresenterSelector = CardPresenterSelector(activity)
        val listRowAdapter = ArrayObjectAdapter(presenterSelector)
        for (card in cardRow.cards) {
            listRowAdapter.add(card)
        }
        return ListRow(listRowAdapter)
    }

    private fun setupUIElements() {
        title = getString(R.string.browse_title)
        badgeDrawable = resources.getDrawable(R.drawable.title_android_tv, null)
        headersState = HEADERS_DISABLED
        isHeadersTransitionOnBackEnabled = false
        brandColor = resources.getColor(R.color.fastlane_background)
    }

    private fun setupEventListeners() {
        onItemViewClickedListener = ItemViewClickedListener()
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private class ItemViewClickedListener : OnItemViewClickedListener {
        override fun onItemClicked(
            itemViewHolder: Presenter.ViewHolder, item: Any,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
            var intent: Intent? = null
            val card = item as Card
            val id = card.id
//            when (id) {
//                0 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        CardExampleActivity::class.java
//                    )
//                }
//                1 -> intent = Intent(
//                    getActivity().getBaseContext(),
//                    PageAndListRowActivity::class.java
//                )
//                2 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        GridExampleActivity::class.java
//                    )
//                }
//                3 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        VideoGridExampleActivity::class.java
//                    )
//                }
//                4 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        DetailViewExampleActivity::class.java
//                    )
//                }
//                5 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        DetailViewExampleWithVideoBackgroundActivity::class.java
//                    )
//                }
//                6 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        VideoExampleActivity::class.java
//                    )
//                }
//                7 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        VideoExampleWithExoPlayerActivity::class.java
//                    )
//                }
//                8 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        MusicExampleActivity::class.java
//                    )
//                }
//                9 -> {
//
//                    // Let's create a new Wizard for a given Movie. The movie can come from any sort
//                    // of data source. To simplify this example we decode it from a JSON source
//                    // which might be loaded from a server in a real world example.
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        WizardExampleActivity::class.java
//                    )
//
//                    // Prepare extras which contains the Movie and will be passed to the Activity
//                    // which is started through the Intent/.
//                    val extras = Bundle()
//                    val json = Utils.inputStreamToString(
//                        getResources().openRawResource(R.raw.wizard_example)
//                    )
//                    val movie = Gson().fromJson(json, Movie::class.java)
//                    extras.putSerializable("movie", movie)
//                    intent!!.putExtras(extras)
//                }
//                10 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        SettingsExampleActivity::class.java
//                    )
//                    startActivity(intent)
//                    return
//                }
//                11 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        DialogExampleActivity::class.java
//                    )
//                }
//                12 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        DynamicVideoRowsActivity::class.java
//                    )
//                    startActivity(intent)
//                    return
//                }
//                13 -> {
//                    intent = Intent(
//                        getActivity().getBaseContext(),
//                        LiveDataRowsActivity::class.java
//                    )
//                    startActivity(intent)
//                    return
//                }
//                else -> {
//                }
//            }
//            if (intent != null) {
//                val bundle =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
//                        .toBundle()
//                startActivity(intent, bundle)
//            }
        }
    }

    private class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder, item: Any,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {
        }
    }
}