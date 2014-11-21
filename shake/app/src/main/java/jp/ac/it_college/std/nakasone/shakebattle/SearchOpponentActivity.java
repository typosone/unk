package jp.ac.it_college.std.nakasone.shakebattle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;


public class SearchOpponentActivity extends Activity
        implements WifiP2pManager.ChannelListener, WiFiDirectBroadcastReceiver.OnReceiveListener
        , DeviceListFragment.DeviceActionListener {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private IntentFilter filter;
    private BroadcastReceiver receiver;

    private DeviceListFragment deviceListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_opponent);

        if (savedInstanceState == null) {
            deviceListFragment = new DeviceListFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, deviceListFragment)
                    .commit();
        }

        filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(this);
        registerReceiver(receiver, filter);

        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("WiFiDirectBroadcastReceiver", "discoverPeers success");
            }

            @Override
            public void onFailure(int reason) {
                Log.d("WiFiDirectBroadcastReceiver", "discoverPeers failure");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onChannelDisconnected() {

    }

    /* implemented OnReceiveListener */
    @Override
    public void onStateChanged() {

    }

    @Override
    public void onPeersChanged() {
        manager.requestPeers(channel, deviceListFragment);
    }

    @Override
    public void onConnectionChanged() {

    }

    @Override
    public void onThisDeviceChanged() {

    }

    /* implemented DeviceActionListener */
    @Override
    public void connect(WifiP2pConfig config) {
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }
}
