package com.example.bitebyte.model;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bitebyte.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.HashMap;

public class FragmentCrearMesa extends Fragment {

    private EditText editCodigoMesa;
    private Button btnCrearMesa;
    private DatabaseReference refMesas;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crear_mesa, container, false);

        editCodigoMesa = view.findViewById(R.id.editCodigoMesa);
        btnCrearMesa = view.findViewById(R.id.btnCrearMesa);

        refMesas = FirebaseDatabase.getInstance().getReference("mesas");

        btnCrearMesa.setOnClickListener(v -> crearMesa());

        return view;
    }

    private void crearMesa() {
        String codigo = editCodigoMesa.getText().toString().trim();

        if (TextUtils.isEmpty(codigo)) {
            Toast.makeText(getContext(), "Ingresa un código", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, Object> mesa = new HashMap<>();
        mesa.put("codigo", codigo);
        mesa.put("mesero", FirebaseAuth.getInstance().getCurrentUser().getUid());

        refMesas.child(codigo).setValue(mesa)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Mesa creada", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
