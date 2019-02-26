package com.example.lukaspeter.bullshitbingo.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseDB {

    private static FirebaseDB INSTANCE;
    private FirebaseFirestore firebaseDB;

    public static FirebaseDB getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FirebaseDB();
        }
        return INSTANCE;
    }

    public FirebaseDB() {
        firebaseDB = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<List<RemoteTemplate>> getAllTemplates() {

        final MutableLiveData<List<RemoteTemplate>> liveData = new MutableLiveData<>();

        firebaseDB.collection("templates")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<RemoteTemplate> templates = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String name = document.getString("name");
                                String creator = document.getString("creator");
                                String description = document.getString("description");
                                Date created = document.getDate("created");
                                long downloaded = document.getLong("downloaded");
                                ArrayList<String> items = (ArrayList<String>) document.get("items");

                                // TODO was soll mit der ID gemacht werden???
                                RemoteTemplate t = new RemoteTemplate(id,name,creator,description,downloaded,created,items);
                                templates.add(t);
                            }

                            liveData.postValue(templates);

                        } else {
                            Log.w("DataRepository", "Error getting documents.", task.getException());
                        }
                    }

                });

        return liveData;
    }

    public MutableLiveData<List<RemoteTemplate>> findTemplateByName(String name) {

        final MutableLiveData<List<RemoteTemplate>> liveData = new MutableLiveData<>();

        firebaseDB.collection("templates")
                .orderBy("name")
                .startAt(name)
                .endAt(name+"\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<RemoteTemplate> templates = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String name = document.getString("name");
                                String creator = document.getString("creator");
                                String description = document.getString("description");
                                Date created = document.getDate("created");
                                long downloaded = document.getLong("downloaded");
                                ArrayList<String> items = (ArrayList<String>) document.get("items");

                                // TODO was soll mit der ID gemacht werden???
                                RemoteTemplate t = new RemoteTemplate(id,name,creator,description,downloaded,created,items);
                                templates.add(t);
                            }

                            liveData.postValue(templates);

                        } else {
                            Log.w("DataRepository", "Error getting documents.", task.getException());
                        }
                    }

                });

        return liveData;
    }

    public MutableLiveData<Boolean> insertTemplate(RemoteTemplate t) {

        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        firebaseDB.collection("templates").document().set(t)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        success.postValue(task.isSuccessful());
                    }
                });

        return success;
    }
}
