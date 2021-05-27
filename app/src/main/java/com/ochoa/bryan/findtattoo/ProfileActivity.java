package com.ochoa.bryan.findtattoo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ochoa.bryan.findtattoo.adapters.TatuadorAdapter;
import com.ochoa.bryan.findtattoo.model.TatuadoresModel;
import com.ochoa.bryan.findtattoo.ui.AuthActivity;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private SearchView searchView;
    private TatuadorAdapter tatuadorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.rvTatuadores);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        searchView = findViewById(R.id.search);

        Query query = FirebaseFirestore.getInstance().collection("Tatuadores");
        FirestoreRecyclerOptions<TatuadoresModel> options = new FirestoreRecyclerOptions.Builder<TatuadoresModel>()
                .setQuery(query, TatuadoresModel.class)
                .build();
        tatuadorAdapter = new TatuadorAdapter(options);
        mFirestoreList.setAdapter(tatuadorAdapter);
        tatuadorAdapter.setOnItemClickListener(new TatuadorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                TatuadoresModel tatuadoresModel = documentSnapshot.toObject(TatuadoresModel.class);
                String id = documentSnapshot.getId();
                Intent i = new Intent(ProfileActivity.this, TatuadorActivity.class);
                i.putExtra("IDTATUADOR", id);
                startActivity(i);

            }
        });

    } // END OF ONCREATE

    @Override
    protected void onStart() {
        super.onStart();
        tatuadorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tatuadorAdapter.stopListening();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCity(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchCity(newText);
                tatuadorAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_CloseSession:
                btnCloseSession();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * con el metodo checkUser comprobamos que user esta actualmente logeado.
     */
    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, AuthActivity.class));
        } else {
            String email = firebaseUser.getEmail();
            //set email
            //binding.emailTv.setText(email);
        }
    }

    /**
     * con el metodo btnCloseSession cerramos la sesión del usuario y volvemos a la actividad anterior donde hay que hacer login o register.
     */
    public void btnCloseSession() {
        checkUser();
        firebaseAuth.signOut();
        Toast.makeText(ProfileActivity.this, R.string.alert_closeSession, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, AuthActivity.class));
        finish();

    }

    /**
     *
     * @param str
     * con el me searchCity buscamos por el recycler view y la bd que tenemos en firestore los
     * items que contienen el Str(ciudad) que buscamos
     */
    private void searchCity(String str) {
        Query query = FirebaseFirestore.getInstance().collection("Tatuadores").orderBy("ciudad").startAt(str).endAt(str+"~");
        FirestoreRecyclerOptions<TatuadoresModel> options = new FirestoreRecyclerOptions.Builder<TatuadoresModel>()
                .setQuery(query, TatuadoresModel.class)
                .build();
        tatuadorAdapter = new TatuadorAdapter(options);
        tatuadorAdapter.startListening();
        mFirestoreList.setAdapter(tatuadorAdapter);
        tatuadorAdapter.setOnItemClickListener(new TatuadorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                TatuadoresModel tatuadoresModel = documentSnapshot.toObject(TatuadoresModel.class);
                String id = documentSnapshot.getId();
                Intent i = new Intent(ProfileActivity.this, TatuadorActivity.class);
                i.putExtra("IDTATUADOR", id);
                startActivity(i);

            }
        });
    }
}