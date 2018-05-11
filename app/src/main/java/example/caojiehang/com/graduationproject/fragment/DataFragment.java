package example.caojiehang.com.graduationproject.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import example.caojiehang.com.graduationproject.DataDb.DeviceData;
import example.caojiehang.com.graduationproject.DataDb.DeviceDataDao;
import example.caojiehang.com.graduationproject.DataDb.GreenDaoManager;
import example.caojiehang.com.graduationproject.R;
import example.caojiehang.com.graduationproject.utils.CheckNumber;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class DataFragment extends BaseFragment {
    private DeviceDataDao  dataDao= GreenDaoManager.getInstance().getmDaoSession().getDeviceDataDao();
    private LineChartView lineChart;
    private TextView dateTitle;
    private EditText inputdate;
    private TextView startTitle;
    private EditText inputTime;
    private TextView endTitle;
    private EditText inputEndTIme;
    private Button queryBt;
    private CheckNumber checkNumber = new CheckNumber();



   /* String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};
    int[] score = {50,42,90,33,10,74,22,18,79,20};*/
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    public DataFragment() {

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_view,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initView();
    /*    getAxisXLables();
        getAxisPoints();*/
        queryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryBuilder.LOG_VALUES = true;
                QueryBuilder.LOG_SQL = true;
                String date = inputdate.getText().toString();
                String time1 = inputTime.getText().toString();
                String time2 = inputEndTIme.getText().toString();
                List<DeviceData> list = dataDao.queryBuilder().where(
                        DeviceDataDao.Properties.Date.eq(date),
                        DeviceDataDao.Properties.
                        ReceiveTime.between(time1,time2)).build().list();
                        if(mAxisXValues.size()>0||mPointValues.size()>0 ){
                            mPointValues.clear();
                            mAxisXValues.clear();
                            initLineChart();
                        }
                        else {
                            for(int i = 0; i<list.size();i++) {
                                mAxisXValues.add(new AxisValue(i).setLabel(list.get(i).getReceiveTime()));
                                mPointValues.add(new PointValue(i, checkNumber.Number(list.get(i).getEfficiency())));
                            }
                            initLineChart();
                        }

/*
                   for(DeviceData deviceData : list ) {
                       Log.d("id:",deviceData.getId().toString());
                       Log.d("speed",deviceData.getSpeed());
                       Log.d("efficiency:",deviceData.getEfficiency());
                   }
*/

                }

        });

        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        lineChart = (LineChartView)getActivity().findViewById(R.id.line_chart);
        startTitle = (TextView)getActivity().findViewById(R.id.start_time);
        inputTime = (EditText)getActivity().findViewById(R.id.input_time);
        endTitle = (TextView)getActivity().findViewById(R.id.end_time);
        inputEndTIme = (EditText)getActivity().findViewById(R.id.input_endTime);
        queryBt = (Button)getActivity().findViewById(R.id.search_bt);
        dateTitle = (TextView)getActivity().findViewById(R.id.date);
        inputdate = (EditText)getActivity().findViewById(R.id.input_date);
    }

//    private void getAxisXLables() {
//        for(int i = 0; i < date.length ;i++ ) {
//            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
//
//        }
//    }
//
//    private void getAxisPoints() {
//        for (int i = 0;i < score.length;i++) {
//            mPointValues.add(new PointValue(i,score[i]));
//
//        }
//    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(false);
        line.setFilled(false);
        line.setHasLabels(true);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasLines(true);
        line.setHasPoints(true);

        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axisX = new Axis();
        axisX.setHasTiltedLabels(true);
        axisX.setTextColor(Color.RED);
        axisX.setTextSize(10);
        axisX.setMaxLabelChars(11);
        axisX.setValues(mAxisXValues);
        data.setAxisXBottom(axisX);
        axisX.setHasLines(true);

        Axis axisY = new Axis();
        axisY.setName("生产效率");
        axisY.setTextSize(12);
        axisY.setTextColor(Color.BLACK);
        data.setAxisYLeft(axisY);


        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float) 2);
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);

        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);

    }



}
