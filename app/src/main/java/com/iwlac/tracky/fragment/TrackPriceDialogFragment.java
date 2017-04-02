package com.iwlac.tracky.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.iwlac.tracky.ProductClickListener;
import com.iwlac.tracky.R;
import com.iwlac.tracky.activity.PriceCompareActivity;
import com.iwlac.tracky.adapter.TrackedProductAdapter;
import com.iwlac.tracky.firebasemanager.Database;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.apptik.widget.MultiSlider;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrackPriceDialogFragment extends DialogFragment {
    @BindView(R.id.btnTrack)
    Button btnTrack;
    @BindView(R.id.etPrice)
    EditText etPrice;

    private String itemId;


    public TrackPriceDialogFragment() {
        // Required empty public constructor
    }

    public static TrackPriceDialogFragment newInstance(String itemId) {
        TrackPriceDialogFragment fragment = new TrackPriceDialogFragment();
        Bundle args = new Bundle();
        args.putString("ID",itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString("ID");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_track_price_dialog, container, false);
        ButterKnife.bind(this, view);
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = Database.track(FirebaseInstanceId.getInstance().getToken(),itemId,Double.parseDouble(etPrice.getText().toString()));
                if (result){
                    dismiss();
                } else {
                    Snackbar.make(v,"Some error occur", Snackbar.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

}
