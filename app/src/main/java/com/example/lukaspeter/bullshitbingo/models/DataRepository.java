package com.example.lukaspeter.bullshitbingo.models;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.lukaspeter.bullshitbingo.db.DeleteAsyncTask;
import com.example.lukaspeter.bullshitbingo.db.InsertAsyncTask;
import com.example.lukaspeter.bullshitbingo.db.SelectAsyncTask;
import com.example.lukaspeter.bullshitbingo.db.UpdateAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataRepository {

    private TemplateDao mTemplateDao;
    private ItemDao mItemDao;
    private GameDao mGameDao;
    private GamelogDao mGamelogDao;
    private AppDatabase db;
    private FirebaseDB firebaseDB;

    public DataRepository(Application application) {
        db = AppDatabase.getDatabase(application);
        mTemplateDao = db.templateDao();
        mItemDao = db.itemDao();
        mGameDao = db.gameDao();
        mGamelogDao = db.gamelogDao();

        firebaseDB = FirebaseDB.getInstance();
    }

    /**
     * DB METHODS
     */

    public void clearLokalDB() {
        new DeleteAsyncTask(db).execute(0);
    }

    /**
     * TEMPLATE METHODS
     */

    public Template getTemplateById(int id) {
        try {
            return (Template) new SelectAsyncTask(mTemplateDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Template getTemplateByRemoteId(String remoteId) {
        try {
            return (Template) new SelectAsyncTask(mTemplateDao).execute(remoteId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LiveData<List<Template>> getAllTemplates() {
        try {
            return (LiveData<List<Template>>) new SelectAsyncTask(mTemplateDao).execute(0).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public long insertTemplate(Template template) {
        long id;
        try {
            id = new InsertAsyncTask(mTemplateDao).execute(template).get();
        } catch (ExecutionException ex) {
            id = 0;
        } catch (InterruptedException e) {
            id = 0;
        }
        return id;
    }

    public void updateTemplateDescription(String description, int templateId) {
        new UpdateAsyncTask(mTemplateDao).execute(description, templateId);
    }

    /**
     * ITEM METHODS
     */

    public void insertItem(Item item) {
        new InsertAsyncTask(mItemDao).execute(item);
    }

    public LiveData<List<Item>> getTemplateItems(int templateId) {
        try {
            return (LiveData<List<Item>>) new SelectAsyncTask(mItemDao).execute(templateId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * GAME METHODS
     */

    public long insertGame(Game game) {
        long id;
        try {
            id = new InsertAsyncTask(mGameDao).execute(game).get();
        } catch (ExecutionException ex) {
            id = 0;
        } catch (InterruptedException e) {
            id = 0;
        }
        return id;

    }

    public void updateGame(Game game) {
        new UpdateAsyncTask(mGameDao).execute(game);
    }

    public void deleteGame(Game game) {
        new DeleteAsyncTask(mGameDao).execute(game);
    }

    public Game getGameById(int id) {
        try {
            return (Game) new SelectAsyncTask(mGameDao).execute(id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LiveData<List<Game>> getAllGames() {
        try {
            return (LiveData<List<Game>>) new SelectAsyncTask(mGameDao).execute(0).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public LiveData<List<GameWithTemplate>> getAllGamesWithTemplate() {
        try {
            return (LiveData<List<GameWithTemplate>>) new SelectAsyncTask(mGameDao).execute(0).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * GAMELOG METHODS
     */

    public void insertGamelog(Gamelog gamelog) {
        new InsertAsyncTask(mGamelogDao).execute(gamelog);
    }

    public List<Gamelog> getGameStatus(int gameId) {
        try {
            return (List<Gamelog>) new SelectAsyncTask(mGamelogDao).execute(gameId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getItemStatus(int gameId, int itemId) {
        try {
            return (boolean) new SelectAsyncTask(mGamelogDao).execute(gameId, itemId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        // if no entry found return checked = false
        return false;
    }

    /**
     * FIREBASE METHODS
     */

    public MutableLiveData<List<RemoteTemplate>> getAllRemoteTemplates() {
        return firebaseDB.getAllTemplates();
    }

    public LiveData<List<RemoteTemplate>> findRemoteTemplatesByName(String name) {
        return firebaseDB.findTemplateByName(name);
    }

    public MutableLiveData<Boolean> uploadTemplate(Template t, List<Item> items, String description) {

        ArrayList<String> itemList = new ArrayList<>();
        for (Item item : items) itemList.add(item.getName());
        RemoteTemplate template = new RemoteTemplate(null, t.getName(), t.getCreator(), description, 0, t.getCreated(), itemList);

        return firebaseDB.insertTemplate(template);
    }
}
