package de.df.usb_host_demo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

/**
 * Created by didlich on 21.03.15.
 */
public class USBTransport {

  private static final String TAG = USBTransport.class.getSimpleName();



  private UsbDevice deviceFound = null;

  private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
  private PendingIntent mPermissionIntent;

  private Activity activity;
  private IMessageCreate message;

  public USBTransport(MainActivity mainActivity) {
    this.activity = (Activity) mainActivity;
    this.message = (IMessageCreate) mainActivity;
    this.activity.registerReceiver(mUsbDeviceReceiver, new IntentFilter(UsbManager.ACTION_USB_DEVICE_ATTACHED));
  }

  private final BroadcastReceiver mUsbDeviceReceiver =
      new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
          String action = intent.getAction();
          if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {

            deviceFound = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            String msg = "ACTION_USB_DEVICE_ATTACHED: \n" + deviceFound.toString();
            Log.d(TAG, msg);
            message.onMessage(msg);
          }
        }

      };

  private void checkDeviceInfoSkipDeviceSearching() {
    //called when ACTION_USB_DEVICE_ATTACHED,
    //device already found, skip device searching


    String i = deviceFound.toString() + "\n" +
               "DeviceID: " + deviceFound.getDeviceId() + "\n" +
               "DeviceName: " + deviceFound.getDeviceName() + "\n" +
               "DeviceClass: " + deviceFound.getDeviceClass() + " - "
               + translateDeviceClass(deviceFound.getDeviceClass()) + "\n" +
               "DeviceSubClass: " + deviceFound.getDeviceSubclass() + "\n" +
               "VendorID: " + deviceFound.getVendorId() + "\n" +
               "ProductID: " + deviceFound.getProductId() + "\n" +
               "InterfaceCount: " + deviceFound.getInterfaceCount();

    Log.d(TAG, "Device-Info: " + i);
    message.onMessage(i);
  }

  private String translateDeviceClass(int deviceClass){
    switch(deviceClass){
      case UsbConstants.USB_CLASS_APP_SPEC:
        return "Application specific USB class";
      case UsbConstants.USB_CLASS_AUDIO:
        return "USB class for audio devices";
      case UsbConstants.USB_CLASS_CDC_DATA:
        return "USB class for CDC devices (communications device class)";
      case UsbConstants.USB_CLASS_COMM:
        return "USB class for communication devices";
      case UsbConstants.USB_CLASS_CONTENT_SEC:
        return "USB class for content security devices";
      case UsbConstants.USB_CLASS_CSCID:
        return "USB class for content smart card devices";
      case UsbConstants.USB_CLASS_HID:
        return "USB class for human interface devices (for example, mice and keyboards)";
      case UsbConstants.USB_CLASS_HUB:
        return "USB class for USB hubs";
      case UsbConstants.USB_CLASS_MASS_STORAGE:
        return "USB class for mass storage devices";
      case UsbConstants.USB_CLASS_MISC:
        return "USB class for wireless miscellaneous devices";
      case UsbConstants.USB_CLASS_PER_INTERFACE:
        return "USB class indicating that the class is determined on a per-interface basis";
      case UsbConstants.USB_CLASS_PHYSICA:
        return "USB class for physical devices";
      case UsbConstants.USB_CLASS_PRINTER:
        return "USB class for printers";
      case UsbConstants.USB_CLASS_STILL_IMAGE:
        return "USB class for still image devices (digital cameras)";
      case UsbConstants.USB_CLASS_VENDOR_SPEC:
        return "Vendor specific USB class";
      case UsbConstants.USB_CLASS_VIDEO:
        return "USB class for video devices";
      case UsbConstants.USB_CLASS_WIRELESS_CONTROLLER:
        return "USB class for wireless controller devices";
      default: return "Unknown USB class!";

    }
  }

  public void onDestroy() {
    this.activity.unregisterReceiver(mUsbDeviceReceiver);
  }

}
