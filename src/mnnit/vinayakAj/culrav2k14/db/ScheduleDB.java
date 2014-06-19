package mnnit.vinayakAj.culrav2k14.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ScheduleDB extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "culravSchedule.db";

	public static abstract class Entry implements BaseColumns {
		public static final String TABLE_NAME = "Schedule";
		public static final String COLUMN_DAY = "day";
		public static final String COLUMN_TIME = "time";
		public static final String COLUMN_EVENT_TITLE = "main";
		public static final String COLUMN_EVENT_SUBTITLE = "sub";
	}

	public static final String[] allCols = { Entry._ID, Entry.COLUMN_DAY,
			Entry.COLUMN_TIME, Entry.COLUMN_EVENT_TITLE,
			Entry.COLUMN_EVENT_SUBTITLE };

	private static final String CREATE_TABLE = "CREATE TABLE "
			+ Entry.TABLE_NAME + " (" + Entry._ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Entry.COLUMN_DAY
			+ " INTEGER, " + Entry.COLUMN_TIME + " TEXT, "
			+ Entry.COLUMN_EVENT_TITLE + " TEXT, "
			+ Entry.COLUMN_EVENT_SUBTITLE + " TEXT)";

	private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "
			+ Entry.TABLE_NAME;

	public ScheduleDB(Context context) {
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
