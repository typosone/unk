package jp.ac.it_college.std.nakasone.shakebattle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

/**
 *
 */
public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private OnReceiveListener listener;

    public WiFiDirectBroadcastReceiver(OnReceiveListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            listener.onStateChanged();
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            listener.onPeersChanged();
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            listener.onConnectionChanged();
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            listener.onThisDeviceChanged();
        }
    }

    public static interface OnReceiveListener {
        void onStateChanged();

        void onPeersChanged();

        void onConnectionChanged();

        void onThisDeviceChanged();
    }
}
