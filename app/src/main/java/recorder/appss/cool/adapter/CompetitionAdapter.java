package recorder.appss.cool.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import recorder.appss.cool.model.Competition;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 29/09/2017.
 */

public class CompetitionAdapter extends RecyclerView.Adapter<CompetitionAdapter.Myviewholder> {
    LinkedHashMap<Competition, String> ks = new LinkedHashMap<Competition, String>();
    ImageLoader imageLoader;

    public CompetitionAdapter(LinkedHashMap<Competition, String> k, ImageLoader imageLoader1) {
        ks.putAll(k);
        imageLoader = imageLoader1;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrv, parent, false);
        Myviewholder mvh = new Myviewholder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {
        //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
        Set<Map.Entry<Competition, String>> mapSet = ks.entrySet();
        Map.Entry<Competition, String> element = (Map.Entry<Competition, String>) mapSet.toArray()[position];


        Competition c = element.getKey();
        holder.tx.setText(c.getName() + "   " + element.getValue());
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(true)
                .build();

        Picasso.with(holder.im.getContext())
                .load(c.getFlagUrl())
                .fit()
                .transform(transformation)
                .into(holder.im);

    }

    public Object getElementByIndex(LinkedHashMap map, int index) {
        return map.get((map.entrySet().toArray())[index]);
    }

    @Override
    public int getItemCount() {
        return ks.size();
    }

    public void updateAnswers(LinkedHashMap<Competition, String> items) {
        ks = items;
        Log.d("siiiiiiiiize", "" + ks.size());
        notifyDataSetChanged();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView tx;
        ImageView im;

        public Myviewholder(View itemView) {
            super(itemView);
            tx = (TextView) itemView.findViewById(R.id.txtcard);
            im = (ImageView) itemView.findViewById(R.id.imgcard);

        }
    }
}
