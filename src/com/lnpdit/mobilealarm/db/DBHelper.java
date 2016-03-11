package com.lnpdit.mobilealarm.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.lnpdit.mobilealarm.entity.LoginUser;
import com.lnpdit.mobilealarm.entity.TransforDate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "";
	private static final int VERSION = 1;
	private static final CursorFactory FACTORY = null;
	private static String DB_NAME = "mobilealarm.db";
	private Context context;
	private static String DB_PATH = "/data/data/com.lnpdit.mobilealarm/databases/";
	private static final int ASSETS_SUFFIX_BEGIN = 101;
	private static final int ASSETS_SUFFIX_END = 103;
	private SQLiteDatabase myDataBase = null;

	private String CHAT_DB = "";
	private String FRIE_DB = "";

	/**
	 * _id integer primary key autoincrement not null "byte_content blob "user_guid text "insert_date text "direction integer "type integer "user_clid bigint "target_type integer "child_clid text
	 * 
	 */
	private static final String CREATE_TABLE_T_SU_ALARM = "CREATE TABLE T_SU_ALARM (ALARMID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,MOBILENUMBER TEXT,TAGNAME TEXT, COORDINATEX TEXT,  COORDINATEY TEXT, ADRESS TEXT,VIDEONAME TEXT,AUDIONAME TEXT,TEXTCONTENTS TEXT,PIC1 TEXT,PIC2 TEXT,PIC3 TEXT,PIC4 TEXT,PIC5 TEXT,PIC6 TEXT,CTIME TEXT)";


	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, null, version);
		this.context = context;
	}

	public DBHelper(Context context, String name, int version) {
		this(context, name, null, version);
	}

	public DBHelper(Context context, String name) {
		this(context, name, VERSION);
	}

	public DBHelper(Context context) {
		this(context, DB_PATH + DB_NAME);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("stockup.db ========== onCreate");
		db.execSQL(CREATE_TABLE_T_SU_ALARM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * 网络报警信息表
	 */

	/**
	 * 
	 *CREATE_TABLE_T_SU_ALARM = "CREATE TABLE T_SU_ALARM (ALARMID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,MOBILENUMBER TEXT,TAGNAME TEXT, COORDINATEX TEXT,  COORDINATEY TEXT, ADRESS TEXT,VIDEONAME TEXT,AUDIONAME TEXT,TEXTCONTENTS TEXT,PIC1 TEXT,PIC2 TEXT,PIC3 TEXT,PIC4 TEXT,PIC5 TEXT,PIC6 TEXT,CTIME TEXT)";
	 * @param data
	 */
	public void insAlarmInfo(TransforDate data) {
		System.out.println("#SU DB# alarminfo");
		SQLiteDatabase db = getWritableDatabase();
		ContentValues alarminfo = new ContentValues();
		alarminfo.put("MOBILENUMBER", data.getMobileNumber());
		alarminfo.put("TAGNAME", data.getTagName());
		alarminfo.put("COORDINATEX", data.getCoordinateX());
		alarminfo.put("COORDINATEY", data.getCoordinateY());
		alarminfo.put("ADRESS", data.getAdress());
		alarminfo.put("VIDEONAME", data.getVideoName());
		alarminfo.put("AUDIONAME", data.getAudioName());
		alarminfo.put("TEXTCONTENTS", data.getTextContents());
		alarminfo.put("PIC1", data.getPic1());
		alarminfo.put("PIC2", data.getPic2());
		alarminfo.put("PIC3", data.getPic3());
		alarminfo.put("PIC4", data.getPic4());
		alarminfo.put("PIC5", data.getPic5());
		alarminfo.put("PIC6", data.getPic6());
		alarminfo.put("CTIME", data.getcTime());
		db.insert("T_SU_ALARM", "", alarminfo);
		close();
	}

	public int clearAlarmInfo() {
		System.out.println("#SU DB# clearAlarmInfo");
		SQLiteDatabase db = getWritableDatabase();
		return db.delete("T_SU_ALARM", null, null);
	}

	public List<TransforDate> queryAlarmInfo(String mobile) {
		System.out.println("#SU DB# queryAlarmInfo");
		List<TransforDate> list = new ArrayList<TransforDate>();
		TransforDate alarminfo = new TransforDate();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM T_SU_ALARM", null);
		if (c.getCount()!=0) {
            c.moveToLast();
            for (int i = 0; i < c.getCount(); i++) {                
                alarminfo = new TransforDate();
                alarminfo.setMobileNumber(c.getString(c.getColumnIndex("MOBILENUMBER")));
                alarminfo.setTagName(c.getString(c.getColumnIndex("TAGNAME")));
                alarminfo.setCoordinateX(c.getString(c.getColumnIndex("COORDINATEX")));
                alarminfo.setCoordinateY(c.getString(c.getColumnIndex("COORDINATEY")));
                alarminfo.setAdress(c.getString(c.getColumnIndex("ADRESS")));
                alarminfo.setVideoName(c.getString(c.getColumnIndex("VIDEONAME")));
                alarminfo.setAudioName(c.getString(c.getColumnIndex("AUDIONAME")));
                alarminfo.setTextContents(c.getString(c.getColumnIndex("TEXTCONTENTS")));
                alarminfo.setPic1(c.getString(c.getColumnIndex("PIC1")));
                alarminfo.setPic2(c.getString(c.getColumnIndex("PIC2")));
                alarminfo.setPic3(c.getString(c.getColumnIndex("PIC3")));
                alarminfo.setPic4(c.getString(c.getColumnIndex("PIC4")));
                alarminfo.setPic5(c.getString(c.getColumnIndex("PIC5")));
                alarminfo.setPic6(c.getString(c.getColumnIndex("PIC6")));
                alarminfo.setcTime(c.getString(c.getColumnIndex("CTIME")));
//			System.out.println(">>>??@@@" + adviser.getName());
//			System.out.println(">>>??@@@" + adviser.getHeartcount());
                if(mobile.equals(c.getString(c.getColumnIndex("MOBILENUMBER")))){                    
                    list.add(alarminfo);
                }
                c.moveToPrevious();
            }
        }
		close();
		return list;
	}
	
	
//	
//	public Adviser queryAdviserInfoById(String id) {
//		System.out.println("#SU DB# queryAdviserInfo");
//		List<Adviser> list = new ArrayList<Adviser>();
//		Adviser adviser = new Adviser();
//		SQLiteDatabase db = this.getReadableDatabase();
//		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI WHERE ADV_USER_ID = "+id, null);
//		while (c.moveToNext()) {
//			adviser = new Adviser();
//			adviser.setAdv_user_id(c.getString(c.getColumnIndex("ADV_USER_ID")));
//			adviser.setCrtime(c.getString(c.getColumnIndex("CRTIME")));
//			adviser.setEmail(c.getString(c.getColumnIndex("EMAIL")));
//			adviser.setGroupid(c.getString(c.getColumnIndex("GROUP_ID")));
//			adviser.setHeadpic(c.getString(c.getColumnIndex("HEADPIC")));
//			adviser.setSex(c.getString(c.getColumnIndex("SEX")));
//			adviser.setIslock(c.getInt(c.getColumnIndex("ISLOCK")) == 0 ? true : false);
//			adviser.setLevel(c.getString(c.getColumnIndex("LEVEL")));
//			adviser.setMark(c.getString(c.getColumnIndex("MARK")));
//			adviser.setMobilephone(c.getString(c.getColumnIndex("MOBILEPHONE")));
//			adviser.setName(c.getString(c.getColumnIndex("NAME")));
//			adviser.setOrgid(c.getString(c.getColumnIndex("ORGID")));
//			adviser.setOrgname(c.getString(c.getColumnIndex("ORGNAME")));
//			adviser.setPaidmark(c.getString(c.getColumnIndex("PAIDMARK")));
//			adviser.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
//			adviser.setPtitle(c.getString(c.getColumnIndex("PTITLE")));
//			adviser.setRealname(c.getString(c.getColumnIndex("REALNAME")));
//			adviser.setRewardmark(c.getString(c.getColumnIndex("REWARDMARK")));
//			adviser.setResume(c.getString(c.getColumnIndex("RESUME")));
//			adviser.setSpecialty(c.getString(c.getColumnIndex("SPECIALTY")));
//			adviser.setStatus(c.getString(c.getColumnIndex("STATUS")));
//			adviser.setHeartcount(c.getString(c.getColumnIndex("HEARTCOUNT")));
//			System.out.println(">>>##@@@" + adviser.getName());
//			System.out.println(">>>##@@@" + adviser.getHeartcount());
//		}
//		close();
//		return adviser;
//	}
	
	
//
//	public String getAdviserHeader(String aid) {
//		System.out.println("#SU DB# getAdviserHeader");
//		String header = "";
//		List<Adviser> list = new ArrayList<Adviser>();
//		Adviser adviser = new Adviser();
//		SQLiteDatabase db = this.getReadableDatabase();
//		String[] str = { aid };
//		Cursor c = db.rawQuery("SELECT * FROM T_SU_ARI WHERE ADV_USER_ID = ?", str);
//		while (c.moveToNext()) {
//			adviser = new Adviser();
//			header = c.getString(c.getColumnIndex("HEADPIC"));
//
//			System.out.println("> pic >>??" + header);
//			list.add(adviser);
//		}
//		close();
//		return header;
//	}

}
