package mnnit.vinayakAj.culrav2k14.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class StreamDB extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "culrav.db";

	public static abstract class StreamEntry implements BaseColumns {
		public static final String TABLE_NAME = "Stream";
		public static final String COLUMN_DAYTIME = "st";
		public static final String COLUMN_TITLE = "head";
		public static final String COLUMN_SUB = "sub";
		public static final String COLUMN_MSG = "msg";
	}

	public static final String[] allCols = { StreamEntry._ID,
			StreamEntry.COLUMN_DAYTIME, StreamEntry.COLUMN_TITLE,
			StreamEntry.COLUMN_SUB, StreamEntry.COLUMN_MSG };

	public static final String CREATE_TABLE = "CREATE TABLE "
			+ StreamEntry.TABLE_NAME + " (" + StreamEntry._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ StreamEntry.COLUMN_DAYTIME + " INTEGER, "
			+ StreamEntry.COLUMN_TITLE + " TEXT, " + StreamEntry.COLUMN_SUB
			+ " TEXT, " + StreamEntry.COLUMN_MSG + " TEXT)";

	private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "
			+ StreamEntry.TABLE_NAME;

	public StreamDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DELETE_TABLE);
		onCreate(db);
	}

}
