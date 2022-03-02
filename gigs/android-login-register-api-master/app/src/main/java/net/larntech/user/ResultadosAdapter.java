package net.larntech.user;

import android.content.Context;
import android.util.Log;
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

public class ResultadosAdapter extends RecyclerView.Adapter<ResultadosAdapter.ResultadosAdapterVh> implements Filterable {

    public List<ResultadosResponse> resultadosResponsesList = new ArrayList<>();
    public List<ResultadosResponse> getResultadosResponsesFilter = new ArrayList<>();
    public Context context;
    public ResultadosResClickListener resultadosResClickListener;

    public ResultadosAdapter(List<ResultadosResponse> resultadosResponses, Context context, ResultadosResClickListener resultadosResClickListener){
        this.resultadosResponsesList = resultadosResponses;
        this.getResultadosResponsesFilter = resultadosResponses;
        this.context = context;
        this.resultadosResClickListener = resultadosResClickListener;
    }




    public interface ResultadosResClickListener{
        void selectedResult(ResultadosResponse resultadosResponse);
    }




    @NonNull
    @Override
    public ResultadosAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
        return new ResultadosAdapterVh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultadosAdapter.ResultadosAdapterVh holder, int position) {

        ResultadosResponse resultadosResponse = resultadosResponsesList.get(position);
        String fecRegistro = resultadosResponse.getFec_registro();
        String laboratorio = resultadosResponse.getLaboratorio();
        String estadoOrden = resultadosResponse.getEstado_orden();

        if(fecRegistro != null) {
            holder.tvFecRegistro.setText(formateDateFromstring("dd/MM/YYYY hh:mm:ss", "dd/MM/YYYY", fecRegistro));
        }else{
            holder.tvFecRegistro.setText("");
        }
        holder.tvLaboratorio.setText(laboratorio);
        holder.tvEstadoOrden.setText(estadoOrden);

        holder.itemView.setOnClickListener(v -> resultadosResClickListener.selectedResult(resultadosResponse));

    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            assert parsed != null;
            outputDate = df_output.format(parsed);

        } catch (ParseException ignored) {
        }

        return outputDate;

    }

    @Override
    public int getItemCount() {
        return resultadosResponsesList.size();
    }

    public static class ResultadosAdapterVh extends RecyclerView.ViewHolder {

        private final TextView tvFecRegistro;
        private final TextView tvLaboratorio;
        private final TextView tvEstadoOrden;

        public ResultadosAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvFecRegistro = itemView.findViewById(R.id.tvFecRegistro);
            tvLaboratorio = itemView.findViewById(R.id.tvLaboratorio);
            tvEstadoOrden = itemView.findViewById(R.id.tvEstadoOrden);
        }
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.values = getResultadosResponsesFilter;
                    filterResults.count = getResultadosResponsesFilter.size();
                }else{
                    String searchStr = constraint.toString().toLowerCase();
                    List<ResultadosResponse> resultadosResponses = new ArrayList<>();
                    for(ResultadosResponse resultadosResponse: getResultadosResponsesFilter){
                        if(resultadosResponse.getNum_solicitud().toLowerCase().contains(searchStr) || resultadosResponse.getFec_registro().toLowerCase().contains(searchStr) || resultadosResponse.getEstado_orden().toLowerCase().contains(searchStr) || resultadosResponse.getLaboratorio().toLowerCase().contains(searchStr)){
                            resultadosResponses.add(resultadosResponse);
                        }
                    }

                    filterResults.values = resultadosResponses;
                    filterResults.count = resultadosResponses.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                resultadosResponsesList = (List<ResultadosResponse>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }


}