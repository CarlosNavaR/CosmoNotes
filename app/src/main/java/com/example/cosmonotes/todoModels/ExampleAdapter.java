package com.example.cosmonotes.todoModels;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cosmonotes.R;
import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    public ArrayList<elemento> mElementos;
    public ArrayList<elemento> mElementos_Checked;
    private ArrayList<todo_groups> mGrupos;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnArrowClick(int position);
        void OnCheckClick(int position);
        void OnUncheckClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener =listener;
    }


    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        // Por grupo
        public TextView mTV_grouptitle,mTV_arrow;
        public LinearLayout mLL_separator;

        // para constructor
        public RecyclerView RV_uc,RV_ch;
        private adapter_item AI_uc;
        private Adapter_item_check AI_ch;
        public RecyclerView.LayoutManager LLM_uc;
        public RecyclerView.LayoutManager LLM_ch;



        public ExampleViewHolder(View itemView, OnItemClickListener listener) {
            super( itemView );
            mLL_separator = itemView.findViewById(R.id.LL_group_lineSeparator);
            mTV_grouptitle = itemView.findViewById(R.id.tv_GroupTitle );
            mTV_arrow = itemView.findViewById(R.id.group_arrow);
            RV_uc = itemView.findViewById( R.id.nested_rv );
            RV_ch = itemView.findViewById( R.id.nested_rv_done );
            LLM_uc = new LinearLayoutManager(itemView.getContext());
            LLM_ch = new LinearLayoutManager(itemView.getContext());


            mTV_grouptitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnArrowClick(position);
                        }
                    }
                }
            });
            mTV_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnArrowClick(position);
                        }
                    }
                }
            });
            RV_uc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnUncheckClick(position);
                        }
                    }
                }
            });
            RV_ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnCheckClick(position);
                        }
                    }
                }
            });


        }
    }
    public ExampleAdapter(ArrayList<todo_groups> listaGrupos){
        mGrupos = listaGrupos;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        todo_groups grupoActual = mGrupos.get( position );
        mElementos = grupoActual.getListaEl_unchecked();
        mElementos_Checked = grupoActual.getListaEl_checked();
        holder.mTV_grouptitle.setText( grupoActual.getTitulo_grupo() );
        if (grupoActual.isAbierto()){
            holder.mTV_arrow.setText(R.string.fa_caret_up_solid);
            holder.RV_uc.setVisibility(View.VISIBLE);
            holder.RV_ch.setVisibility(View.VISIBLE);
            if(mElementos_Checked.size() !=  0){ holder.mLL_separator.setVisibility(View.VISIBLE);}
            if(mElementos_Checked.size() ==  0){ holder.mLL_separator.setVisibility(View.GONE );}
        }else {
            holder.mTV_arrow.setText(R.string.fa_caret_down_solid);
            holder.RV_uc.setVisibility(View.GONE);
            holder.RV_ch.setVisibility(View.GONE);
            if(mElementos_Checked.size() !=  0){ holder.mLL_separator.setVisibility(View.GONE);}
            if(mElementos_Checked.size() ==  0){ holder.mLL_separator.setVisibility(View.GONE );}
        }
        buildRecyclerView( holder , position);

    }

    public void buildRecyclerView(ExampleViewHolder holder,int groupIndex){
        holder.AI_uc = new adapter_item(mElementos);
        holder.RV_uc.setLayoutManager( holder.LLM_uc);
        holder.RV_uc.setAdapter( holder.AI_uc);
        holder.AI_ch = new Adapter_item_check(mElementos_Checked);
        holder.RV_ch.setLayoutManager( holder.LLM_ch);
        holder.RV_ch.setAdapter( holder.AI_ch);
        }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_group, parent,false );
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public int getItemCount() {
        return mGrupos.size();
    }


}