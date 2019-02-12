package com.example.lukaspeter.bullshitbingo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.adapters.GameGridViewAdapter;
import com.example.lukaspeter.bullshitbingo.helpers.TempItem;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.ItemViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity implements GameGridViewAdapter.OnClickGridViewItemListener {

    private Button btnCallBingo;
    private ItemViewModel mItemViewModel;
    private List<TempItem> tempItems = new ArrayList<>();
    private Template mTemplate;
    private TemplateViewModel mTemplateViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Load Template ID from Intent
        Intent mIntent = getIntent();
        final int templateId = mIntent.getIntExtra("game_id", 0);

        // TODO Change here from Template to Game View Model!
        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mTemplate = mTemplateViewModel.getTemplateById(templateId);

        // Close activity if Template not found
        if (mTemplate == null) {
            GameActivity.this.finish();
        }

        // TODO Get Game name
        setTitle(mTemplate.getName());

        initBingoButton();
        initGridViewAdapter();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // TODO Save Game state here in DB
        Log.d("blumm", "Game paused");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO Load Game state here from DB
    }

    @Override
    public void onClickGridViewItem(TempItem item) {

        item.setChecked(!item.isChecked());

        boolean isFinished = this.isBingo();
        setBtnCallBingoVisibility(isFinished);
    }

    private void initGridViewAdapter() {
        final GridView gridView = (GridView) findViewById(R.id.game_grid_view);
        final GameGridViewAdapter adapter = new GameGridViewAdapter(tempItems, this, this);
        gridView.setAdapter(adapter);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemViewModel.getTemplateItems(mTemplate.getId()).observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                // TODO wenn neues spiel -> shuffle ; sonst -> positions lesen
                //for (Item i : items) {
                //    tempItems.add(new TempItem(i.getId(),i.getName(),false));
                //}
                //Collections.shuffle(tempItems);

                // TODO Game nicht neu -> Item positions als Text auslesen
                String positions = "2;1;3;4;5;6;7;8;9;10;11;12;13;14;15;16"; // Example
                String[] arrayOfPos = positions.split(";");
                for (int i = 0; i < arrayOfPos.length; i++) {
                    for (Item item : items) {
                        if (item.getId() == Integer.valueOf(arrayOfPos[i])) {
                            tempItems.add(new TempItem(item.getId(), item.getName(), false));
                        }
                    }
                }
                adapter.setItems(tempItems);
            }
        });
    }

    private void initBingoButton() {
        btnCallBingo = this.findViewById(R.id.game_button_bingo);
        btnCallBingo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBingoClick();
            }
        });
        setBtnCallBingoVisibility(false);

    }

    private void setBtnCallBingoVisibility(boolean visible) {

        btnCallBingo.clearAnimation();

        if (visible) {
            btnCallBingo.setVisibility(View.VISIBLE);
            // Blink animation
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(200); //You can manage the blinking time with this parameter
            anim.setStartOffset(200);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            btnCallBingo.startAnimation(anim);
        } else {
            btnCallBingo.setVisibility(View.GONE);
        }
    }

    private boolean isBingo() {

        // Vertical
        for (int i = 0; i < 4; i++) {
            if (tempItems.get(i).isChecked() && tempItems.get(i + 4).isChecked() && tempItems.get(i + 8).isChecked() && tempItems.get(i + 12).isChecked())
                return true;
        }
        // Horizontal
        for (int i = 0; i < 13; i += 4) {
            if (tempItems.get(i).isChecked() && tempItems.get(i + 1).isChecked() && tempItems.get(i + 2).isChecked() && tempItems.get(i + 3).isChecked())
                return true;
        }
        // Cross
        if (tempItems.get(0).isChecked() && tempItems.get(5).isChecked() && tempItems.get(10).isChecked() && tempItems.get(15).isChecked())
            return true;
        if (tempItems.get(3).isChecked() && tempItems.get(6).isChecked() && tempItems.get(9).isChecked() && tempItems.get(12).isChecked())
            return true;

        return false;
    }

    private void onCallBingoClick() {
        // TODO Save game in DB and go back to Menu
        Toast.makeText(this, "CONGRATULATIONS!", Toast.LENGTH_SHORT).show();
    }
}
