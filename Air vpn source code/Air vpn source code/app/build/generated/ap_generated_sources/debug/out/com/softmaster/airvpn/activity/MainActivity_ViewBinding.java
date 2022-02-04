// Generated code from Butter Knife. Do not modify!
package com.softmaster.airvpn.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.softmaster.airvpn.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view7f0a008f;

  private View view7f0a00b6;

  private View view7f0a0225;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.connect_btn, "field 'vpn_connect_btn' and method 'onClick'");
    target.vpn_connect_btn = Utils.castView(view, R.id.connect_btn, "field 'vpn_connect_btn'", ImageView.class);
    view7f0a008f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.main_toolbar, "field 'toolbar'", Toolbar.class);
    target.selectedServerImage = Utils.findRequiredViewAsType(source, R.id.vpn_country_image, "field 'selectedServerImage'", ImageView.class);
    target.selectedServerName = Utils.findRequiredViewAsType(source, R.id.vpn_country_name, "field 'selectedServerName'", TextView.class);
    target.uploading_speed_textview = Utils.findRequiredViewAsType(source, R.id.uploading_speed, "field 'uploading_speed_textview'", TextView.class);
    target.downloading_speed_textview = Utils.findRequiredViewAsType(source, R.id.downloading_speed, "field 'downloading_speed_textview'", TextView.class);
    target.vpn_connection_time = Utils.findRequiredViewAsType(source, R.id.vpn_connection_time, "field 'vpn_connection_time'", TextView.class);
    target.vpn_connection_time_text = Utils.findRequiredViewAsType(source, R.id.vpn_connection_time_text, "field 'vpn_connection_time_text'", TextView.class);
    view = Utils.findRequiredView(source, R.id.drawer_opener, "field 'Drawer_opener_image' and method 'OpenDrawer'");
    target.Drawer_opener_image = Utils.castView(view, R.id.drawer_opener, "field 'Drawer_opener_image'", ImageView.class);
    view7f0a00b6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.OpenDrawer(p0);
      }
    });
    target.timerTextView = Utils.findRequiredViewAsType(source, R.id.tv_timer, "field 'timerTextView'", TextView.class);
    target.connection_btn_block = Utils.findRequiredViewAsType(source, R.id.connection_btn_block, "field 'connection_btn_block'", RelativeLayout.class);
    target.second_elipse = Utils.findRequiredViewAsType(source, R.id.second_elipse, "field 'second_elipse'", RelativeLayout.class);
    target.pulsator = Utils.findRequiredViewAsType(source, R.id.pulsator, "field 'pulsator'", PulsatorLayout.class);
    target.rate_win = Utils.findRequiredViewAsType(source, R.id.rate_win, "field 'rate_win'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.vpn_select_country, "method 'onClick'");
    view7f0a0225 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.vpn_connect_btn = null;
    target.toolbar = null;
    target.selectedServerImage = null;
    target.selectedServerName = null;
    target.uploading_speed_textview = null;
    target.downloading_speed_textview = null;
    target.vpn_connection_time = null;
    target.vpn_connection_time_text = null;
    target.Drawer_opener_image = null;
    target.timerTextView = null;
    target.connection_btn_block = null;
    target.second_elipse = null;
    target.pulsator = null;
    target.rate_win = null;

    view7f0a008f.setOnClickListener(null);
    view7f0a008f = null;
    view7f0a00b6.setOnClickListener(null);
    view7f0a00b6 = null;
    view7f0a0225.setOnClickListener(null);
    view7f0a0225 = null;
  }
}
