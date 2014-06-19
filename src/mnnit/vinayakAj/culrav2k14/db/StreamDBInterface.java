package mnnit.vinayakAj.culrav2k14.db;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.db.StreamDB.StreamEntry;
import mnnit.vinayakAj.culrav2k14.util.Stream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StreamDBInterface {
	SQLiteDatabase db;
	StreamDB dbHelper;

	public StreamDBInterface(Context context) {
		dbHelper = new StreamDB(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public void insert(Stream item) {
		open();

		ContentValues cv = new ContentValues();
		cv.put(StreamEntry.COLUMN_DAYTIME, item.getId());
		cv.put(StreamEntry.COLUMN_TITLE, item.getTitle());
		cv.put(StreamEntry.COLUMN_SUB, item.getSubtitle());
		cv.put(StreamEntry.COLUMN_MSG, item.getMessage());

		db.insert(StreamEntry.TABLE_NAME, null, cv);

		close();
	}

	public void insert(ArrayList<Stream> items) {
		open();
		ContentValues cv = new ContentValues();
		int size = items.size();

		for (int i = 0; i < size; i++) {
			cv.put(StreamEntry.COLUMN_DAYTIME, items.get(i).getId());
			cv.put(StreamEntry.COLUMN_TITLE, items.get(i).getTitle());
			cv.put(StreamEntry.COLUMN_SUB, items.get(i).getSubtitle());
			cv.put(StreamEntry.COLUMN_MSG, items.get(i).getMessage());

			db.insert(StreamEntry.TABLE_NAME, null, cv);
		}

		close();
	}

	public ArrayList<Stream> getStreamItems() {
		ArrayList<Stream> result = new ArrayList<Stream>();

		open();

		Cursor cursor = db.query(StreamEntry.TABLE_NAME, StreamDB.allCols,
				null, null, null, null, StreamEntry.COLUMN_DAYTIME + " DESC");
		if (cursor.moveToFirst()) {
			do {
				result.add(new Stream(cursor));
			} while (cursor.moveToNext());
		} else {
			result = null;
		}
		cursor.close();

		close();

		return result;
	}

	public void reset() {
		open();
		dbHelper.onUpgrade(db, 0, 1);
		close();
	}
}
