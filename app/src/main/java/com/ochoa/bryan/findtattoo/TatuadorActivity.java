package com.ochoa.bryan.findtattoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ochoa.bryan.findtattoo.databinding.ActivityTatuadorBinding;
import org.jetbrains.annotations.NotNull;


public class TatuadorActivity extends AppCompatActivity {
    private ActivityTatuadorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTatuadorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String id = getIntent().getStringExtra("IDTATUADOR");
        FirebaseFirestore.getInstance().collection("Tatuadores")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                binding.tvNombre.setText(documentSnapshot.get("nombre").toString());
                                binding.tvDireccion.setText(documentSnapshot.get("direccion").toString());
                                binding.tvCity.setText(documentSnapshot.get("ciudad").toString());
                                binding.tvTelefono.setText(documentSnapshot.get("telefono").toString());
                                binding.tvDescripcion.setText(documentSnapshot.get("descripcion").toString());

                                Glide.with(binding.ivTatuador.getContext())
                                        .load(documentSnapshot.get("url"))
                                        .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                                        .circleCrop()
                                        .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                                        .into(binding.ivTatuador);

                                binding.tvTelefono.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        String tel = documentSnapshot.get("telefono").toString();
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: " + tel));
                                        startActivity(intent);
                                    }
                                });

                                binding.tvDireccion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        GeoPoint docuGeo = documentSnapshot.getGeoPoint("localizacion");
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo: " + docuGeo.getLatitude() + ", " + docuGeo.getLongitude()));
                                        startActivity(intent);
                                    }
                                });
                                binding.ivInstagram.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String url = documentSnapshot.get("instagram").toString();
                                        Uri uri = Uri.parse(url);
                                        Intent instagram = new Intent(Intent.ACTION_VIEW, uri);
                                        instagram.setPackage("com.instagram.android");
                                        try {
                                            startActivity(instagram);
                                        } catch (ActivityNotFoundException e) {
                                            e.printStackTrace();
                                            Toast.makeText(TatuadorActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    }
                });
    }
}