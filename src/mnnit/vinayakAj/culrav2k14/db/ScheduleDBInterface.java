package mnnit.vinayakAj.culrav2k14.db;

import java.util.ArrayList;

import mnnit.vinayakAj.culrav2k14.db.ScheduleDB.Entry;
import mnnit.vinayakAj.culrav2k14.util.Schedule;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ScheduleDBInterface {
	SQLiteDatabase db;
	ScheduleDB dbHelper;

	public ScheduleDBInterface(Context context) {
		dbHelper = new ScheduleDB(context);
	}

	public void open() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
	}

	public void insert(Schedule schedule) {
		open();

		ContentValues cv = new ContentValues();
		cv.put(Entry.COLUMN_DAY, schedule.getDay());
		cv.put(Entry.COLUMN_TIME, schedule.getTime());
		cv.put(Entry.COLUMN_EVENT_TITLE, schedule.getTitle());
		cv.put(Entry.COLUMN_EVENT_SUBTITLE, schedule.getSubtitle());

		db.insert(Entry.TABLE_NAME, null, cv);

		close();
	}

	public void insert(ArrayList<Schedule> schedules) {
		open();
		int size = schedules.size();
		ContentValues cv = new ContentValues();

		for (int i = 0; i < size; i++) {
			Schedule schedule = schedules.get(i);

			cv.put(Entry.COLUMN_DAY, schedule.getDay());
			cv.put(Entry.COLUMN_TIME, schedule.getTime());
			cv.put(Entry.COLUMN_EVENT_TITLE, schedule.getTitle());
			cv.put(Entry.COLUMN_EVENT_SUBTITLE, schedule.getSubtitle());

			db.insert(Entry.TABLE_NAME, null, cv);
		}
		close();
	}

	public ArrayList<Schedule> getSchedules() {
		ArrayList<Schedule> result = new ArrayList<Schedule>();
		open();

		Cursor cursor = db.query(Entry.TABLE_NAME, ScheduleDB.allCols, null,
				null, null, null, Entry.COLUMN_DAY);
		if (cursor.moveToFirst()) {
			do {
				result.add(new Schedule(cursor));
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
		dbHelper.onUpgrade(db, 1, 2);
		close();
	}
}
