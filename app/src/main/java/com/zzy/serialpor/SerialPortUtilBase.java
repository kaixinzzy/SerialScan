package com.zzy.serialpor;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

public class SerialPortUtilBase {
    private static final String TAG = "SerialPortUtilBase";

    private SerialPort serialPort = null;
    private boolean isSerialPortValid = false;// 串口是否打开

    OutputStream outputStream;
    InputStream inputStream;

    SerialPortUtilBase(String path, int baudrate, int parity) {
        // 打开串口
        getSerialPort(path, baudrate, parity);
        if (null != serialPort) {
            try {
                outputStream = serialPort.getOutputStream();
                inputStream = serialPort.getInputStream();
                isSerialPortValid = true;
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "串口打开失败");
        }
    }

    /**
     * 打开串口
     * @param path  串口号
     * @param baudrate 波特率
     * @param parity 奇偶校验
     * @return 串口对象
     */
    private SerialPort getSerialPort(String path, int baudrate, int parity) {
        if (serialPort == null) {
            File file = new File(path);
            try {
                serialPort = new SerialPort(file, baudrate, parity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return serialPort;
    }

    boolean isSerialPortValid() {
        return isSerialPortValid;
    }

    /**
     * 发送消息
     * @param buffer 发送的消息
     */
    private void sendBuffer(final byte[] buffer) {
        try {
            outputStream.write(buffer);
//            String dataStr = byteToString(buffer, buffer.length);
//            Log.d(TAG, "PC->VMC sendBuffer = " + dataStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
