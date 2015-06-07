package indexer.com.portfolio.utils;

/**
 * Created by indexer on 6/7/15.
 */

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChecker {
  public static NetworkInfo mobile;
  public static NetworkInfo wifi;

  public static boolean isNetworkConnected(ConnectivityManager connectivityManager) {
    boolean isConnected = false;

    mobile = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
    if (mobile.isAvailable() && mobile.isAvailable()) {
      isConnected = true;
    }

    wifi = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
    if (wifi.isAvailable() && wifi.isConnected()) {
      isConnected = true;
    }
    return isConnected;
  }
}
