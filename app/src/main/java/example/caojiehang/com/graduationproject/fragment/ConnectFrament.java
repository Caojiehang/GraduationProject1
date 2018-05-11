package example.caojiehang.com.graduationproject.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.caojiehang.com.graduationproject.R;
import example.caojiehang.com.graduationproject.connectservice.SocketService;

public class ConnectFrament extends BaseFragment {
    private TextView ipAddress;
    private EditText inputIP;
    private TextView port;
    private EditText inputPort;
    private Button btConnect;
    private Button btCancel;
    private View lineView;
    private ImageView deviceImg;
    private TextView deviceName;
    private Context mContext;
    SocketService socketService = null;
    private boolean isBound = false;

    /*public Submit submits = new Submit() {
        @Override
        public void submit(String etIp, int etPort) {

        }
    };*/
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            socketService = ((SocketService.MyBinder) service).getService();
            String etIp = inputIP.getText().toString().trim();
            int etPort = Integer.valueOf(inputPort.getText().toString());
            socketService.connect(etIp, etPort);
            isBound = true;
            Toast.makeText(getActivity(), "连接成功", Toast.LENGTH_SHORT).show();
            }


        @Override
        public void onServiceDisconnected(ComponentName name) {

            isBound = false;

        }
    };

    public ConnectFrament(){


    }


    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        mContext = context;
      /*  submits =(Submit)getActivity();*/

    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connect_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)

    {
        initView();
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent = new Intent(mContext,SocketService.class);
                socketService.close();
                mContext.stopService(stopIntent);
                if(isBound) {
                    mContext.unbindService(serviceConnection);
                }
            }
        });
        btConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String etIp = inputIP.getText().toString().trim();
                int etPort = Integer.valueOf(inputPort.getText().toString());
                submits = (Submit)getActivity();
                submits.submit(etIp,etPort);*/
                Intent service = new Intent(mContext,SocketService.class);
                /* mContext.startService(service);*/
                mContext.bindService(service, serviceConnection, Context.BIND_AUTO_CREATE);
              /*  BuildConnect(etIp,etPort);*/
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        ipAddress = (TextView)getActivity().findViewById(R.id.ip_address);
        inputIP = (EditText)getActivity().findViewById(R.id.et_address);
        port = (TextView)getActivity().findViewById(R.id.port_title);
        inputPort = (EditText)getActivity().findViewById(R.id.et_port);
        btConnect = (Button) getActivity().findViewById(R.id.build_connect);
        btCancel = (Button) getActivity().findViewById(R.id.cancel_bt);
        lineView = (View)getActivity().findViewById(R.id.line_view);
        deviceImg = (ImageView)getActivity().findViewById(R.id.device_img);
        deviceName = (TextView)getActivity().findViewById(R.id.device_name);
    }
   /* public interface Submit {
        public void submit(String etIp, int etPort);
    }*/
}
