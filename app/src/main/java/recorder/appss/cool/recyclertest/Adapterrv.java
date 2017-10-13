package recorder.appss.cool.recyclertest;

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

import java.util.ArrayList;
import java.util.List;

import recorder.appss.cool.model.Match;

/**
 * Created by work on 29/09/2017.
 */

public class Adapterrv  extends RecyclerView.Adapter<Adapterrv.Myviewholder> {
    List<Match> ks= new ArrayList<Match>();
    ImageLoader imageLoader;
    public  Adapterrv(List<Match> k, ImageLoader imageLoader1)
    {
ks.addAll(k);
         imageLoader=imageLoader1;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrv,parent,false);
Myviewholder mvh= new Myviewholder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, int position) {
    //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
        holder.tx.setText(ks.get(position).getCompetition().getName());
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(true)
                .build();

        Picasso.with( holder.im.getContext())
                .load(ks.get(position).getCompetition().getFlagUrl())
                .fit()
                .transform(transformation)
                .into(holder.im);

    }

    @Override
    public int getItemCount() {
        return ks.size();
    }

    public void updateAnswers(List<Match> items) {
        ks = items;
        Log.d("siiiiiiiiize",""+ks.size());
        notifyDataSetChanged();
    }
    public class Myviewholder extends RecyclerView.ViewHolder{

         TextView tx;
        ImageView im;
        public Myviewholder(View itemView) {
            super(itemView);
            tx=(TextView)itemView.findViewById(R.id.txtcard);
            im=(ImageView) itemView.findViewById(R.id.imgcard);

        }
    }
}
