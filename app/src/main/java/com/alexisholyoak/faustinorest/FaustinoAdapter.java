package com.alexisholyoak.faustinorest;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FaustinoAdapter extends RecyclerView.Adapter<FaustinoAdapter.FaustinoViewHolder> {

    public  class FaustinoViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        CardView cardView;
        TextView text1,text2;
        FaustinoViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            cardView=(CardView) view.findViewById(R.id.FaustinoCard);
            text1=(TextView)view.findViewById(R.id.txtUsuario);
            text2=(TextView)view.findViewById(R.id.txtFecha);
        }
        @Override
        public void onClick(View v) {
            // The user may not set a click listener for list items, in which case our listener
            // will be null, so we need to check for this
            if (mOnEntryClickListener != null) {
                mOnEntryClickListener.onEntryClick(v, getLayoutPosition());
            }
        }
    }
    private List<Faustino> mFaustinos;
    private Context mContext;
    public FaustinoAdapter(Context context,List<Faustino> FaustinoArrayList){
        mContext=context;
        mFaustinos=FaustinoArrayList;
    }
    @Override
    public FaustinoAdapter.FaustinoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.faustino_card,parent,false);
        return new FaustinoAdapter.FaustinoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FaustinoAdapter.FaustinoViewHolder holder, int position) {
        Faustino Faustino=mFaustinos.get(position);
        String usuario=Faustino.getIdUsuario();
        String fecha=Faustino.getFRegistro();
        holder.text1.setText(usuario);
        holder.text2.setText(fecha);
    }

    @Override
    public int getItemCount() {
        return mFaustinos.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private FaustinoAdapter.OnEntryClickListener mOnEntryClickListener;

    public interface OnEntryClickListener {
        void onEntryClick(View view, int position);
    }

    public void setOnEntryClickListener(FaustinoAdapter.OnEntryClickListener onEntryClickListener) {
        mOnEntryClickListener = onEntryClickListener;
    }
}