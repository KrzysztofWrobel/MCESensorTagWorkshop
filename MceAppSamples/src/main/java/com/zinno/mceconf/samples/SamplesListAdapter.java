package com.zinno.mceconf.samples;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.joanzapata.android.iconify.Iconify;
import com.zinno.sensortag.BleServiceBindingActivity;

import java.util.ArrayList;

public class SamplesListAdapter extends RecyclerView.Adapter<SamplesListEntryViewHolder> {
    //TODO if you want to you can set your device mac address here
    public static final String MY_FIRST_SENSOR_TAG_MAC = null;
    public static final String MY_SECOND_SENSOR_TAG_MAC = null;


    Context context;

    public SamplesListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SamplesListEntryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_sampleslist_entry, viewGroup, false);
        return new SamplesListEntryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SamplesListEntryViewHolder viewHolder, int position) {
        final Samples sample = Samples.values()[position];

        viewHolder.nameTextView.setText(sample.nameId);

        viewHolder.iconTextView.setText(sample.iconId);
        Iconify.addIcons(viewHolder.iconTextView);

        viewHolder.iconTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSampleClick(sample);
            }
        });
    }

    private void onSampleClick(Samples sample) {
        Intent intent = null;

        String myFirstSensorTagMac = MY_FIRST_SENSOR_TAG_MAC;
        BluetoothDevice firstDevice = LocalStorage.getDevice(context, 0); //Get first device from preferences
        if (firstDevice != null) {
            myFirstSensorTagMac = firstDevice.getAddress();
        }
        String mySecondSensorTagMac = MY_SECOND_SENSOR_TAG_MAC;
        BluetoothDevice secondDevice = LocalStorage.getDevice(context, 1); //Get second device from preferences
        if (secondDevice != null) {
            mySecondSensorTagMac = secondDevice.getAddress();
        }

        if (myFirstSensorTagMac == null) {
            //Try second device
            myFirstSensorTagMac = mySecondSensorTagMac;
        }

        if (myFirstSensorTagMac == null) {
            Toast.makeText(context, "You need to choose at least one Sensor Tag to use these samples. Go to settings.", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (sample) {
            case ACCELEROMETER:
                intent = new Intent(this.context, AccelerometerActivity.class);
                intent.putExtra(BleServiceBindingActivity.EXTRAS_DEVICE_NAME, "Dice Sensor");
                intent.putExtra(BleServiceBindingActivity.EXTRAS_DEVICE_ADDRESS, myFirstSensorTagMac);
                break;
            case BUTTON_GAME:
                intent = new Intent(this.context, ButtonsActivity.class);
                ArrayList<String> deviceNames1 = new ArrayList<>();
                deviceNames1.add("Player 1 pad");
                deviceNames1.add("Player 2 pad");
                ArrayList<String> deviceAddresses1 = new ArrayList<>();
                deviceAddresses1.add(myFirstSensorTagMac);
                deviceAddresses1.add(mySecondSensorTagMac);
                intent.putExtra(BleServiceBindingActivity.EXTRAS_DEVICE_NAMES, deviceNames1);
                intent.putExtra(BleServiceBindingActivity.EXTRAS_DEVICE_ADDRESSES, deviceAddresses1);
                break;
        }

        if (intent != null) {
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return Samples.values().length;
    }
}
