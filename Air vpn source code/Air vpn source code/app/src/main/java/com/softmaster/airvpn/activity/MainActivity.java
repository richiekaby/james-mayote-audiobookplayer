package com.softmaster.airvpn.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.VpnService;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.anchorfree.reporting.TrackingConstants;
import com.anchorfree.sdk.UnifiedSDK;
import com.anchorfree.vpnsdk.callbacks.Callback;
import com.anchorfree.vpnsdk.callbacks.CompletableCallback;
import com.anchorfree.vpnsdk.exceptions.VpnException;
import com.anchorfree.vpnsdk.vpnservice.VPNState;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.material.navigation.NavigationView;
import com.infideap.drawerbehavior.AdvanceDrawerLayout;
import com.pepperonas.materialdialog.MaterialDialog;
import com.softmaster.airvpn.Config;
import com.softmaster.airvpn.R;
import com.softmaster.airvpn.adapter.PrefManager;
import com.softmaster.airvpn.fragments.ServersFragment;
import com.softmaster.airvpn.model.Server;
import com.softmaster.airvpn.utils.CheckInternetConnection;
import com.softmaster.airvpn.utils.Converter;
import com.softmaster.airvpn.utils.SharedPreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.blinkt.openvpn.OpenVpnApi;
import de.blinkt.openvpn.core.OpenVPNService;
import de.blinkt.openvpn.core.OpenVPNThread;
import de.blinkt.openvpn.core.VpnStatus;
import es.dmoral.toasty.Toasty;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ServersFragment.RegionChooserInterface {

    private String selectedCountry = "";
    private SharedPreference preference;
    private Dialog RateDialog;


    @BindView(R.id.connect_btn)
    ImageView vpn_connect_btn;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.vpn_country_image)
    ImageView selectedServerImage;

    @BindView(R.id.vpn_country_name)
    TextView selectedServerName;

    @BindView(R.id.uploading_speed)
    TextView uploading_speed_textview;

    @BindView(R.id.downloading_speed)
    TextView downloading_speed_textview;

    @BindView(R.id.vpn_connection_time)
    TextView vpn_connection_time;

    @BindView(R.id.vpn_connection_time_text)
    TextView vpn_connection_time_text;

    @BindView(R.id.drawer_opener)
    ImageView Drawer_opener_image;

    @BindView(R.id.tv_timer)
    TextView timerTextView;

    @BindView(R.id.connection_btn_block)
    RelativeLayout connection_btn_block;

    @BindView(R.id.second_elipse)
    RelativeLayout second_elipse;

    @BindView(R.id.pulsator)
    PulsatorLayout pulsator;

    @BindView(R.id.rate_win)
    ImageView rate_win;

    ImageView cursor;


    private Server server;
    private CheckInternetConnection connection;

    private OpenVPNThread vpnThread = new OpenVPNThread();
    private OpenVPNService vpnService = new OpenVPNService();

    boolean vpnStart = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        AdvanceDrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawer.setViewScale(Gravity.START, 0.9f);
        drawer.setRadius(Gravity.START, 35);
        drawer.setViewElevation(Gravity.START, 20);

        setupDrawer();
        initAll();
    }

    public void initAll(){
        preference = new SharedPreference(this);
        server = preference.getServer();

        if (server != null ) {
            selectedServerName.setText(server.getCountry());
            Glide.with(this)
                    .load(server.getFlagUrl())
                    .into(selectedServerImage);
        }
        else {
            selectedCountry = "no";
            selectedServerName.setText(selectedCountry);
         }




        connection = new CheckInternetConnection();

        isServiceRunning();
        VpnStatus.initLogCache(this.getCacheDir());


    }


    /**
     * @param v: click listener view
     */
    @OnClick({R.id.connect_btn, R.id.vpn_select_country})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_btn:

                if (vpnStart) {

                    new MaterialDialog.Builder(this)
                            .title("Confirmation")
                            .message("Are You Sure to Disconnect The AirVPN")
                            .positiveText("Disconnect")
                            .negativeText("CANCEL")
                            .positiveColor(R.color.red)
                            .negativeColor(R.color.color_btn)
                            .buttonCallback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    confirmDisconnect();
                                }

                                @Override
                                public void onNegative(MaterialDialog dialog) {
                                    super.onNegative(dialog);
                                }
                            })
                            .show();

                } else {
                    prepareVpn();
                }
                break;

            case R.id.vpn_select_country:
                if (!Config.isVPNConnected) {
                    ServersFragment.newInstance().show(getSupportFragmentManager(), ServersFragment.TAG);
                } else {
                    Toasty.error(this, "Please disconnect the VPN first", Toast.LENGTH_SHORT,true).show();
                }

                break;

        }
    }




    private void setupDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, 0, 0);//R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void isServiceRunning() {
        setStatus(vpnService.getStatus());
    }



    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("connectionState"));

        if (server == null) {
            server = preference.getServer();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }


    /**
     * Status change with corresponding vpn connection status
     * @param connectionState
     */
    public void setStatus(String connectionState) {
        if (connectionState!= null)
            switch (connectionState) {
                case "DISCONNECTED":
                    vpnStart = false;
                    vpnService.setDefaultStatus();
                    uploading_speed_textview.setText(R.string._0_mbps);
                    downloading_speed_textview.setText(R.string._0_mbps);
                    vpn_connection_time.setText(R.string.disconnected);
                    vpn_connection_time.setTextColor(getResources().getColor(R.color.yellow_color));
                    Config.isVPNConnected = false;
                    vpn_connect_btn.setImageResource(R.drawable.main_icon);
                    connection_btn_block.setBackground(ContextCompat.getDrawable(this,R.drawable.ellipse_1));
                    second_elipse.setBackground(ContextCompat.getDrawable(this,R.drawable.ellipse_2));

                    break;
                case "CONNECTED":
                    Toasty.success(MainActivity.this, "VPN Connected Successfully!", Toast.LENGTH_SHORT, true).show();
                    vpnStart = true;// it will use after restart this activity
                    pulsator.stop();
                    vpn_connection_time.setText(R.string.connected);
                    vpn_connection_time.setTextColor(getResources().getColor(R.color.gnt_green));
                    connection_btn_block.setBackground(ContextCompat.getDrawable(this,R.drawable.ellipse_yes1));
                    second_elipse.setBackground(ContextCompat.getDrawable(this,R.drawable.ellipse_yes2));
                    vpn_connect_btn.setImageResource(R.drawable.main_icon);
                    Config.isVPNConnected = true;

                    break;
                case "WAIT":
                    vpn_connection_time.setTextColor(getResources().getColor(R.color.gnt_green));
                    vpn_connection_time.setText("waiting for server connection!!");
                    break;
                case "AUTH":
                    connection_btn_block.setBackground(ContextCompat.getDrawable(this,R.drawable.ellipse_con1));
                    second_elipse.setBackground(ContextCompat.getDrawable(this,R.drawable.ellipse_con2));
                    pulsator.start();
                    vpn_connection_time.setTextColor(getResources().getColor(R.color.gnt_green));
                    vpn_connection_time.setText("server authenticating!!");
                    break;
                case "RECONNECTING":
                    vpn_connection_time.setTextColor(getResources().getColor(R.color.gnt_green));
                    vpn_connection_time.setText("Reconnecting...");
                    break;
                case "NONETWORK":
                    vpn_connection_time.setTextColor(getResources().getColor(R.color.gnt_green));
                    vpn_connection_time.setText("No network connection");
                    break;
            }

    }




    /**
     * Receive broadcast message
     */
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                setStatus(intent.getStringExtra("state"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {

                String duration = intent.getStringExtra("duration");
                String lastPacketReceive = intent.getStringExtra("lastPacketReceive");
                String byteIn = intent.getStringExtra("byteIn");
                String byteOut = intent.getStringExtra("byteOut");

                if (duration == null) duration = "00:00:00";
                if (lastPacketReceive == null) lastPacketReceive = "0";
                if (byteIn == null) byteIn = " 0 mbps ";
                if (byteOut == null) byteOut = " 0 mbps ";
                updateConnectionStatus(duration, lastPacketReceive, byteIn, byteOut);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };



    /**
     * Update status UI
     * @param duration: running time
     * @param lastPacketReceive: last packet receive time
     * @param byteIn: incoming data
     * @param byteOut: outgoing data
     */
    public void updateConnectionStatus(String duration, String lastPacketReceive, String byteIn, String byteOut) {
        timerTextView.setText(duration);
        downloading_speed_textview.setText(byteIn);
        uploading_speed_textview.setText(byteOut);
    }

    /**
     * Show toast message
     * @param message: toast message
     */
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    /**
     * Start the VPN
     */
    private void startVpn() {
        try {
            // .ovpn file
            InputStream conf = this.getAssets().open(server.getOvpn());
            InputStreamReader isr = new InputStreamReader(conf);
            BufferedReader br = new BufferedReader(isr);
            String config = "";
            String line;

            while (true) {
                line = br.readLine();
                if (line == null) break;
                config += line + "\n";
            }

            br.readLine();
            OpenVpnApi.startVpn(this, config, server.getCountry(), server.getOvpnUserName(), server.getOvpnUserPassword());


        } catch (IOException | RemoteException e) {
            e.printStackTrace();
        }
    }



    /**
     * Show show disconnect confirm dialog
     */
    public void confirmDisconnect(){
                stopVpn();
    }



    /**
     * Stop vpn
     * @return boolean: VPN status
     */
    public boolean stopVpn() {
        try {
            vpnThread.stop();
            vpnStart = false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }



    /**
     * Prepare for vpn connect with required permission
     */
    private void prepareVpn() {
        if (!vpnStart) {
            if (getInternetStatus()) {

                // Checking permission for network monitor
                Intent intent = VpnService.prepare(this);

                if (intent != null) {
                    startActivityForResult(intent, 1);
                } else startVpn();//have already permission


            } else {

                // No internet connection available
                showToast("you have no internet connection !!");
            }

        } else if (stopVpn()) {

            // VPN is stopped, show a Toast message.
            showToast("Disconnect Successfully");
        }
    }

    /**
     * Internet connection status.
     */
    public boolean getInternetStatus() {
        return connection.netCheck(this);
    }





    /**
     * Taking permission for network access
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            //Permission granted, start the VPN
            startVpn();
        } else {
            showToast("Permission Deny !! ");
        }
    }


    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.drawer_opener)
    public void OpenDrawer(View v) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.openDrawer(GravityCompat.START);
    }

    @SuppressLint({"NonConstantResourceId", "ShowToast"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
        // Handle navigation view item clicks here.
        switch (menuitem.getItemId()) {
            case R.id.nav_upgrade:
//            upgrade application is available...
                if (!Config.isVPNConnected) {
                    ServersFragment.newInstance().show(getSupportFragmentManager(), ServersFragment.TAG);
                }
                else {
                    Toasty.error(this, "Please disconnect the VPN first", Toast.LENGTH_SHORT,true).show();
                }
                break;
            case R.id.nav_unlock:
//                showRewardedAd();
                break;
            case R.id.nav_helpus:
//            find help about the application
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.developer_email)});
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.help_to_improve_us_email_subject));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.help_to_improve_us_body));

                try {
                    startActivity(Intent.createChooser(intent, "send mail"));
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(this, "No mail app found!!!", Toast.LENGTH_SHORT);
                } catch (Exception ex) {
                    Toast.makeText(this, "Unexpected Error!!!", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.nav_rate:
//            rate application...
                if(cursor!=null){
                    cursor.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_in_out));
                }
                RateDialog.show();

                break;

            case R.id.nav_share:
//            share the application...
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "share app");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using this Free VPN App, it's provide all servers free https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    Toasty.success(this, "Error..", Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.nav_faq:
                startActivity(new Intent(this, Faq.class));
                break;

            case R.id.nav_about:
                showAboutDialog();
                break;

            case R.id.nav_policy:
                try {
                    Uri uri = Uri.parse(getResources().getString(R.string.privacy_policy_link)); // missing 'http://' will cause crashed
                    Intent intent_policy = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent_policy);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Toasty.error(this, "Please give a valid privacy policy URL.", Toast.LENGTH_SHORT,true).show();
                }
                break;
        }
        return true;
    }

    private void showAboutDialog() {

        Dialog about_dialog = new Dialog(this);
        about_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        about_dialog.setContentView(R.layout.dialog_about);
        about_dialog.setCancelable(true);
        about_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(about_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        about_dialog.findViewById(R.id.bt_close).setOnClickListener(v -> about_dialog.dismiss());

        about_dialog.show();
        about_dialog.getWindow().setAttributes(lp);
    }


    @Override
    public void onRegionSelected(Server item) {
        selectedServerName.setText(item.getCountry());
        Glide.with(this)
                .load(server.getFlagUrl())
                .into(selectedServerImage);
        selectedCountry = item.getCountry();
        showMessage("Reconnecting to VPN with " + selectedCountry);
        prepareVpn();

    }


    protected void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
