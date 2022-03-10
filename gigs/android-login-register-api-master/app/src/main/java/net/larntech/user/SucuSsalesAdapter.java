package net.larntech.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SucuSsalesAdapter extends RecyclerView.Adapter<SucuSsalesAdapter.SucuSsalesAdapterVh> implements Filterable {

    public List<SucuSsalesResponse> sucuSsalesResponses = new ArrayList<>();
    public List<SucuSsalesResponse> getsucuSsalesResponsesFilter = new ArrayList<>();
    public Context context;
    public SucuSsalesClickListener sucuSsalesClickListener;

    public SucuSsalesAdapter(List<SucuSsalesResponse> sucuSsalesResponses, Context context, SucuSsalesClickListener sucuSsalesClickListener){
        this.sucuSsalesResponses = sucuSsalesResponses;
        this.getsucuSsalesResponsesFilter = sucuSsalesResponses;
        this.context = context;
        this.sucuSsalesClickListener = sucuSsalesClickListener;
    }




    public interface SucuSsalesClickListener{
        void selectedResult(SucuSsalesResponse sucuSsalesResponse);
    }




    @NonNull
    @Override
    public SucuSsalesAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_sucessales,parent,false);
        return new SucuSsalesAdapterVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SucuSsalesAdapter.SucuSsalesAdapterVh holder, int position) {


        SucuSsalesResponse sucuSsalesResponse = sucuSsalesResponses.get(position);
        String nomSucursal = sucuSsalesResponse.getNom_sucursal();
        String dscHorario = sucuSsalesResponse.getDsc_horario();
        String dscDireccion = sucuSsalesResponse.getDsc_direccion();
        String numTelefono = sucuSsalesResponse.getNum_telefono();
        String dscCorreo = sucuSsalesResponse.getDsc_correo();

        holder.tvNomSucursal.setText(nomSucursal);
        holder.tvDscHorario.setText(dscHorario);
        holder.tvDscDireccion.setText(dscDireccion);
        holder.tvNumTelefono.setText(numTelefono);
        holder.tvDscCorreo.setText(dscCorreo);


        holder.itemView.setOnClickListener(v -> sucuSsalesClickListener.selectedResult(sucuSsalesResponse));

    }


    @Override
    public int getItemCount() {
        return sucuSsalesResponses.size();
    }

    public static class SucuSsalesAdapterVh extends RecyclerView.ViewHolder {

        private final TextView tvNomSucursal;
        private final TextView tvDscHorario;
        private final TextView tvDscDireccion;
        private final TextView tvNumTelefono;
        private final TextView tvDscCorreo;

        public SucuSsalesAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvNomSucursal = itemView.findViewById(R.id.tvNomSucursal);
            tvDscHorario = itemView.findViewById(R.id.tvDscHorario);
            tvDscDireccion = itemView.findViewById(R.id.tvDscDireccion);
            tvNumTelefono = itemView.findViewById(R.id.tvNumTelefono);
            tvDscCorreo = itemView.findViewById(R.id.tvDscCorreo);
        }
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = getsucuSsalesResponsesFilter;
                    filterResults.count = getsucuSsalesResponsesFilter.size();
                }else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<SucuSsalesResponse> sucuSsalesResponses = new ArrayList<>();
                    for(SucuSsalesResponse sucuSsalesResponse: getsucuSsalesResponsesFilter){
                        if(sucuSsalesResponse.getNom_sucursal().toLowerCase().contains(searchStr)

                                || sucuSsalesResponse.getDsc_correo().toLowerCase().contains(searchStr)
                                || sucuSsalesResponse.getDsc_direccion().toLowerCase().contains(searchStr)

                        ){
                            sucuSsalesResponses.add(sucuSsalesResponse);
                        }
                    }

                    filterResults.values = sucuSsalesResponses;
                    filterResults.count = sucuSsalesResponses.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                sucuSsalesResponses = (List<SucuSsalesResponse>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }


}