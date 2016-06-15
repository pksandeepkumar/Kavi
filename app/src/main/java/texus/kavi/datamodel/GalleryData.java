package texus.kavi.datamodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import texus.kavi.db.Databases;
import texus.kavi.utility.LOG;
import texus.kavi.utility.Utility;
import texus.kavi.xml.TexXmlElement;
import texus.kavi.xml.TexXmlParser;

public class GalleryData implements Parcelable {

	public static final String TABLE_NAME = "TableGallery";

	public static final String ID = "id";
	public static final String URL = "url";
	public static final String CAPTION = "caption";
	public static final String VIEWED = "viewed";
	public static final String LIKED = "liked";

    public String url = "";
	public int id = 0;
    public String caption = "";
	public boolean viewed = false;
	public boolean liked = false;

	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(url);
		dest.writeString(caption);
		dest.writeByte((byte) (viewed ? 1 : 0));
		dest.writeByte((byte) (liked ? 1 : 0));
	}

	public GalleryData() {

	}

	/**
	 * Retrieving Student data from Parcel object
	 * This constructor is invoked by the method createFromParcel(Parcel source) of
	 * the object CREATOR
	 **/
	private GalleryData(Parcel in){
		this.url = in.readString();
		this.caption = in.readString();
		this.viewed = in.readByte() != 0;
		this.liked = in.readByte() != 0;

	}

	public static final Creator<GalleryData> CREATOR = new Creator<GalleryData>() {

		@Override
		public GalleryData createFromParcel(Parcel source) {
			return new GalleryData(source);
		}

		@Override
		public GalleryData[] newArray(int size) {
			return new GalleryData[size];
		}
	};


//    <Image id="1"
// url="https://3.bp.blogspot.com/-WTbk2gwCxCc/V06KsSWVUUI/AAAAAAAAC0c/T3FBkpIB
// P_8INv1ocWiG5Dl3hS8KG3lsQCLcB/s1600/650x380_romantic_offer_opera_hotel_spa.jpg"
// caption="Hotel Ramada interior"/>

	public static final String CREATE_TABE_QUERY = "CREATE TABLE  " + TABLE_NAME
			+ " ( " + "_id" + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
			+ ID + " INTEGER, "
			+ URL + " TEXT, "
			+ CAPTION + " TEXT, "
			+ VIEWED + " varchar(1), "
			+ LIKED + " varchar(1) );";


	public static ArrayList<GalleryData> getParsed( String xml) {
		ArrayList<GalleryData> objects = new ArrayList<GalleryData>();
		TexXmlParser xmlTree = new TexXmlParser(xml);
		TexXmlElement rootElement = xmlTree.rootElement;
		if(rootElement != null) {
			for( TexXmlElement element: rootElement.children) {
				if(element == null) {
					continue;
				}
                GalleryData object =  new GalleryData();
                object.url = element.getAttribute("url");
                object.caption = element.getAttribute("caption");
				object.id = Utility.parseInt(element.getAttribute("id"));
                objects.add(object);
			}
		}
		return objects;
	}

	public static GalleryData getAnObjectFromCursor(Cursor c) {
		GalleryData instance = null;
		if( c != null) {
			instance = new GalleryData();
			instance.id = c.getInt(c.getColumnIndex(ID));
			instance.url = c.getString(c.getColumnIndex(URL));
			instance.caption = c.getString(c.getColumnIndex(CAPTION));
			instance.viewed = Utility.parseBoolean(c.getString(
					c.getColumnIndex(VIEWED)));
			instance.liked = Utility.parseBoolean(c.getString(
					c.getColumnIndex(LIKED)));
		} else {
			LOG.log("getAnObjectFromCursor:", "getAnObjectFromCursor Cursor is null");
		}
		return instance;
	}

	public static void insertObject(Databases db, GalleryData galleryData) {
		if( galleryData == null) return;
		SQLiteDatabase sqld = db.getWritableDatabase();

		ContentValues cv = new ContentValues();
		cv.put(URL, galleryData.url);
		cv.put(CAPTION, galleryData.caption);
		cv.put(ID, galleryData.id);

		sqld.insert(TABLE_NAME, null,cv);

		sqld.close();
	}


	public static void insertObjects(Databases db, ArrayList<GalleryData> galleryDatas) {
		if( galleryDatas == null) return;
		SQLiteDatabase sqld = db.getWritableDatabase();
		for( GalleryData galleryData : galleryDatas) {
			if( null == galleryData ) continue;
			ContentValues cv = new ContentValues();
			cv.put(URL, galleryData.url);
			cv.put(CAPTION, galleryData.caption);
			cv.put(ID, galleryData.id);
			sqld.insert(TABLE_NAME, null,cv);
		}
		sqld.close();
	}

	public static GalleryData getAnObject(Databases db, int id) {
		GalleryData object =  null;
		SQLiteDatabase dbRead = db.getReadableDatabase();
		String query = "select * from " + TABLE_NAME + " WHERE "
				+ ID + " = " + id + " ";
		LOG.log("Query:", "Query:" + query);
		Cursor c = dbRead.rawQuery(query, null);
		if (c.moveToFirst()) {
			object = getAnObjectFromCursor(c);
		}
		c.close();
		dbRead.close();
		return object;
	}


	public static ArrayList<GalleryData> getAllViewedObjects(Databases db) {
		ArrayList<GalleryData> objects = new ArrayList<GalleryData>();
		SQLiteDatabase dbRead = db.getReadableDatabase();
		String query = "select * from " + TABLE_NAME + " WHERE "
				+ VIEWED + " = '" + true + "' ";
		LOG.log("Query:", "Query:" + query);
		Cursor c = dbRead.rawQuery(query, null);
		if (c.moveToFirst()) {
			do {
				objects.add(getAnObjectFromCursor(c));
			} while ( c.moveToNext()) ;
		}
		c.close();
		dbRead.close();
		return objects;
	}

	public static ArrayList<GalleryData> getAllNonViewedObjects(Databases db) {
		ArrayList<GalleryData> objects = new ArrayList<GalleryData>();
		SQLiteDatabase dbRead = db.getReadableDatabase();
		String query = "select * from " + TABLE_NAME + " WHERE "
				+ VIEWED + " = '" + false + "' ";
		LOG.log("Query:", "Query:" + query);
		Cursor c = dbRead.rawQuery(query, null);
		if (c.moveToFirst()) {
			do {
				objects.add(getAnObjectFromCursor(c));
			} while ( c.moveToNext()) ;
		}
		c.close();
		dbRead.close();
		return objects;
	}


	public static ArrayList<GalleryData> getAllLikedObjects(Databases db) {
		ArrayList<GalleryData> objects = new ArrayList<GalleryData>();
		SQLiteDatabase dbRead = db.getReadableDatabase();
		String query = "select * from " + TABLE_NAME + " WHERE "
				+ LIKED + " = '" + true + "' ";
		LOG.log("Query:", "Query:" + query);
		Cursor c = dbRead.rawQuery(query, null);
		if (c.moveToFirst()) {
			do {
				objects.add(getAnObjectFromCursor(c));
			} while ( c.moveToNext()) ;
		}
		c.close();
		dbRead.close();
		return objects;
	}

	public static boolean updateObject(Databases db, GalleryData instance) {
		SQLiteDatabase sql = db.getWritableDatabase();
		String query = "";
		query = "update " + TABLE_NAME + " SET "
				+ URL + " = '" + instance.url + "' , "
				+ CAPTION + " = '" + instance.caption + "'"
				+ "' WHERE " + ID
				+ " = " + instance.id + "";
		LOG.log("Query:", "Query:" + query);
		sql.execSQL(query);
		sql.close();
		return true;
	}

	public static boolean setViewed(Databases db, GalleryData instance) {
		SQLiteDatabase sql = db.getWritableDatabase();
		String query = "";
		query = "update " + TABLE_NAME + " SET "
				+ VIEWED + " = '" + instance.viewed + "'  "
				+ "' WHERE " + ID
				+ " = " + instance.id + "";
		LOG.log("Query:", "Query:" + query);
		sql.execSQL(query);
		sql.close();
		return true;
	}

	public static boolean setLiked(Databases db, GalleryData instance) {
		SQLiteDatabase sql = db.getWritableDatabase();
		String query = "";
		query = "update " + TABLE_NAME + " SET "
				+ LIKED + " = '" + instance.liked + "'  "
				+ "' WHERE " + ID
				+ " = " + instance.id + "";
		LOG.log("Query:", "Query:" + query);
		sql.execSQL(query);
		sql.close();
		return true;
	}

	public static boolean deleteTable(Databases db) {
		try {
			SQLiteDatabase sql = db.getWritableDatabase();
			String query = "DELETE from " + TABLE_NAME;
			LOG.log("Query:", "Query:" + query);
			sql.execSQL(query);
			sql.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean deleteAnItem(Databases db, int id) {
		try {
			SQLiteDatabase sql = db.getWritableDatabase();
			String query = "DELETE from " + TABLE_NAME + " WHERE " + ID + "=" + id;
			LOG.log("Query:", "Query:" + query);
			sql.execSQL(query);
			sql.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean deleteItems(Databases db, ArrayList<GalleryData> galleryDatas) {
		if( galleryDatas == null) return true;
		try {
			SQLiteDatabase sql = db.getWritableDatabase();
			for( GalleryData galleryData : galleryDatas) {
				String query = "DELETE from " + TABLE_NAME + " WHERE " + ID + "=" + galleryData.id;
				LOG.log("Query:", "Query:" + query);
				sql.execSQL(query);
			}
			sql.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}



	public static void inseartOrUpdateOperationLocal(Databases db, GalleryData object) {
		if(object == null) return;
		GalleryData fetchedObject = getAnObject(db, object.id);
		if (fetchedObject == null) {
			insertObject(db, object);
		} else {
			updateObject(db, object);
		}
	}





}
