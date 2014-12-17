package br.com.fgr.emergencia.bd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BDHospitalHelper extends SQLiteOpenHelper {

    public static final String NOME_BD = "emergencia.db";

    public static final String NOME_TABELA = "hospital";
    public static final String COLUNA_ID = "_id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_LATITUDE = "latitude";
    public static final String COLUNA_LONGITUDE = "longitude";
    private static final String CRIACAO_BD = "create table " + NOME_TABELA
            + " ( " + COLUNA_ID + " integer primary key autoincrement, "
            + COLUNA_NOME + " text not null, " + COLUNA_LATITUDE
            + " real not null, " + COLUNA_LONGITUDE + " real not null);";
    public static final String PROJECAO[] = new String[]{COLUNA_ID, COLUNA_NOME, COLUNA_LATITUDE, COLUNA_LONGITUDE};
    public static final int NUM_COLUNA_ID = 0;
    public static final int NUM_COLUNA_NOME = 1;
    public static final int NUM_COLUNA_LATITUDE = 2;
    public static final int NUM_COLUNA_LONGITUDE = 3;
    private static final int VERSAO_BD = 4;
    private final Context context;
    private String caminhoBD;
    private SQLiteDatabase myDataBase;

    public BDHospitalHelper(Context context) {

        super(context, NOME_BD, null, VERSAO_BD);

        this.context = context;

        caminhoBD = context.getApplicationInfo().dataDir + "/databases/";

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // db.execSQL(CRIACAO_BD);

        try {
            createDatabase();
        } catch (IOException ioE) {
            ioE.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);

        onCreate(db);

    }

    public void createDatabase() throws IOException {

        boolean dbExist = checkDatabase();

        if (!dbExist) {

            this.getReadableDatabase();

            try {

                copyDatabase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }

        }

    }

    private boolean checkDatabase() {

        SQLiteDatabase checkDB = null;

        try {

            String myPath = caminhoBD + NOME_BD;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {

            e.printStackTrace();
        }

        if (checkDB != null)
            checkDB.close();

        return checkDB != null ? true : false;

    }

    private void copyDatabase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open(NOME_BD);

        // Path to the just created empty db
        String outFileName = caminhoBD + NOME_BD;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDatabase() throws SQLException {

        //Open the database
        String myPath = caminhoBD + NOME_BD;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

}