package lu.fisch.canze.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import lu.fisch.canze.R;
import lu.fisch.canze.activities.MainActivity;
import lu.fisch.canze.classes.Activity;
import lu.fisch.canze.classes.ActivityRegistry;


public class CostumFragment extends Fragment {

    public CostumFragment() {
        // Required empty public constructor
    }

    private static final int BUTTONCOUNT = 14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_costum, container, false);

        ActivityRegistry registry = ActivityRegistry.getInstance();

        for(int i=0; i<BUTTONCOUNT; i++)
        {
            int buttonId = getResources().getIdentifier("buttonC" + i, "id",MainActivity.getInstance().getApplicationContext().getPackageName());
            Button button = view.findViewById(buttonId);
            if(i<registry.size())
            {
                Activity a = registry.get(i);
                button.setText(a.getTitle());

                int drawableId = getResources().getIdentifier(a.getDrawable(), "id",MainActivity.getInstance().getApplicationContext().getPackageName());
                Drawable icon=this.getResources(). getDrawable(drawableId);
                button.setCompoundDrawablesWithIntrinsicBounds(icon,null,null,null);
                activateButton(view,buttonId,a.getClassOf());
            }
            // hide button
            else button.setVisibility(View.INVISIBLE);
        }


        // do stuff
        /*
        Button btnTag = new Button(this);
        btnTag.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        btnTag.setText("Button");
        btnTag.setId(some_random_id);
         */

        return view;
    }

    private void activateButton(View view, int buttonId, final Class<?> activityClass) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MainActivity.isSafe()) return;
                if (MainActivity.device == null) {
                    MainActivity.toast(MainActivity.TOAST_NONE, R.string.toast_AdjustSettings);
                    return;
                }
                MainActivity.getInstance().leaveBluetoothOn = true;
                Intent intent = new Intent(MainActivity.getInstance(), activityClass);
                CostumFragment.this.startActivityForResult(intent, MainActivity.LEAVE_BLUETOOTH_ON);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MainActivity.getInstance().onActivityResult(requestCode, resultCode, data);
    }
}