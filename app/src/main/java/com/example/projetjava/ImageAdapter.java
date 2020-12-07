package com.example.projetjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetjava.fragment.Ajouter;
import com.example.projetjava.fragment.Detail;
import com.example.projetjava.fragment.Liste;
import com.squareup.picasso.Picasso;
import java.util.List;
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Livre> mUploads;
    public  Liste liste = new Liste();
    public ImageAdapter(Context context, List<Livre> uploads) {
        mContext = context;
        mUploads = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.card_view, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Livre uploadCurrent = mUploads.get(position);
       // Log.d("IMAGE",uploadCurrent.getImage());
        holder.autheur.setText("Auteur: ");
        holder.genre.setText("Genre: ");
      holder.textViewName.setText(uploadCurrent.getNom()+" "+uploadCurrent.getPrenom());
      holder.textViewTitle.setText(uploadCurrent.getTitre());
      holder.textViewGenre.setText(uploadCurrent.getGenre());
      /* holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity)view.getContext();
                Detail fragment = new Detail();
                //this.setVisibility(View.INVISIBLE);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.rec,fragment).commit();
            }


        });*/
        //centerInside
        Picasso.get()
                .load(uploadCurrent.getImage())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return mUploads.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder {
       public TextView textViewName,textViewTitle,textViewGenre,genre,autheur;
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            autheur = itemView.findViewById(R.id.text_view_auteur);
            genre = itemView.findViewById(R.id.text_view_gen);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewGenre = itemView.findViewById(R.id.text_view_genre);
            textViewTitle = itemView.findViewById(R.id.text_view_titre);
            imageView = itemView.findViewById(R.id.image_view);
          /*  imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity)view.getContext();
                    Detail fragment = new Detail();
                    //this.setVisibility(View.INVISIBLE);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.Liste,fragment).commit();
                }


            });*/
           /* imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    NavHostFragment.findNavController(Liste.this)
                            .navigate(R.id.action_ajout_to_liste);
                }


            });*/
        }
    }
}