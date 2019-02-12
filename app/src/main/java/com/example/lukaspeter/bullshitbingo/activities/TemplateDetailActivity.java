package com.example.lukaspeter.bullshitbingo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.adapters.GameGridViewAdapter;
import com.example.lukaspeter.bullshitbingo.helpers.TempItem;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.ItemViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.ArrayList;
import java.util.List;

public class TemplateDetailActivity extends AppCompatActivity implements GameGridViewAdapter.OnClickGridViewItemListener {

    private Button btnStart;
    private ItemViewModel mItemViewModel;
    private List<TempItem> tempItems = new ArrayList<>();
    private Template mTemplate;
    private TemplateViewModel mTemplateViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);

        // Load Template ID from Intent
        Intent mIntent = getIntent();
        final int templateId = mIntent.getIntExtra("template_id", 0); //TODO: Bug template ID wird in intend korrekt überegben aber offensichtlich hier nicht korrekt ausgelesen: templateId = 0

        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mTemplate = mTemplateViewModel.getTemplateById(templateId);

        // Close activity if Template not found
        if (mTemplate == null) {
            TemplateDetailActivity.this.finish();
        }

        setTitle(mTemplate.getName());

        initStartButton();
        initGridViewAdapter();
    }

    private void initGridViewAdapter() {
        final GridView gridView = findViewById(R.id.template_detail_grid_view);
        final GameGridViewAdapter adapter = new GameGridViewAdapter(tempItems, this, this);
        gridView.setAdapter(adapter);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemViewModel.getTemplateItems(mTemplate.getId()).observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                for (Item i : items) {
                    tempItems.add(new TempItem(i.getId(), i.getName(), false));
                }
                adapter.setItems(tempItems);
            }
        });
    }

    private void initStartButton() {
        btnStart = this.findViewById(R.id.template_detail_button_bingo);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnStartClick();
            }
        });

    }

    private void onBtnStartClick() {
        // TODO Create game and Push game id to GameActivity
        Intent mIntent = new Intent(this, GameActivity.class);
        mIntent.putExtra("game_id", mTemplate.getId()); // TODO change to id of game
        startActivity(mIntent);
    }

    @Override
    public void onClickGridViewItem(TempItem item) {
        // Do nothing in DetailView
    }
}
