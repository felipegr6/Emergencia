package br.com.fgr.emergencia.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.io.IOException;

public class HospitalProvider extends ContentProvider {

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/hospitais";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/hospital";
    private static final int HOSPITAIS = 1;
    private static final int HOSPITAL_ID = 2;
    private static final String AUTHORITY = "br.com.fgr.emergencia.contentprovider";
    private static final String BASE_PATH = "hospitais";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);
    private static final UriMatcher sURIMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {

        sURIMatcher.addURI(AUTHORITY, BASE_PATH, HOSPITAIS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", HOSPITAL_ID);

    }

    private BDHospitalHelper database;

    @Override
    public boolean onCreate() {

        database = new BDHospitalHelper(getContext());

        try {
            database.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        database.openDatabase();

        return false;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Set the table
        queryBuilder.setTables(BDHospitalHelper.NOME_TABELA);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case HOSPITAIS:
                break;
            case HOSPITAL_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(BDHospitalHelper.COLUNA_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;

    }

    @Override
    public String getType(Uri uri) {

        return null;

    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;

        switch (uriType) {

            case HOSPITAIS:
                id = sqlDB.insert(BDHospitalHelper.NOME_TABELA, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {

            case HOSPITAIS:
                rowsDeleted = sqlDB.delete(BDHospitalHelper.NOME_TABELA, selection,
                        selectionArgs);
                break;
            case HOSPITAL_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection))
                    rowsDeleted = sqlDB.delete(BDHospitalHelper.NOME_TABELA,
                            BDHospitalHelper.COLUNA_ID + "=" + id, null);
                else
                    rowsDeleted = sqlDB.delete(BDHospitalHelper.NOME_TABELA,
                            BDHospitalHelper.COLUNA_ID + "=" + id + " and "
                                    + selection, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {

            case HOSPITAIS:
                rowsUpdated = sqlDB.update(BDHospitalHelper.NOME_TABELA, values,
                        selection, selectionArgs);
                break;
            case HOSPITAL_ID:
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection))
                    rowsUpdated = sqlDB.update(BDHospitalHelper.NOME_TABELA,
                            values, BDHospitalHelper.COLUNA_ID + "=" + id, null);
                else
                    rowsUpdated = sqlDB.update(BDHospitalHelper.NOME_TABELA,
                            values, BDHospitalHelper.COLUNA_ID + "=" + id + " and "
                                    + selection, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowsUpdated;

    }

}