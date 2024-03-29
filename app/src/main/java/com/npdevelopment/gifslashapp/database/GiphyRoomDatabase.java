package com.npdevelopment.gifslashapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.npdevelopment.gifslashapp.database.dao.FavoriteDao;
import com.npdevelopment.gifslashapp.database.dao.HistoryDao;
import com.npdevelopment.gifslashapp.models.Favorite;
import com.npdevelopment.gifslashapp.models.History;

@Database(entities = {Favorite.class, History.class}, version = 1, exportSchema = false)
public abstract class GiphyRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "giphy_database";
    private static volatile GiphyRoomDatabase INSTANCE;

    public abstract FavoriteDao favoriteDao();

    public abstract HistoryDao historyDao();

    public static GiphyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GiphyRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GiphyRoomDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
