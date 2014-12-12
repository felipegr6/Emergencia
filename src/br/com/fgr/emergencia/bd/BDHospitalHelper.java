package br.com.fgr.emergencia.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDHospitalHelper extends SQLiteOpenHelper {

	public static final String NOME_BD = "hospitais.db";

	public static final String NOME_TABELA = "hospital";
	public static final String COLUNA_ID = "_id";
	public static final String COLUNA_NOME = "nome";
	public static final String COLUNA_LATITUDE = "latitude";
	public static final String COLUNA_LONGITUDE = "longitude";

	public static final int NUM_COLUNA_ID = 0;
	public static final int NUM_COLUNA_NOME = 1;
	public static final int NUM_COLUNA_LATITUDE = 2;
	public static final int NUM_COLUNA_LONGITUDE = 3;

	private static final String CRIACAO_BD = "create table " + NOME_TABELA
			+ " ( " + COLUNA_ID + " integer primary key autoincrement, "
			+ COLUNA_NOME + " text not null, " + COLUNA_LATITUDE
			+ " real not null, " + COLUNA_LONGITUDE + " real not null);";

	private static final int VERSAO_BD = 1;

	public BDHospitalHelper(Context context) {

		super(context, NOME_BD, null, VERSAO_BD);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CRIACAO_BD);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS " + NOME_TABELA);

		onCreate(db);

	}

}