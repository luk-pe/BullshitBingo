package com.example.lukaspeter.bullshitbingo.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lukaspeter.bullshitbingo.R;
import com.example.lukaspeter.bullshitbingo.models.Item;
import com.example.lukaspeter.bullshitbingo.models.Template;
import com.example.lukaspeter.bullshitbingo.viewModels.ItemViewModel;
import com.example.lukaspeter.bullshitbingo.viewModels.TemplateViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class NewGameActivity extends AppCompatActivity {
    private EditText templateNameInput;
    private TemplateViewModel mTemplateViewModel;
    private ItemViewModel mItemViewModel;
    private Template template;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        templateNameInput = findViewById(R.id.templateName);
        Button createTemplateButton = findViewById(R.id.button_createTemplate);
        mTemplateViewModel = ViewModelProviders.of(this).get(TemplateViewModel.class);
        mItemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        createTemplateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int[] inputFieldsIds = getItemInputFieldsId();
                boolean emptyRequiredFields = false;
                //check if template name is filled
                String templateNameString = templateNameInput.getText().toString();
                if (templateNameString.trim().equals("")) {
                    emptyRequiredFields = true;
                } else {
                    //check if all items filled
                    for (int i = 0; i < inputFieldsIds.length; i++) {
                        EditText itemNameInput = findViewById(inputFieldsIds[i]);
                        if (itemNameInput.getText().toString().trim().equals("")) {
                            emptyRequiredFields = true;
                            break;
                        }
                    }
                }
                // empty fields
                if (emptyRequiredFields) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(NewGameActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(NewGameActivity.this);
                    }
                    builder.setTitle(R.string.emptyFields)
                            .setMessage(R.string.emptyInputFields)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
                // no empty fields
                else {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String username = user.getDisplayName();
                    if (username != null) {
                        if (username.equals("")) username = user.getEmail();
                    } else {
                        username = user.getEmail();
                    }
                    EditText templateDescriptionInput = findViewById(R.id.description);
                    String templateDescription = templateDescriptionInput.getText().toString();

                    template = new Template(templateNameString, username, true, new Date(), templateDescription, null);
                    long tid = mTemplateViewModel.insertTemplate(template);

                    // check if tid == 0 -> template wasn't created -> exception in dataRepository
                    if (tid == 0) {
                        AlertDialog.Builder builder;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            builder = new AlertDialog.Builder(NewGameActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(NewGameActivity.this);
                        }
                        builder.setTitle(R.string.tryAgain)
                                .setMessage(R.string.templateNotCreated)
                                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    // template was created
                    else {
                        // create items
                        for (int i = 0; i < inputFieldsIds.length; i++) {
                            EditText itemNameInput = findViewById(inputFieldsIds[i]);
                            String itemNameString = itemNameInput.getText().toString();
                            item = new Item(i + 1, itemNameString, (int) tid);
                            mItemViewModel.insertItem(item);
                        }
                        //show template with items
                        Intent mIntent = new Intent(NewGameActivity.this, TemplateDetailActivity.class);
                        mIntent.putExtra("template_id", (int) tid);
                        startActivity(mIntent);

                    }
                }
            }

        });

    }

    private int[] getItemInputFieldsId() {
        int[] inputFieldIds = new int[16];
        for (int i = 0; i < 16; i++) {
            int j = i + 1;
            String name = "item" + j;
            inputFieldIds[i] = this.getResources().getIdentifier(name, "id", this.getPackageName());
        }
        return inputFieldIds;
    }


}
