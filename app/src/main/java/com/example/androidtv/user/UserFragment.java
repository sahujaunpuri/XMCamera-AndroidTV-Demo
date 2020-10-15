/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.androidtv.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.leanback.app.BrowseSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.example.androidtv.MultiScreenActivity;
import com.example.androidtv.R;
import com.example.androidtv.cards.presenters.CardPresenterSelector;
import com.example.androidtv.model.Card;
import com.example.androidtv.model.CardRow;
import com.example.androidtv.utils.Utils;
import com.google.gson.Gson;
import com.lib.funsdk.support.FunSupport;

//import com.example.androidtv.cards.CardExampleActivity;
//import com.example.androidtv.details.DetailViewExampleActivity;
//import com.example.androidtv.details.DetailViewExampleWithVideoBackgroundActivity;
//import com.example.androidtv.dialog.DialogExampleActivity;
//import com.example.androidtv.grid.GridExampleActivity;
//import com.example.androidtv.grid.VideoGridExampleActivity;
//import com.example.androidtv.media.MusicExampleActivity;
//import com.example.androidtv.media.VideoExampleActivity;
//import com.example.androidtv.media.VideoExampleWithExoPlayerActivity;
//import com.example.androidtv.page.PageAndListRowActivity;
//import com.example.androidtv.room.controller.overview.LiveDataRowsActivity;
//import com.example.androidtv.rows.DynamicVideoRowsActivity;
//import com.example.androidtv.settings.SettingsExampleActivity;
//import com.example.androidtv.wizard.WizardExampleActivity;


public class UserFragment extends BrowseSupportFragment {

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!FunSupport.getInstance().hasLogin()
        ) {
//            startActivity(Intent(this, LoginActivity::class.java))
            if (!FunSupport.getInstance().login("quangvupp321", "haphungquangvu")) {
                Toast.makeText(getActivity(), R.string.guide_message_error_call, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Login successfully", Toast.LENGTH_SHORT).show();
            }
//            startActivity(Intent(this, MultiScreenActivity2::class.java))
        }

        setupUIElements();
        setupRowAdapter();
        setupEventListeners();
    }

    private void setupRowAdapter() {
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        createRows();
        setAdapter(mRowsAdapter);
    }

    private void createRows() {
        String json = Utils
                .inputStreamToString(getResources().openRawResource(R.raw.user_cards));
        CardRow[] rows = new Gson().fromJson(json, CardRow[].class);
        for (CardRow row : rows) {
            mRowsAdapter.add(createCardRow(row));
        }
    }

    private ListRow createCardRow(CardRow cardRow) {
        PresenterSelector presenterSelector = new CardPresenterSelector(getActivity());
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(presenterSelector);
        for (Card card : cardRow.getCards()) {
            listRowAdapter.add(card);
        }
        return new ListRow(listRowAdapter);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.browse_title));
        setBadgeDrawable(getResources().getDrawable(R.drawable.title_android_tv, null));
        setHeadersState(HEADERS_DISABLED);
        setHeadersTransitionOnBackEnabled(false);
        setBrandColor(getResources().getColor(R.color.fastlane_background));
    }

    private void setupEventListeners() {
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {

        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                                  RowPresenter.ViewHolder rowViewHolder, Row row) {
            Intent intent = null;
            Card card = (Card) item;
            int id = card.getId();
            switch (id) {
                case 0: {
//                    intent = new Intent(getActivity().getBaseContext(),
//                            UserActivity.class);
//                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity())
//                            .toBundle();
//                    startActivity(intent, bundle);
                    break;
                }
                case 1: {
                    break;
                }
                case 2: {
                    break;
                }
                case 3: {
                    break;
                }
                case 4: {
                    break;
                }
                default:
                    break;
            }
        }
    }

    private final class ItemViewSelectedListener implements OnItemViewSelectedListener {

        @Override
        public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                                   RowPresenter.ViewHolder rowViewHolder, Row row) {
        }
    }
}
