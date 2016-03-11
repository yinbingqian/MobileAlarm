package com.lnpdit.mobilealarm.utils;

import com.amap.api.location.AMapLocation;

public class SOAP_UTILS {
	public class METHOD {
		public static final String CHECKUSERLOGIN = "CheckUserLogin";
        public static final String ADDUSERS = "AddUsers";
        public static final String SETIDENTIFYCODE = "SetIdentifyCode";
        public static final String UPDATEBYCODE = "updateByCode";
        public static final String SETIDENTIFYCODE_RESETPWD = "SetIdentifyCode_ResetPWD";
        public static final String UPDATEUSERPWD = "updateUserPWD";
        public static final String TRANSFORDATE = "transforDate";
        public static final String SAVEFILE = "SaveFile";
        public static final String GETTAGBYDESC = "GetTagByDesc";
	}

	public class ERROR {
		public static final String ERR0000 = "ERR 000";
		public static final String ERR0001 = "ERR 001";
		public static final String ERR0002 = "ERR 002";
		public static final String ERR0003 = "ERR 003";
		public static final String ERR0004 = "ERR 004";
		public static final String ERR0005 = "ERR 005 XML解析错误";
		public static final String ERR0006 = "ERR 006 本地错误";

	}
	
	public class LOCATION {
	    /**
	     *  开始定位
	     */
	    public final static int MSG_LOCATION_START = 0;
	    /**
	     * 定位完成
	     */
	    public final static int MSG_LOCATION_FINISH = 1;
	    /**
	     * 停止定位
	     */
	    public final static int MSG_LOCATION_STOP= 2;
	}

	public static final String NAMESPACE = "MobileNewspaper";
    public static final String IP_SIMPLE = "200.20.30.92";
//    public static final String IP_SIMPLE = "221.180.149.201";
//    public static final String IP = "http://200.20.30.130:7799";
//    public static final String IP = "http://200.20.32.120:7799";
    public static final String IP = "http://123.57.56.70:7799";
//    public static final String IP = "http://200.20.31.81:7799";
//    public static final String IP = "http://200.20.30.126:7799";
    
//    public static final String IP = "http://200.20.30.92:7799";
//    public static final String URL = IP + "/manage/videoPost.aspx";
    public static final String URL = IP + "/phoneinvoke.asmx?wsdl";
    public static final String URL_WITHOUT_WSDL = IP + "/phoneinvoke.asmx";
    public static final String PIC_FILE = IP + "/manage/pic/";
    public static final String PIC_JOURNAL = IP + "/manage/magpic/";
    public static final String PIC_PUSH = IP + "/upload/";
    public static final String URL_SERVER = IP + "/apksource/version.xml";
    public static final String VIDEO_PATH = IP + "/manage/videofile/";
    public static final String ATTEND_PATH = IP + "/manage/pic/attend/";
    public static final String FOOD_PATH = IP + "/manage/pic/food/";
    public static final String AUDIO_PATH = IP + "/audio/";
    public static final String COL_PATH = IP + "/columns.xml";
    
	// login type
	public static final int POLICE = 0;// 警察
	public static final int CITIZEN = 1;// 市民

	/**
     * 根据定位结果返回定位信息的字符串
     * @param loc
     * @return
     */
    public synchronized static String getLocationStr(AMapLocation location){
        if(null == location){
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if(location.getErrorCode() == 0){
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");
            
            if (location.getProvider().equalsIgnoreCase(
                    android.location.LocationManager.GPS_PROVIDER)) {
                // 以下信息只有提供者是GPS时才会有
                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : "
                        + location.getExtras().getInt("satellites", 0) + "\n");
            } else {
                // 供者是GPS时是没有以下信息的
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
            }
        } else {
            //定位失败
            sb.append("定位为失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }
        return sb.toString();
    }
	
}
