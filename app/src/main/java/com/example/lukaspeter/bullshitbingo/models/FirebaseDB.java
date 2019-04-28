package com.example.lukaspeter.bullshitbingo.models;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                                RemoteTemplate t = new RemoteTemplate(id, name, creator, description, downloaded, created, items);
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
                .endAt(name + "\uf8ff")
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

                                RemoteTemplate t = new RemoteTemplate(id, name, creator, description, downloaded, created, items);
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

    public MutableLiveData<Boolean> insertUser(String uid, Map<String, Object> user) {

        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        firebaseDB.collection("users").document(uid).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        success.postValue(task.isSuccessful());
                    }
                });
        return success;
    }

    public MutableLiveData<HashMap<String, String>> findUserByMail(String mail) {

        final MutableLiveData<HashMap<String, String>> liveData = new MutableLiveData<>();

        firebaseDB.collection("users")
                .whereEqualTo("email", mail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<RemoteTemplate> templates = new ArrayList<>();

                            HashMap<String, String> user = new HashMap<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                user.put("uid", document.getId());
                                user.put("name", document.getString("name"));
                                user.put("email", document.getString("email"));
                            }

                            liveData.postValue(user);

                        } else {
                            liveData.postValue(null);
                            Log.w("DataRepository", "Error getting documents.", task.getException());
                        }
                    }
                });

        return liveData;
    }

    public MutableLiveData<Boolean> updateUser(String uid, String username) {

        Map<String, Object> user = new HashMap<>();
        user.put("name", username);
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        firebaseDB.collection("users").document(uid).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        success.postValue(task.isSuccessful());
                    }
                });

        return success;
    }

    public MutableLiveData<Boolean> addSubscriber(final HashMap<String, String> user) {
        String userUID = user.get("uid");
        final String userMail = user.get("email");
        final MutableLiveData<Boolean> success = new MutableLiveData<>();
        firebaseDB.collection("users").document(userUID).update("subscribers", FieldValue.arrayUnion(userMail))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            HashMap<String, String> sub_to = new HashMap<>();
                            firebaseDB.collection("users").document(currentUser.getUid()).update("subscribes_to", FieldValue.arrayUnion(user))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            success.postValue(task.isSuccessful());
                                        }
                                    });
                        } else {
                            success.postValue(false);
                        }
                    }
                });

        return success;
    }

    public MutableLiveData<List<HashMap<String, String>>> getSubscribesTo() {

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final MutableLiveData<List<HashMap<String, String>>> liveData = new MutableLiveData<>();

        firebaseDB.collection("users")
                .document(user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<HashMap<String, String>> subscribes_to = new ArrayList<>();
                            DocumentSnapshot document = task.getResult();

                            subscribes_to = (ArrayList<HashMap<String, String>>) document.get("subscribes_to");

                            liveData.postValue(subscribes_to);

                        } else {
                            Log.w("DataRepository", "Error getting documents.", task.getException());
                        }
                    }

                });

        return liveData;
    }
}
