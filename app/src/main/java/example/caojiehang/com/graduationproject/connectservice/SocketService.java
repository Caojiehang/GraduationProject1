package example.caojiehang.com.graduationproject.connectservice;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;

import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService extends Service {
    private final String TAG = "SocketService";
    private String ip ;
    private int port ;
    Socket socket = null;
    MyBinder myBinder = new MyBinder();
    public SocketService() {
        super();
    }

    public class MyBinder extends Binder {
       public SocketService getService() {
            return SocketService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate()");
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
/*        ip = "192.168.104.2";
        port = 8080;
        connect(ip,port);*/
        Log.d(TAG,"SUCCESS");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    /*    Log.d(TAG,"STOPED");*/
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"onBind()");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: success");
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {

        super.onRebind(intent);
    }

    public void connect(final String ipaddress, final int portnumber) {
        new Thread(new Runnable() {
            String ips = ipaddress;
            int ports = portnumber;

            @Override
            public void run() {
                try {
                        socket = new Socket(ips, ports);
                        Receive_Thread receive_thread = new Receive_Thread();
                        if(!socket.isClosed()) {
                        receive_thread.start();
                        } else {
                            receive_thread.interrupt();
                        }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "无法创建socket");
                }
            }

        }).start();


    }
    public class Receive_Thread extends Thread {
        @Override
        public void run() {
                resMsg();
            super.run();
        }
    }
    public void resMsg() {
        try {
           /* InputStream inputStream = socket.getInputStream();
            DataInputStream input = new DataInputStream(inputStream);
            byte[] b = new byte[10000];
            while(true) {
                int length = input.read(b);
                String Msg = new String (b,0,length,"UTF-8");
                Log.d(TAG, "recievedMsg:" + Msg);
                if (callBack != null) {
                    callBack.onDataChanged(Msg);
                }*/
          /*  }*/
         /*   if(socket.isConnected()) {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String Msg = reader.readLine();*/

            DataInputStream input = new DataInputStream(socket.getInputStream());
            String jsonStr = null;
            String jsonByte = null;
            int len = 0;
            byte[] byt = new byte[1024];
            while ((len = input.read(byt)) != -1) {
                jsonStr = new String(byt, 0, len);
                jsonByte = new String(jsonStr.getBytes(), "GBK");
                Log.d(TAG, "recievedMsg:" + jsonByte);
                if (callBack != null) {
                    callBack.onDataChanged(jsonByte);
                }
            }

            } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private CallBack callBack = null;
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
    public static interface CallBack {
        void onDataChanged(String data);
    }
    public void sendMsg(final String msg) {
      Thread sendThread = new Thread (new Runnable() {
            @Override
            public void run() {
                OutputStream out = null;
                BufferedWriter bw = null;
                try {
                     out = socket.getOutputStream();
                     bw = new BufferedWriter(new OutputStreamWriter(out));
                     bw.write(msg+"\n");
                     bw.flush();
                 }catch (IOException e) {
                     e.printStackTrace();
                }

            }
        });
        if (!socket.isClosed()) {
            sendThread.start();
        }else {
            sendThread.interrupt();
        }

    }
    public void close() {
        try {
                socket.close();

        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
