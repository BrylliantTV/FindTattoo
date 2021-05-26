package com.ochoa.bryan.findtattoo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.ochoa.bryan.findtattoo.R;
import com.ochoa.bryan.findtattoo.model.TatuadoresModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class TatuadorAdapter extends FirestoreRecyclerAdapter<TatuadoresModel, TatuadorAdapter.myViewHolder > {
    private OnItemClickListener listener;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TatuadorAdapter(@NonNull FirestoreRecyclerOptions<TatuadoresModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TatuadorAdapter.myViewHolder holder, int position, @NonNull TatuadoresModel model) {

        holder.list_name.setText(model.getNombre());
        holder.list_city.setText(model.getCiudad());
        holder.estilo1.setText(model.getEstilo1());
        holder.estilo2.setText(model.getEstilo2());
        holder.estilo3.setText(model.getEstilo3());
        Glide.with(holder.img.getContext())
                .load(model.getUrl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tatuadores, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        private TextView list_name;
        private TextView list_city;
        private CircleImageView img;
        private TextView estilo1, estilo2, estilo3;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.tvName);
            list_city = itemView.findViewById(R.id.tvCity);
            img = itemView.findViewById(R.id.imgTatuador);
            estilo1 = itemView.findViewById(R.id.tvStyle1);
            estilo2 = itemView.findViewById(R.id.tvStyle2);
            estilo3 = itemView.findViewById(R.id.tvStyle3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
