package com.lnpdit.mobilealarm.page.activity.networkalarm;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.lnpdit.mobilealarm.R;
import com.lnpdit.mobilealarm.base.component.BaseActivity;
import com.lnpdit.mobilealarm.db.DBHelper;
import com.lnpdit.mobilealarm.entity.TagInfo;
import com.lnpdit.mobilealarm.entity.TransforDate;
import com.lnpdit.mobilealarm.entity.image.Bimp;
import com.lnpdit.mobilealarm.http.HttpMultipartPostPic;
import com.lnpdit.mobilealarm.http.HttpMultipartPostVideo;
import com.lnpdit.mobilealarm.http.HttpMultipartPostVoice;
import com.lnpdit.mobilealarm.http.RdaResultPack;
import com.lnpdit.mobilealarm.page.activity.networkalarm.image.PhotoActivity;
import com.lnpdit.mobilealarm.page.activity.networkalarm.image.TestPicActivity;
import com.lnpdit.mobilealarm.page.activity.networkalarm.video.VideoActivity;
import com.lnpdit.mobilealarm.utils.FileUtils;
import com.lnpdit.mobilealarm.utils.SOAP_UTILS;
import com.lnpdit.mobilealarm.utils.sound.RecordUtil;
import com.lnpdit.mobilealarm.utils.tag.TagBaseAdapter;
import com.lnpdit.mobilealarm.utils.tag.TagCloudLayout;
import com.lnpdit.mobilealarm.webservice.SoapRes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class NetwortAlarmActivity extends BaseActivity
        implements AMapLocationListener, LocationSource {
    private Button send_bt, back_bt;
    private TagCloudLayout mContainer;
    private TagBaseAdapter mAdapter;
    private GridAdapter grid_adapter;

    private GridView noScrollgridview;
    // 语音相关控件
    private Button mRecord;
    private LinearLayout mDisplayVoiceLayout;
    private ImageView mDisplayVoicePlay;
    private View mDisplayVoiceLength;
    
//    private ProgressBar mDisplayVoiceProgressBar;
    private TextView mDisplayVoiceTime;

    private RelativeLayout mRecordLayout;
    private ImageView mRecordVolume;
    private ImageView mRecordLight_1;
    private ImageView mRecordLight_2;
    private ImageView mRecordLight_3;
    private TextView mRecordTime;
    private ProgressBar mRecordProgressBar;

    private Animation mRecordLight_1_Animation;
    private Animation mRecordLight_2_Animation;
    private Animation mRecordLight_3_Animation;

    private MediaPlayer mMediaPlayer;
    private RecordUtil mRecordUtil;
    private static final int MAX_TIME = 60;// 最长录音时间
    private static final int MIN_TIME = 2;// 最短录音时间

    private static final int RECORD_NO = 0; // 不在录音
    private static final int RECORD_ING = 1; // 正在录音
    private static final int RECORD_ED = 2; // 完成录音
    private int mRecord_State = 0; // 录音的状态
    private float mRecord_Time;// 录音的时间
    private double mRecord_Volume;// 麦克风获取的音量值
    private boolean mPlayState; // 播放状态
    private int mPlayCurrentPosition;// 当前播放的时间
    private static final String PATH = "/sdcard/KaiXin/Record/";// 录音存储路径
    private String mRecordPath;// 录音的存储名称
    private int mMAXVolume;// 最大音量高度
    private int mMINVolume;// 最小音量高度
    // private EditText mEditTextContent;
    private ImageView  volume;

    public static List<String> label_id;
    public static List<String> label_name;

    Context context;

    private OnLocationChangedListener mListener;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private AMap aMap;
    private MapView mapView;

    RelativeLayout location_layout;
    ScrollView all_scrollview;
    TextView location_text;
    EditText location_edit;
    RadioGroup tab_rg;
    RadioButton text_rb;
    RadioButton sound_rb;
    RadioButton pic_rb;
    RadioButton video_rb;
    RelativeLayout text_layout;
    EditText text_et;
    RelativeLayout sound_layout;
    RelativeLayout pic_layout;
    RelativeLayout video_layout;
    TextView voice_delete;// 音频删除
    TextView video_delete;// 视频删除
    Button video_bt; // 视频按钮
    ImageView videodisplay_img;//视频展示按钮
    

    private ProgressDialog progressDialog;

    String mobileNo;
    String cTime;
    String longitude = "";
    String latitude = "";
    String myphone = "";
    String Pic1 = "";
    String Pic2 = "";
    String Pic3 = "";
    String Pic4 = "";
    String Pic5 = "";
    String Pic6 = "";
    private String path_pic = "";//图片存储路径
    
    //音频手指移动取消
    
    int[] location = new int[2];  
    int mYpositon = 0;
//    getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标

    

    private HttpMultipartPostVideo postvideo;//视频文件上传
    private HttpMultipartPostPic postpic;//图片文件上传
    private HttpMultipartPostVoice postvoice;//音频文件上传
    

    private int mMinItemWidth; //��С��item���音频长度
    private int mMaxItemWidth; //����item���

    // 视频相关控件
    private LinearLayout video_display_layout;

    private VideoView videoView1;// 视频播放控件
    private String path_video = "";// 视频路径

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networkalarm);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        context = this;

        viewInit();
        
        mapInit();

        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        mHandler.sendEmptyMessage(SOAP_UTILS.LOCATION.MSG_LOCATION_START);

        SharedPreferences sharedPreferences = getSharedPreferences("userinfo",
                Activity.MODE_PRIVATE);
        mobileNo = sharedPreferences.getString("mobileNo", "");

        // 音频初始化

//        all_scrollview.setEnabled(false);
        setListener();
        initSound();

        String[] property_vacode = new String[] {};
        soapService.GetTagByDesc(property_vacode);
        
        //音频长度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
//        mMaxItemWidth = (int) (outMetrics.widthPixels * 0.7f);
        mMaxItemWidth = 500;
        mMinItemWidth = (int) (outMetrics.widthPixels * 0.15f);
        
//        View view = mRecord.getLayoutParams().
//        view.getLocationInWindow(location);
//        mYpositon= location[1];
    }

    private void viewInit() {
        all_scrollview = (ScrollView) findViewById(
                R.id.all_scrollview);
        // 语音相关控件
        mDisplayVoiceLayout = (LinearLayout) findViewById(
                R.id.voice_display_voice_layout);
        mDisplayVoicePlay = (ImageView) findViewById(
                R.id.voice_display_voice_play);
        mDisplayVoiceLength = (View) findViewById(
                R.id.voice_display_voice_length);
//        mDisplayVoicePlay.setImageResource(
//                R.drawable.voice3);
//        mDisplayVoiceProgressBar = (ProgressBar) findViewById(
//                R.id.voice_display_voice_progressbar);
        mDisplayVoiceTime = (TextView) findViewById(
                R.id.voice_display_voice_time);

        mRecord = (Button) findViewById(R.id.voice_record_btn);
        mRecordLayout = (RelativeLayout) findViewById(R.id.voice_record_layout);
        mRecordVolume = (ImageView) findViewById(R.id.voice_recording_volume);
        mRecordLight_1 = (ImageView) findViewById(R.id.voice_recordinglight_1);
        mRecordLight_2 = (ImageView) findViewById(R.id.voice_recordinglight_2);
        mRecordLight_3 = (ImageView) findViewById(R.id.voice_recordinglight_3);
        mRecordTime = (TextView) findViewById(R.id.voice_record_time);
        mRecordProgressBar = (ProgressBar) findViewById(
                R.id.voice_record_progressbar);
        // 图片相关控件
        grid_adapter = new GridAdapter(this);
        grid_adapter.update();
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        noScrollgridview.setAdapter(grid_adapter);
        noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(NetwortAlarmActivity.this,
                            noScrollgridview);
                } else {
                    Intent intent = new Intent(NetwortAlarmActivity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);
                }
            }
        });
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        // 设置定位监听
        locationClient.setLocationListener(this);
        // 标签自定义控件
        mContainer = (TagCloudLayout) findViewById(R.id.container);
        location_layout = (RelativeLayout) this
                .findViewById(R.id.location_layout);
        location_text = (TextView) this.findViewById(R.id.location_text);
        location_edit = (EditText) this.findViewById(R.id.location_edit);
        location_layout.setClickable(true);
        location_layout.setOnClickListener(this);
        tab_rg = (RadioGroup) this.findViewById(R.id.tab_rg);
        text_rb = (RadioButton) this.findViewById(R.id.text_rb);
        sound_rb = (RadioButton) this.findViewById(R.id.sound_rb);
        pic_rb = (RadioButton) this.findViewById(R.id.pic_rb);
        video_rb = (RadioButton) this.findViewById(R.id.video_rb);
        text_layout = (RelativeLayout) this.findViewById(R.id.text_layout);
        text_et = (EditText) this.findViewById(R.id.text_et);
        sound_layout = (RelativeLayout) this.findViewById(R.id.sound_layout);
        pic_layout = (RelativeLayout) this.findViewById(R.id.pic_layout);
        video_layout = (RelativeLayout) this.findViewById(R.id.video_layout);
        send_bt = (Button) findViewById(R.id.send_bt);
        send_bt.setOnClickListener(this);
        back_bt = (Button) findViewById(R.id.back_bt);
        back_bt.setOnClickListener(this);
        video_bt = (Button) findViewById(R.id.video_bt);
        video_bt.setOnClickListener(this);
        voice_delete = (TextView) findViewById(R.id.voice_delete);
        voice_delete.setClickable(true);
        voice_delete.setOnClickListener(this);
        video_delete = (TextView) findViewById(R.id.video_delete);
        video_delete.setClickable(true);
        video_delete.setOnClickListener(this);
        tab_rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                switch (checkedId) {
                case R.id.text_rb:
                    text_layout.setVisibility(1);
                    sound_layout.setVisibility(8);
                    pic_layout.setVisibility(8);
                    video_layout.setVisibility(8);
                    break;
                case R.id.sound_rb:
                    text_layout.setVisibility(8);
                    sound_layout.setVisibility(1);
                    pic_layout.setVisibility(8);
                    video_layout.setVisibility(8);
                    break;
                case R.id.pic_rb:
                    text_layout.setVisibility(8);
                    sound_layout.setVisibility(8);
                    pic_layout.setVisibility(1);
                    video_layout.setVisibility(8);
                    break;
                case R.id.video_rb:
                    text_layout.setVisibility(8);
                    sound_layout.setVisibility(8);
                    pic_layout.setVisibility(8);
                    video_layout.setVisibility(1);
                    break;

                default:
                    break;
                }
            }
        });
        video_rb.setChecked(true);
        // 视频相关控件

        video_display_layout = (LinearLayout) findViewById(
                R.id.video_display_layout);
        videodisplay_img = (ImageView) findViewById(R.id.videodisplay_img);
        videodisplay_img.setClickable(true);
        videodisplay_img.setOnClickListener(this);
    }

    private void labelInit(List<TagInfo> taglist) {
        label_id = new ArrayList<String>();
        label_name = new ArrayList<String>();

        // for (int i = 0; i < taglist.size(); i++) {
        // String _id = taglist.get(i).getId();
        // String _name = taglist.get(i).getTagName();
        //
        //// label_id.add(_id);
        // label_name.add(_name);
        // }
        mAdapter = new TagBaseAdapter(this, taglist);
        mContainer.setAdapter(mAdapter);

        mContainer.setItemClickListener(
                new TagCloudLayout.TagItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        // Toast.makeText(NetwortAlarmActivity.this,mList.get(position),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 初始化AMap对象
     */
    private void mapInit() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // aMap.setMyLocationType()
    }

    // 根据控件的选择，重新设置定位参数
    private void initOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(false);
        locationOption.setOnceLocation(true);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
        case R.id.send_bt:
            // 获取选中的标签
            send_bt.setEnabled(false);
            String res = "";
            for (int i = 0; i < label_id.size(); i++) {
                if (i == label_id.size() - 1) {
                    res = res + label_id.get(i);
                } else {
                    res = res + label_id.get(i) + ",";
                }
            }
            // Toast.makeText(NetwortAlarmActivity.this, res,
            // Toast.LENGTH_SHORT)
            // .show();
            SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm");       
            Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
            cTime   =    formatter.format(curDate);   
            // 获取系统当前时间
//            Time time = new Time("GMT+8");
//            time.setToNow();
//            int year = time.year;
//            int month = time.month;
//            int day = time.monthDay;
//            int minute = time.minute;
//            int hour = time.hour;
            // int sec = time.second;
//            cTime = year + "-" + month + "-" + day + " " + hour + ":" + minute;

            if (mobileNo.equals("")) {
                Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                return;
            }
            if (res.equals("")) {
                Toast.makeText(context, "请选择标签", Toast.LENGTH_SHORT).show();
                return;
            }
            if (location_edit.getText().toString().equals("")) {
                Toast.makeText(context, "请输入地址", Toast.LENGTH_SHORT).show();
                return;
            }
            if (longitude.equals("")) {
                Toast.makeText(context, "请定位", Toast.LENGTH_SHORT).show();
                return;
            }
            

            if (Bimp.drr.size() > 0) {
                Pic1 = Bimp.drr.get(0);
            }
            if (Bimp.drr.size() > 1) {
                Pic2 = Bimp.drr.get(1);
            }
            if (Bimp.drr.size() > 2) {
                Pic3 = Bimp.drr.get(2);
            }
            if (Bimp.drr.size() > 3) {
                Pic4 = Bimp.drr.get(3);
            }
            if (Bimp.drr.size() > 4) {
                Pic5 = Bimp.drr.get(4);
            }
            if (Bimp.drr.size() > 5) {
                Pic6 = Bimp.drr.get(5);
            }

                mRecordPath = mRecordPath==null?"":mRecordPath;
            
            
            if (Pic1.equals("") && text_et.getText().toString().trim().equals("") && path_video.equals("") && mRecordPath.equals("")) {
                Toast.makeText(context, "请选择文本、图片、音频、视频中至少一项输入",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (Bimp.drr.size() > 0) {
              new FileUploadPic().execute(Bimp.drr.get(0));
          }
          if (Bimp.drr.size() > 1) {
              new FileUploadPic().execute(Bimp.drr.get(1));
          }
          if (Bimp.drr.size() > 2) {
              new FileUploadPic().execute(Bimp.drr.get(2));
          }
          if (Bimp.drr.size() > 3) {
              new FileUploadPic().execute(Bimp.drr.get(3));
          }
          if (Bimp.drr.size() > 4) {
              new FileUploadPic().execute(Bimp.drr.get(4));
          }
          if (Bimp.drr.size() > 5) {
              new FileUploadPic().execute(Bimp.drr.get(5));
          }
            
            String video_name = "";
            if(!path_video.equals("")){                
                String[] video_path_array = path_video.split("/");
                video_name = video_path_array[video_path_array.length-1];
            }
            String pic_name = "";
            if(!path_pic.equals("")){                
                String[] pic_path_array = path_pic.split("/");
                pic_name = pic_path_array[pic_path_array.length-1];
            }
            String[] property_vacode = new String[] { mobileNo, res, longitude,
                    latitude, location_edit.getText().toString(),video_name, getName(mRecordPath), 
                    text_et.getText().toString(), getName(Pic1), getName(Pic2),getName(Pic3),
                    getName(Pic4), getName(Pic5), getName(Pic6), cTime };
            soapService.transforDate(property_vacode);

            
//            Toast.makeText(context, "报警提交成功",
//                    Toast.LENGTH_SHORT).show();
            progressDialog = new ProgressDialog(this);
            String stri = getResources().getString(R.string.Is_sending_a_request);
            progressDialog.setMessage(stri);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

//            progressDialog.dismiss();
            
//            finish();
            break;

        // 位置相关
        case R.id.location_layout:
            initOption();
            location_text.setText("开始定位");
            // 设置定位参数
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
            mHandler.sendEmptyMessage(SOAP_UTILS.LOCATION.MSG_LOCATION_START);
            break;
        case R.id.back_bt:
            finish();
            break;

        // 音频相关
        case R.id.voice_delete:
            mRecord.setVisibility(View.VISIBLE);
            mDisplayVoiceLayout.setVisibility(View.GONE);

//            clearSound(getName(mRecordPath));
            clearSound(mRecordPath,getName(mRecordPath));
            try {                
                if (mMediaPlayer.isPlaying()) {                
                    mMediaPlayer.stop();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            break;
            //视频相关
        case R.id.video_bt:

            Intent intent_video = new Intent();
            intent_video.putExtra("mobileNo", mobileNo);
            intent_video.setClass(context, VideoActivity.class);
            startActivityForResult(intent_video, 4);
            break;
        case R.id.video_delete:

            video_bt.setVisibility(View.VISIBLE);
            video_display_layout.setVisibility(View.GONE);
            break;
        // 小视频相关
            
        case R.id.videodisplay_img:
//            video_bt.setVisibility(View.GONE);
//            video_display_layout.setVisibility(View.VISIBLE);
//
//            filePath=192.168.1.217:8088/Upload/text.wmv  
            try {
                Intent it = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(path_video);
                it.setDataAndType(uri, "video/mp4");
                startActivity(it);
                Toast.makeText(context, "图片路径" +path_video, Toast.LENGTH_SHORT).show();
                
            } catch (Exception e) {
                // TODO: handle exception
            }
            break;
        default:
            break;
        }
    }
    
public String getName(String path_){
    
    if(path_!= null){                
        String[] path_array = path_.split("/");
        String path_name = path_array[path_array.length-1];
        return path_name;
        
    }else{        
        return "";
    }
}

    
 public boolean clearSound(String SDPATH,String fileName) {

//     public static void clearSound(String fileName) {
        //SDPATH目录路径，fileName文件名
        
//     File file = new File(SDPATH + "/" + fileName);  
        File file = new File(SDPATH );  
      boolean flag = false;
        if (file == null || !file.exists() || file.isDirectory()){  
            return false;  
        }
//        file.delete();

      flag = file.getAbsoluteFile().delete();
      Log.d(fileName,"文件" + fileName + "是否删除成功：" + flag);
        return true;
     
//     File localFile = new File(fileName);
//     boolean flag = false;
//     if(localFile.isFile()&&localFile.exists()){
//         Log.d(fileName,"localFile-Path-"+localFile.getPath()+"Ab "+localFile.getAbsolutePath());
//         Log.d(fileName,"localFile-File-"+localFile+"Ab "+localFile.getAbsoluteFile());
//         Log.d(fileName,"localFileName--"+fileName);
//         flag = localFile.getAbsoluteFile().delete();
//     }
    }  
    @Override
    protected void onEventMainThread(RdaResultPack http) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onEvent(Object obj) {
        // TODO Auto-generated method stub
        super.onEvent(obj);
        SoapRes res = (SoapRes) obj;
        if (res.getCode().equals(SOAP_UTILS.METHOD.TRANSFORDATE)) {
            send_bt.setEnabled(true);
            //释放正在提交提示
            if (res.getObj() != null) {
                progressDialog.dismiss();
                if (res.getObj().toString().equals("ok")) {

                  Toast.makeText(context, "报警提交成功",Toast.LENGTH_SHORT).show();

                    DBHelper dbh = new DBHelper(context);
                    TransforDate td = new TransforDate();
                    td.setAdress(location_edit.getText().toString());
                    td.setAudioName(mRecordPath + "," + mRecord_Time);
                    td.setVideoName(path_video);
                    td.setCoordinateX(longitude);
                    td.setCoordinateY(latitude);

                    if (Bimp.drr.size() > 0) {
                        td.setPic1(Bimp.drr.get(0));
                    }
                    if (Bimp.drr.size() > 1) {
                        td.setPic2(Bimp.drr.get(1));
                    }
                    if (Bimp.drr.size() > 2) {
                        td.setPic3(Bimp.drr.get(2));
                    }
                    if (Bimp.drr.size() > 3) {
                        td.setPic4(Bimp.drr.get(3));
                    }
                    if (Bimp.drr.size() > 4) {
                        td.setPic5(Bimp.drr.get(4));
                    }
                    if (Bimp.drr.size() > 5) {
                        td.setPic6(Bimp.drr.get(5));
                    }
                    td.setMobileNumber(mobileNo);
                    td.setTextContents(text_et.getText().toString());
                    // 获取选中的标签
                    String tag_res = "";
                    for (int i = 0; i < label_name.size(); i++) {
                        if (i == label_name.size() - 1) {
                            tag_res = tag_res + label_name.get(i);
                        } else {
                            tag_res = tag_res + label_name.get(i) + ",";
                        }
                    }
                    td.setTagName(tag_res);
                    td.setcTime(cTime);
                    dbh.insAlarmInfo(td);
                    
                    finish();
                } else if (res.getObj().toString().equals("false")) {

                    Toast.makeText(context, "网络报警失败", Toast.LENGTH_SHORT).show();
                }

            }
        } else if (res.getCode().equals(SOAP_UTILS.METHOD.GETTAGBYDESC)) {
            List<TagInfo> taglist = (List<TagInfo>) res.getObj();
            labelInit(taglist);
        }
    }

    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
            // 开始定位
            case SOAP_UTILS.LOCATION.MSG_LOCATION_START:
                location_text.setText("正在定位...");
                break;
            // 定位完成
            case SOAP_UTILS.LOCATION.MSG_LOCATION_FINISH:
                AMapLocation loc = (AMapLocation) msg.obj;
                String result = SOAP_UTILS.getLocationStr(loc);
                // location_edit.setText(result);
                location_edit.setText(loc.getAddress());
                mapView.setVisibility(1);
                location_text.setText("点击定位当前位置");
                longitude = String.valueOf(loc.getLongitude());
                latitude = String.valueOf(loc.getLatitude());
                break;
            // 停止定位
            case SOAP_UTILS.LOCATION.MSG_LOCATION_STOP:
                location_text.setText("点击定位当前位置");
                break;
            default:
                break;
            }
        };
    };

    @Override
    public void onLocationChanged(AMapLocation loc) {
        // TODO Auto-generated method stub
        if (mListener != null && loc != null) {
            if (loc != null && loc.getErrorCode() == 0) {
                mListener.onLocationChanged(loc);// 显示系统小蓝点

                Message msg = mHandler.obtainMessage();
                msg.obj = loc;
                msg.what = SOAP_UTILS.LOCATION.MSG_LOCATION_FINISH;
                mHandler.sendMessage(msg);
            } else {
                String errText = "定位失败," + loc.getErrorCode() + ": "
                        + loc.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
        mapView.onDestroy();

      Bimp.max = 0;
      Bimp.act_bool = true;
      Bimp.bmp = new ArrayList<Bitmap>(); 
      Bimp.drr = new ArrayList<String>();
        
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

//    @Override
//    protected void onStop() {
//        // TODO Auto-generated method stub
//        super.onStop();
//
//        Bimp.max = 0;
//        Bimp.act_bool = true;
//        Bimp.bmp = new ArrayList<Bitmap>(); 
//        Bimp.drr = new ArrayList<String>();
//    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        // TODO Auto-generated method stub
        mListener = listener;
        if (locationClient == null) {
            locationClient = new AMapLocationClient(this);
            locationOption = new AMapLocationClientOption();
            // 设置定位监听
            locationClient.setLocationListener(this);
            // 设置为高精度定位模式
            locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            // 设置定位参数
            locationClient.setLocationOption(locationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            locationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        mListener = null;
        if (locationClient != null) {
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient = null;
    }

    // 图片

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            loading();
        }

        public int getCount() {
            return (Bimp.bmp.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case 1:
                    grid_adapter.notifyDataSetChanged();
                    break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                path_pic = Bimp.drr.get(Bimp.max);
                                System.out.println(path_pic);
                                Bitmap bm = Bimp.revitionImageSize(path_pic);//图片压缩
                                Bimp.bmp.add(bm);
                                String newStr = path_pic.substring(//路径截取
                                        path_pic.lastIndexOf("/") + 1,
                                        path_pic.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }

    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        grid_adapter.update();
        super.onRestart();
    }

    private static final int TAKE_PICTURE = 0x000000;

    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        switch (requestCode) {
        case TAKE_PICTURE:
            if (Bimp.drr.size() < 6 && resultCode == -1) {
                Bimp.drr.add(path_pic);

//                if (Bimp.drr.size() > 0) {
//                    new FileUpload().execute(Bimp.drr.get(0));
//                }
//                if (Bimp.drr.size() > 1) {
//                    new FileUpload().execute(Bimp.drr.get(1));
//                }
//                if (Bimp.drr.size() > 2) {
//                    new FileUpload().execute(Bimp.drr.get(2));
//                }
//                if (Bimp.drr.size() > 3) {
//                    new FileUpload().execute(Bimp.drr.get(3));
//                }
//                if (Bimp.drr.size() > 4) {
//                    new FileUpload().execute(Bimp.drr.get(4));
//                }
//                if (Bimp.drr.size() > 5) {
//                    new FileUpload().execute(Bimp.drr.get(5));
//                }
            }

        Log.e(path_pic, "++++++++++++++++++++++++++++");  
        Log.e(path_pic, "++++++++++++++++++++++++++++");  
        Log.e(path_pic, "++++++++++++++++++++++++++++");  
        Log.e(path_pic, "++++++++++++++++++++++++++++");  
        Log.e(path_pic, "++++++++++++++++++++++++++++");  
            break;
            
        case 4:
            try {
                path_video = data.getStringExtra("videopath");
                if(!path_video.equals("")){
                    video_bt.setVisibility(View.GONE);
                    video_display_layout.setVisibility(View.VISIBLE);
                }else{

                    video_bt.setVisibility(View.VISIBLE);
                    video_display_layout.setVisibility(View.GONE);
                }
                
                new FileUploadVideo().execute(path_video);
                

//                String errText = "定位失败," + loc.getErrorCode() + ": "
//                        + loc.getErrorInfo();
//                String errText = "定位失败," + loc.getErrorCode() + ": "
//                        + loc.getErrorInfo();
//                String errText = "定位失败," + loc.getErrorCode() + ": "
//                        + loc.getErrorInfo();
//                String errText = "定位失败," + loc.getErrorCode() + ": "
//                        + loc.getErrorInfo();
            } catch (Exception e) {
                // TODO: handle exception
            }
            

//            Toast.makeText(context, path_video + "", Toast.LENGTH_SHORT).show();
            break;
        }
    }
//    
//    //MediaMetadataRetriever可以获取视频任何一帧的缩略图
//    public static Bitmap createVideoThumbnail(String filePath) {  
//        // MediaMetadataRetriever is available on API Level 8  
//        // but is hidden until API Level 10  
//        Class  clazz = null;  
//        Object instance = null;  
//        try {  
//            clazz = Class.forName(android.media.MediaMetadataRetriever);  
//            android.media.MediaMetadataRetriever;
//            instance = clazz.newInstance();  
//       
//            Method method = clazz.getMethod(setDataSource, String.class);  
//            method.invoke(instance, filePath);  
//       
//            // The method name changes between API Level 9 and 10.  
//            if (Build.VERSION.SDK_INT <= 9) {  
//                return (Bitmap) clazz.getMethod(captureFrame).invoke(instance);  
//            } else {  
//                byte[] data = (byte[]) clazz.getMethod(getEmbeddedPicture).invoke(instance);  
//                if (data != null) {  
//                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  
//                    if (bitmap != null) return bitmap;  
//                }  
//                return (Bitmap) clazz.getMethod(getFrameAtTime).invoke(instance);  
//            }  
//        } catch (IllegalArgumentException ex) {  
//            // Assume this is a corrupt video file  
//        } catch (RuntimeException ex) {  
//            // Assume this is a corrupt video file.  
//        } catch (InstantiationException e) {  
////            Log.e(TAG, createVideoThumbnail, e);  
//        } catch (InvocationTargetException e) {  
////            Log.e(TAG, createVideoThumbnail, e);  
//        } catch (ClassNotFoundException e) {  
////            Log.e(TAG, createVideoThumbnail, e);  
//        } catch (NoSuchMethodException e) {  
////            Log.e(TAG, createVideoThumbnail, e);  
//        } catch (IllegalAccessException e) {  
////            Log.e(TAG, createVideoThumbnail, e);  
//        } finally {  
//            try {  
//                if (instance != null) {  
//                    clazz.getMethod(release).invoke(instance);  
//                }  
//            } catch (Exception ignored) {  
//            }  
//        }  
//        return null;  
//    }
    
    public void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(
                Environment.getExternalStorageDirectory() + "/myimage/",
                String.valueOf(System.currentTimeMillis()) + ".jpg");
        path_pic = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View.inflate(mContext, R.layout.item_popupwindows,
                    null);
            view.startAnimation(
                    AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(LayoutParams.FILL_PARENT);
            setHeight(LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(NetwortAlarmActivity.this,
                            TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }

    }

    /**
     * 监听事件
     */
    private void setListener() {
       
        
        mRecord.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                // 开始录音  
                case MotionEvent.ACTION_DOWN:
                    //设置scrollview不可滚动
//                    all_scrollview.setOnTouchListener(new View.OnTouchListener(){
//                        @Override
//                        public boolean onTouch(View arg0, MotionEvent arg1) {
//                        return true;
//                        }
//                        });
Log.e("---------------------------------","DOWN" );
Log.e("---------------------------------","DOWN" );
Log.e("---------------------------------","DOWN" );
Log.e("---------------------------------","DOWN" );
Log.e("---------------------------------","DOWN" );
Log.e("---------------------------------","DOWN" );
Log.e("---------------------------------","DOWN" );
                    if (mRecord_State != RECORD_ING) {
                        // 开始动画效果
                        mRecordLayout.setVisibility(View.VISIBLE);
                        startRecordLightAnimation();
                        // 修改录音状态
                        mRecord_State = RECORD_ING;
                        // 设置录音保存路径
                        mRecordPath = PATH +UUID.randomUUID().toString()
                                + ".amr";
                        // 实例化录音工具类
                        mRecordUtil = new RecordUtil(mRecordPath);
                        try {
                            // 开始录音
                            mRecordUtil.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        new Thread(new Runnable() {

                            public void run() {
                                // 初始化录音时间
                                mRecord_Time = 0;
                                while (mRecord_State == RECORD_ING) {
                                    // 大于最大录音时间则停止录音
                                    if (mRecord_Time >= MAX_TIME) {
                                        mRecordHandler.sendEmptyMessage(0);
                                    } else {
                                        try {
                                            // 每隔200毫秒就获取声音音量并更新界面显示
                                            Thread.sleep(200);
                                            mRecord_Time += 0.2;
                                            if (mRecord_State == RECORD_ING) {
                                                mRecord_Volume = mRecordUtil
                                                        .getAmplitude();
                                                mRecordHandler
                                                        .sendEmptyMessage(1);
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }).start();
                    }
                    break;
                // 停止录音
//                case MotionEvent.ACTION_MOVE:// 当手指移动到view外面，会cancel
                        
                case MotionEvent.ACTION_UP:
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    Log.e("---------------------------------","UPUPUPUPUPUPUPUPUP" );
                    if (mRecord_State == RECORD_ING) {
                        // 停止动画效果
                        mRecordLayout.setVisibility(View.GONE);
                        stopRecordLightAnimation();
                        // 修改录音状态
                        mRecord_State = RECORD_ED;
                        try {
                            // 停止录音
                            mRecordUtil.stop();
                            // 初始录音音量
                            mRecord_Volume = 0;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // 如果录音时间小于最短时间
                        if (mRecord_Time <= MIN_TIME) {
                            // 显示提醒
                            Toast.makeText(NetwortAlarmActivity.this, "录音时间过短",
                                    Toast.LENGTH_SHORT).show();
                            // 修改录音状态
                            mRecord_State = RECORD_NO;
                            // 修改录音时间
                            mRecord_Time = 0;
                            // 修改显示界面
                            mRecordTime.setText("0″");
                            mRecordProgressBar.setProgress(0);
                            // 修改录音声音界面
                            ViewGroup.LayoutParams params = mRecordVolume
                                    .getLayoutParams();
                            params.height = 0;
                            mRecordVolume.setLayoutParams(params);
                        } else {
                            // 录音成功,则显示录音成功后的界面
                            mRecord.setVisibility(View.GONE);
                            mDisplayVoiceLayout.setVisibility(View.VISIBLE);
                            mDisplayVoicePlay.setImageResource(
                                    R.drawable.voice3);
//                            mDisplayVoiceProgressBar.setMax((int) mRecord_Time);
//                            mDisplayVoiceProgressBar.setProgress(0);
                            mDisplayVoiceTime.setText((int) mRecord_Time + "″");

                            //音频条长度
                            ViewGroup.LayoutParams lp = mDisplayVoiceLength.getLayoutParams();
                            lp.width = (int) (162 + (500 / 60f)* mRecord_Time);
//                            lp.width = (int) (mMinItemWidth + (mMaxItemWidth / 60f)* mRecord_Time);
//                            audioLength = (int) (mMinItemWidth + (mMaxItemWidth / 60f)* mRecord_Time);
                            new FileUploadVoice().execute(mRecordPath);
                        }
                    }
                    break;
                }
                return false;
            }
        });
//        mDisplayVoicePlay.setOnClickListener(new OnClickListener() {
            mDisplayVoiceLayout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // 播放录音
                if (!mPlayState) {
                    mMediaPlayer = new MediaPlayer();
                    try {
                        // 添加录音的路径
                        mMediaPlayer.setDataSource(mRecordPath);
                        // 准备
                        mMediaPlayer.prepare();
                        // 播放
                        mMediaPlayer.start();
                        // 根据时间修改界面
                        new Thread(new Runnable() {

                            public void run() {

//                                mDisplayVoiceProgressBar
//                                        .setMax((int) mRecord_Time);
                                mPlayCurrentPosition = 0;
                                while (mMediaPlayer.isPlaying()) {
                                    mPlayCurrentPosition = mMediaPlayer
                                            .getCurrentPosition() / 1000;
//                                    mDisplayVoiceProgressBar
//                                            .setProgress(mPlayCurrentPosition);
                                }
                            }
                        }).start();
                        // 修改播放状态
                        mPlayState = true;
                        // 修改播放图标
                        mDisplayVoicePlay.setImageResource(
                                R.anim.sound_anim);
                        AnimationDrawable animationDrawable = (AnimationDrawable) mDisplayVoicePlay.getDrawable();  
                        animationDrawable.start();
                        
                        mMediaPlayer.setOnCompletionListener(
                                new OnCompletionListener() {
                            // 播放结束后调用
                            public void onCompletion(MediaPlayer mp) {
                                // 停止播放
                                mMediaPlayer.stop();
                                // 修改播放状态
                                mPlayState = false;
                                // 修改播放图标
                                mDisplayVoicePlay.setImageResource(
                                        R.drawable.voice3);
                                // 初始化播放数据
                                mPlayCurrentPosition = 0;
//                                mDisplayVoiceProgressBar
//                                        .setProgress(mPlayCurrentPosition);
                            }
                        });

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (mMediaPlayer != null) {
                        // 根据播放状态修改显示内容
                        if (mMediaPlayer.isPlaying()) {
                            mPlayState = false;
                            mMediaPlayer.stop();
                            mDisplayVoicePlay.setImageResource(
                                    R.anim.sound_anim);
                            AnimationDrawable animationDrawable = (AnimationDrawable) mDisplayVoicePlay.getDrawable();  
                            animationDrawable.start();
                            mPlayCurrentPosition = 0;
//                            mDisplayVoiceProgressBar
//                                    .setProgress(mPlayCurrentPosition);
                        } else {
                            mPlayState = false;
                            mDisplayVoicePlay.setImageResource(
                                    R.drawable.voice3);
                            mPlayCurrentPosition = 0;
//                            mDisplayVoiceProgressBar
//                                    .setProgress(mPlayCurrentPosition);
                        }
                    }
                }
            }
        });
//            //设置scrollview可滚动
//            all_scrollview.setOnTouchListener(new View.OnTouchListener(){
//                @Override
//                public boolean onTouch(View arg0, MotionEvent arg1) {
//                return false;
//                }
//                });
    }

    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));

        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(
                year + "-" + month + "-" + day + " " + hour + ":" + mins);

        return sbBuffer.toString();
    }

    private void initSound() {
        // 设置当前的最小声音和最大声音值
        mMINVolume = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4.5f,
                getResources().getDisplayMetrics());
        mMAXVolume = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 65f,
                getResources().getDisplayMetrics());
    }

    /**
     * 用来控制动画效果
     */
    Handler mRecordLightHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 0:
                if (mRecord_State == RECORD_ING) {
                    mRecordLight_1.setVisibility(View.VISIBLE);
                    mRecordLight_1_Animation = AnimationUtils.loadAnimation(
                            NetwortAlarmActivity.this, R.anim.voice_anim);
                    mRecordLight_1.setAnimation(mRecordLight_1_Animation);
                    mRecordLight_1_Animation.startNow();
                }
                break;

            case 1:
                if (mRecord_State == RECORD_ING) {
                    mRecordLight_2.setVisibility(View.VISIBLE);
                    mRecordLight_2_Animation = AnimationUtils.loadAnimation(
                            NetwortAlarmActivity.this, R.anim.voice_anim);
                    mRecordLight_2.setAnimation(mRecordLight_2_Animation);
                    mRecordLight_2_Animation.startNow();
                }
                break;
            case 2:
                if (mRecord_State == RECORD_ING) {
                    mRecordLight_3.setVisibility(View.VISIBLE);
                    mRecordLight_3_Animation = AnimationUtils.loadAnimation(
                            NetwortAlarmActivity.this, R.anim.voice_anim);
                    mRecordLight_3.setAnimation(mRecordLight_3_Animation);
                    mRecordLight_3_Animation.startNow();
                }
                break;
            case 3:
                if (mRecordLight_1_Animation != null) {
                    mRecordLight_1.clearAnimation();
                    mRecordLight_1_Animation.cancel();
                    mRecordLight_1.setVisibility(View.GONE);

                }
                if (mRecordLight_2_Animation != null) {
                    mRecordLight_2.clearAnimation();
                    mRecordLight_2_Animation.cancel();
                    mRecordLight_2.setVisibility(View.GONE);
                }
                if (mRecordLight_3_Animation != null) {
                    mRecordLight_3.clearAnimation();
                    mRecordLight_3_Animation.cancel();
                    mRecordLight_3.setVisibility(View.GONE);
                }

                break;
            }
        }
    };
    /**
     * 用来控制录音
     */
    Handler mRecordHandler = new Handler() {

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case 0:
                if (mRecord_State == RECORD_ING) {
                    // 停止动画效果
                    stopRecordLightAnimation();
                    // 修改录音状态
                    mRecord_State = RECORD_ED;
                    try {
                        // 停止录音
                        mRecordUtil.stop();
                        // 初始化录音音量
                        mRecord_Volume = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // 根据录音修改界面显示内容
                    mRecordLayout.setVisibility(View.GONE);
                    mRecord.setVisibility(View.GONE);
                    mDisplayVoiceLayout.setVisibility(View.VISIBLE);
                    mDisplayVoicePlay.setImageResource(
                            R.anim.sound_anim);
                    AnimationDrawable animationDrawable = (AnimationDrawable) mDisplayVoicePlay.getDrawable();  
                            animationDrawable.start();
//                    mDisplayVoiceProgressBar.setMax((int) mRecord_Time);
//                    mDisplayVoiceProgressBar.setProgress(0);
                    mDisplayVoiceTime.setText((int) mRecord_Time + "″");
                   //音频条长度
                    ViewGroup.LayoutParams lp = mDisplayVoiceLength.getLayoutParams();
                    lp.width = (int) (mMinItemWidth + (mMaxItemWidth / 60f)* mRecord_Time);
                }
                break;

            case 1:
                // 根据录音时间显示进度条
                mRecordProgressBar.setProgress((int) mRecord_Time);
                // 显示录音时间
                mRecordTime.setText((int) mRecord_Time + "″");
                // 根据录音声音大小显示效果
                ViewGroup.LayoutParams params = mRecordVolume.getLayoutParams();
                if (mRecord_Volume < 200.0) {
                    params.height = mMINVolume;
                } else if (mRecord_Volume > 200.0 && mRecord_Volume < 400) {
                    params.height = mMINVolume * 2;
                } else if (mRecord_Volume > 400.0 && mRecord_Volume < 800) {
                    params.height = mMINVolume * 3;
                } else if (mRecord_Volume > 800.0 && mRecord_Volume < 1600) {
                    params.height = mMINVolume * 4;
                } else if (mRecord_Volume > 1600.0 && mRecord_Volume < 3200) {
                    params.height = mMINVolume * 5;
                } else if (mRecord_Volume > 3200.0 && mRecord_Volume < 5000) {
                    params.height = mMINVolume * 6;
                } else if (mRecord_Volume > 5000.0 && mRecord_Volume < 7000) {
                    params.height = mMINVolume * 7;
                } else
                    if (mRecord_Volume > 7000.0 && mRecord_Volume < 10000.0) {
                    params.height = mMINVolume * 8;
                } else if (mRecord_Volume > 10000.0
                        && mRecord_Volume < 14000.0) {
                    params.height = mMINVolume * 9;
                } else
                    if (mRecord_Volume > 14000.0 && mRecord_Volume < 17000.0) {
                    params.height = mMINVolume * 10;
                } else if (mRecord_Volume > 17000.0
                        && mRecord_Volume < 20000.0) {
                    params.height = mMINVolume * 11;
                } else
                    if (mRecord_Volume > 20000.0 && mRecord_Volume < 24000.0) {
                    params.height = mMINVolume * 12;
                } else if (mRecord_Volume > 24000.0
                        && mRecord_Volume < 28000.0) {
                    params.height = mMINVolume * 13;
                } else if (mRecord_Volume > 28000.0) {
                    params.height = mMAXVolume;
                }
                mRecordVolume.setLayoutParams(params);
                break;
            }
        }

    };

    /**
     * 开始动画效果
     */
    private void startRecordLightAnimation() {
        mRecordLightHandler.sendEmptyMessageDelayed(0, 0);
        mRecordLightHandler.sendEmptyMessageDelayed(1, 1000);
        mRecordLightHandler.sendEmptyMessageDelayed(2, 2000);
    }

    /**
     * 停止动画效果
     */
    private void stopRecordLightAnimation() {
        mRecordLightHandler.sendEmptyMessage(3);
    }

    private void updateDisplay(double signalEMA) {

        switch ((int) signalEMA) {
        case 0:
        case 1:
            volume.setImageResource(R.drawable.amp1);
            break;
        case 2:
        case 3:
            volume.setImageResource(R.drawable.amp2);
            break;
        case 4:
        case 5:
            volume.setImageResource(R.drawable.amp3);
            break;
        case 6:
        case 7:
            volume.setImageResource(R.drawable.amp4);
            break;
        case 8:
        case 9:
            volume.setImageResource(R.drawable.amp5);
            break;
        case 10:
        case 11:
            volume.setImageResource(R.drawable.amp6);
            break;
        default:
            volume.setImageResource(R.drawable.amp7);
            break;
        }
    }
    
    class FileUploadPic extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            String path = (String) params[0];
            File file = new File(path);
            if (file.exists()) {
                postpic = new HttpMultipartPostPic(context, path);
                postpic.execute();
            } else {
//                Toast.makeText(context, "file not exists", Toast.LENGTH_LONG).show();
            }
            return params;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
//            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
        }

    }
    class FileUploadVoice extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            String path = (String) params[0];
            File file = new File(path);
            if (file.exists()) {
                postvoice = new HttpMultipartPostVoice(context, path);
                postvoice.execute();
            } else {
//                Toast.makeText(context, "file not exists", Toast.LENGTH_LONG).show();
            }
            return params;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
//            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
        }

    }
    class FileUploadVideo extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object... params) {
            System.out.println(">>>>>");
            String path = (String) params[0];
            File file = new File(path);
            if (file.exists()) {
                postvideo = new HttpMultipartPostVideo(context, path);
                postvideo.execute();
            } else {
//                Toast.makeText(context, "file not exists", Toast.LENGTH_LONG).show();
            }
            return params;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
//            Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
        }

    }

}