package example.caojiehang.com.graduationproject.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Timer;

import example.caojiehang.com.graduationproject.DataDb.DaoSession;
import example.caojiehang.com.graduationproject.DataDb.DeviceData;
import example.caojiehang.com.graduationproject.DataDb.GreenDaoManager;
import example.caojiehang.com.graduationproject.GsonTest.ResMsg;
import example.caojiehang.com.graduationproject.R;
import example.caojiehang.com.graduationproject.connectservice.SocketService;

public class DeviceMonitorFragment extends BaseFragment {
    private TextView datashow;
    private TextView title;
/*    private EditText reMsg;
    private EditText sgMsg;
    private Button SendBt;*/
    private Button sendeco;
    private TextView ecoMsg;

    private TextView dataTitle;
    private TextView dataMsg;
  /*  private Button dataSend;*/

    private Button openBt;
    private Button closeBt;

    private TextView data2Title;
    private TextView data2Msg;
   /* private Button data2Send;*/
    DaoSession mDaosession = GreenDaoManager.getInstance().getmDaoSession();

    /*SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd  HH:mm:ss");*/

       Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
           /* Date curDate = new Date(System.currentTimeMillis());
            String str = format.format(curDate);*/
            String receiveMsg = msg.getData().getString("data");
            Gson gson = new Gson();
            ResMsg resMsg = gson.fromJson(receiveMsg,ResMsg.class);
            ecoMsg.setText(resMsg.getGloveName());
            dataMsg.setText(resMsg.getSpeed());
            data2Msg.setText(resMsg.getEfficiency());
            if((resMsg.getSpeed()).equals("1800r/s")) {
                sendNotification();
            }
            /*reMsg.setText(receiveMsg + " " + getTime());*/
            super.handleMessage(msg);
            if (mDaosession != null) {
                DeviceData deviceData = new DeviceData(null, resMsg.getGloveName(),resMsg.getSpeed(),resMsg.getEfficiency(),
                        getTime(),getDate());
                mDaosession.getDeviceDataDao().insert(deviceData);
            } else {
                Log.d("TAG","空对象");
            }
        }
    };
    private SocketService socketService;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            socketService = ((SocketService.MyBinder) service).getService();
            socketService.setCallBack(new SocketService.CallBack() {
                @Override
                public void onDataChanged(String data) {
                    Message msg = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString("data",data);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public DeviceMonitorFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.decice_monitor,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initView();
        Intent service = new Intent(getActivity().getApplicationContext(),SocketService.class);
        getActivity().getApplicationContext().bindService(service,serviceConnection, Context.BIND_AUTO_CREATE);
       /* SendBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputMsg = sgMsg.getText().toString();
                socketService.sendMsg(inputMsg);
            }
        });*/
        sendeco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ecoMsg = "1";
                Timer timer = new Timer(true);
                timer.schedule(new java.util.TimerTask() {
                public void run() {
                    socketService.sendMsg(ecoMsg);
                }
            },0,1000);

        }
        });

        openBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String control1 = "2";
                socketService.sendMsg(control1);
            }
        });

        closeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String control2 = "3";
                socketService.sendMsg(control2);
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {

        /*datashow = (TextView)getActivity().findViewById(R.id.device_m);
        title = (TextView)getActivity().findViewById(R.id.receive_data);
        reMsg = (EditText)getActivity().findViewById(R.id.Receive_ET);
        sgMsg = (EditText)getActivity().findViewById(R.id.messge_send);
        SendBt = (Button)getActivity().findViewById(R.id.send_msg);*/
        sendeco = (Button)getActivity().findViewById(R.id.send_eco);
        ecoMsg = (TextView) getActivity().findViewById(R.id.msg_eco);
        dataTitle = (TextView)getActivity().findViewById(R.id.data1_rec);
        dataMsg = (TextView) getActivity().findViewById(R.id.msg_data1);
        openBt = (Button)getActivity().findViewById(R.id.open_bt);
        closeBt = (Button)getActivity().findViewById(R.id.close_bt);
        data2Title = (TextView) getActivity().findViewById(R.id.data2_title);
        data2Msg = (TextView) getActivity().findViewById(R.id.msg_data2);
    }
    public String  getTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);
        String time =hour+":"+minutes+":"+seconds;
        return time;
        }
        public String getDate() {
        Calendar calendar = Calendar.getInstance();
        int Month = calendar.get(Calendar.MONTH)+1;
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = Month + "-"+ Day;
        return date;
        }
        public void sendNotification() {
            NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(mContext)
                    .setContentTitle("Error")
                    .setContentText("电机故障")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_error_red_400_24dp)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_error_red_400_24dp))
                    .build();
            manager.notify(1,notification);
        }


    }


