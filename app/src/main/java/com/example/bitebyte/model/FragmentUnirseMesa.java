package com.example.bitebyte.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;

public class FragmentUnirseMesa extends Fragment {

    public FragmentUnirseMesa() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unirse_mesa, container, false);
    }
}
