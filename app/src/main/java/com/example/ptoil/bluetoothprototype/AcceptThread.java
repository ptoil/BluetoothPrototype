package com.example.ptoil.bluetoothprototype;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;

public class AcceptThread extends Thread {
    BluetoothAdapter bluetoothAdapter;

    private final BluetoothServerSocket mmServerSocket;

    public AcceptThread() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("BluetoothPrototype", MyUUID.getUUID());
        } catch (IOException e) {
            System.out.println("Socket's listen() method failed" + e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                System.out.println("Socket's accept() method failed" + e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    System.out.println("Could not close the connect socket" + e);
                }

                break;
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close the connect socket" + e);
        }
    }
}