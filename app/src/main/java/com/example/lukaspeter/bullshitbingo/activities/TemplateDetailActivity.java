package com.example.lukaspeter.bullshitbingo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.adapters.GameGridViewAdapter;
import com.example.lukaspeter.bullshitbingo.helpers.SimpleAlertDialog;
import com.example.lukaspeter.bullshitbingo.helpers.TempItem;
import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.GameViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.ItemViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TemplateDetailActivity extends AppCompatActivity implements GameGridViewAdapter.OnClickGridViewItemListener {

    private TextView txtViewCreator;
    private TextView txtViewDescription;
    private Button btnEditDescription;
    private Button btnStart;
    private Button btnMakePublic;
    private ItemViewModel mItemViewModel;
    //private List<TempItem> tempItems = new ArrayList<>();
    private Template mTemplate;
    private List<Item> mItems = new ArrayList<>();
    private TemplateViewModel mTemplateViewModel;
    private GameViewModel mGameViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_detail);

        // Load Template ID from Intent
        Intent mIntent = getIntent();
        final int templateId = mIntent.getIntExtra("template_id", 0);

        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mTemplate = mTemplateViewModel.getTemplateById(templateId);

        // Close activity if Template not found
        if (mTemplate == null) {
            TemplateDetailActivity.this.finish();
        }

        setTitle(mTemplate.getName());

        initTextViews();
        initButtons();
        initGridViewAdapter();
    }

    private void initGridViewAdapter() {
        final GridView gridView = findViewById(R.id.template_detail_grid_view);
        final GameGridViewAdapter adapter = new GameGridViewAdapter(new ArrayList<TempItem>(), this, this);
        gridView.setAdapter(adapter);

        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mItemViewModel.getTemplateItems(mTemplate.getId()).observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                mItems = items;
                List<TempItem> tempItems = new ArrayList<>();
                for (Item i : items) {
                    tempItems.add(new TempItem(i.getId(), i.getName(), false));
                }
                adapter.setItems(tempItems);
            }
        });
    }

    private void initTextViews() {
        txtViewCreator = this.findViewById(R.id.template_detail_text_creator);
        txtViewDescription = this.findViewById(R.id.template_detail_text_description);

        txtViewCreator.setText(getResources().getString(R.string.created_by) + " " + mTemplate.getCreator());
        txtViewDescription.setText(mTemplate.getDescription());
    }

    private void initButtons() {
        btnEditDescription = this.findViewById(R.id.template_detail_button_edit_description);
        btnEditDescription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDescriptionChangeDialog(mTemplate.getDescription());
            }
        });

        btnStart = this.findViewById(R.id.template_detail_button_play);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnStartClick();
            }
        });

        btnMakePublic = this.findViewById(R.id.template_detail_button_make_public);
        btnMakePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnMakePublicClick();
            }
        });
        if (!mTemplate.isPriv()) btnMakePublic.setVisibility(View.GONE);
    }


    private void onBtnStartClick() {
        // Create game and push game id to GameActivity
        int templateId = mTemplate.getId();
        Game game = new Game(new Date(), templateId);
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        long gameId = mGameViewModel.insertGame(game);

        // Close activity if gameId = 0 -> game wasn't created -> exception in dataRepository
        if (gameId == 0) {
            TemplateDetailActivity.this.finish();
        }

        Intent mIntent = new Intent(this, GameActivity.class);
        mIntent.putExtra("game_id", (int) gameId);
        startActivity(mIntent);
    }

    private void onBtnMakePublicClick() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText creatorBox = new EditText(this);
        creatorBox.setHint("Creator");
        layout.addView(creatorBox); // Notice this is an add method

        final EditText descriptionBox = new EditText(this);
        descriptionBox.setHint("Description");
        layout.addView(descriptionBox); // Another add method

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bingo)
                .setMessage(R.string.bingo)
                .setView(layout)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onAlertOKBtnClick(creatorBox.getText().toString(),descriptionBox.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void onAlertOKBtnClick(String editTextCreator, String editTextDescription) {
        btnMakePublic.setEnabled(false);
        mTemplate.setCreator(editTextCreator);
        mTemplateViewModel.uploadTemplate(mTemplate,mItems,editTextDescription).observe(TemplateDetailActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean success) {
                if (success) {
                    new SimpleAlertDialog(TemplateDetailActivity.this,R.string.success, R.string.success_public).show();
                    btnMakePublic.setVisibility(View.GONE);
                } else {
                    new SimpleAlertDialog(TemplateDetailActivity.this,R.string.error, R.string.error_public).show();
                    btnMakePublic.setEnabled(true);
                }
            }
        });
    }

    private void showDescriptionChangeDialog(String description){
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(TemplateDetailActivity.this);
        View promptView = layoutInflater.inflate(R.layout.dialog_change_description, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(TemplateDetailActivity.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = promptView.findViewById(R.id.description_input);
        editText.setText(description);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateDescription(editText.getText().toString());
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    @Override
    public void onClickGridViewItem(TempItem item) {
        // Do nothing in DetailView
    }

    private void updateDescription(String description){
        txtViewDescription.setText(description);
        mTemplateViewModel.updateTemplateDescription(description, mTemplate.getId());
    }
}
