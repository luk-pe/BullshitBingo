package com.example.lukaspeter.bullshitbingo.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.adapters.GameGridViewAdapter;
import com.example.lukaspeter.bullshitbingo.helpers.SimpleAlertDialog;
import com.example.lukaspeter.bullshitbingo.helpers.TempItem;
import com.example.lukaspeter.bullshitbingo.models.Game;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.RemoteTemplate;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.GameViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.ItemViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RemoteTemplateDetailActivity extends AppCompatActivity implements GameGridViewAdapter.OnClickGridViewItemListener {

    // UI
    private TextView txtViewCreator;
    private TextView txtViewDescription;
    private Button btnCopy;

    private List<TempItem> tempItems = new ArrayList<>();
    private RemoteTemplate mTemplate;
    private TemplateViewModel mTemplateViewModel;
    private ItemViewModel mItemViewModel;
    private GameViewModel mGameViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_template_detail);

        // Set ViewModels
        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        mGameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        // Load Template ID from Intent
        Intent mIntent = getIntent();
        mTemplate = (RemoteTemplate) mIntent.getSerializableExtra("template");

        setTitle(mTemplate.getName());

        initTextViews();
        initPlayButton();
        initGridViewAdapter();
    }

    private void initGridViewAdapter() {
        final GridView gridView = findViewById(R.id.remote_template_detail_grid_view);
        final GameGridViewAdapter adapter = new GameGridViewAdapter(tempItems, this, this);
        gridView.setAdapter(adapter);

        ArrayList<String> templateItems = mTemplate.getItems();
        ArrayList<TempItem> tempItems = new ArrayList<>();
        for (int i=0;i<templateItems.size();i++) {
            tempItems.add(new TempItem(i+1, templateItems.get(i),false));
        }
        adapter.setItems(tempItems);
    }

    private void initPlayButton() {
        btnCopy = this.findViewById(R.id.remote_template_detail_button_create_copy);
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnCopyTemplateClick();
            }
        });
    }

    private void initTextViews() {
        txtViewCreator = this.findViewById(R.id.remote_template_detail_text_creator);
        txtViewDescription = this.findViewById(R.id.remote_template_detail_text_description);

        txtViewCreator.setText(getResources().getString(R.string.created_by) + " " + mTemplate.getCreator());
        txtViewDescription.setText(mTemplate.getDescription());
    }

    private void onBtnCopyTemplateClick() {

        // Save local copy of template and items on device
        Template template = mTemplateViewModel.getTemplateByRemoteId(mTemplate.getId());
        if(template == null){
            template = new Template(mTemplate.getName(), mTemplate.getCreator(), false, mTemplate.getCreated(), mTemplate.getDescription(), mTemplate.getId());
            long tid = mTemplateViewModel.insertTemplate(template);
            // create items
            for (int i = 0; i < mTemplate.getItems().size(); i++) {
                Item item = new Item(i + 1, mTemplate.getItems().get(i), (int) tid);
                mItemViewModel.insertItem(item);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.success);
        builder.setMessage(R.string.copy_created);
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent(RemoteTemplateDetailActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
        builder.show();
    }

    @Override
    public void onClickGridViewItem(TempItem item) {
        // Do nothing in DetailView
    }
}
