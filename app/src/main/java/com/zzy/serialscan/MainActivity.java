package com.zzy.serialscan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zzy.serialpor.SerialPortUtil;

/**
 * 以刷卡器为例，
 * 设备分为两种状态
 *  1：闲置状态 提示灯灭，不卡不响应
 *  2：工作状态 提示灯亮，刷卡后将刷卡器得到的信息传给android设备
 */
public class MainActivity extends AppCompatActivity {
    private String path = "/dev/ttyACM0";// 串口地址
    private int baudrate = 9600;// 波特率
    private int parity = 1;// 校验字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SerialPortUtil(path, baudrate, parity);

    }



}
