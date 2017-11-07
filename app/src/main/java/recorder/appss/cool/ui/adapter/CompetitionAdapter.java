package recorder.appss.cool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import recorder.appss.cool.model.Competition;
import recorder.appss.cool.ui.fragment.TabFragmentCompetitionList;
import recorder.appss.cool.ui.fragment.FragmentMatchsCompet;
import recorder.appss.cool.recyclertest.R;

/**
 * Created by work on 29/09/2017.
 */

public class CompetitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Competition> list_compt = new ArrayList<>();
    LinkedHashMap<Competition, String> ks = new LinkedHashMap<Competition, String>();

    TabFragmentCompetitionList ff;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    private int nbmatch_total, nbmatch_live;

    public CompetitionAdapter(LinkedHashMap<Competition, String> k, TabFragmentCompetitionList f) {
        ks.putAll(k);

        ff = f;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;

        } else if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > ks.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.compet_list_item, parent, false);
            Myviewholder mvh = new Myviewholder(v);
            return mvh;

        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_competetion, parent, false);
            return new HeaderViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
        if (holder instanceof Myviewholder) {
            Set<Map.Entry<Competition, String>> mapSet = ks.entrySet();
            Map.Entry<Competition, String> element = (Map.Entry<Competition, String>) mapSet.toArray()[position - 1];


            Competition c = element.getKey();
            list_compt.add(c);
            String[] str_array_nbmatch_nblive = element.getValue().split(":");
            ((Myviewholder) holder).txtmatch.setText(str_array_nbmatch_nblive[0]);
            ((Myviewholder) holder).txtnblive.setText(str_array_nbmatch_nblive[1]);
            String[] str_array_compet_country = c.getName().split(":");
            ((Myviewholder) holder).tx.setText(str_array_compet_country[1]);
            ((Myviewholder) holder).tcountry.setText(str_array_compet_country[0]);
            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(40)
                    .oval(true)
                    .build();

            Picasso.with(((Myviewholder) holder).im.getContext())
                    .load(c.getFlagUrl())
                    .fit()
                    .transform(transformation)
                    .into(((Myviewholder) holder).im);

        } else if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).all.setText(nbmatch_total + "");
            ((HeaderViewHolder) holder).alllive.setText(nbmatch_live + "");

        }

    }


    @Override
    public int getItemCount() {
        return ks.size() + 1;
    }

    public void updateAnswers(LinkedHashMap<Competition, String> items, int nbmatch, int nblive) {
        nbmatch_total = nbmatch;
        nbmatch_live = nblive;
        ks = items;
        notifyDataSetChanged();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {

        TextView tx, tcountry, txtmatch, txtnblive;
        ImageView im;

        public Myviewholder(View itemView) {
            super(itemView);
            txtmatch = (TextView) itemView.findViewById(R.id.nbmatch);
            txtnblive = (TextView) itemView.findViewById(R.id.nbmatchlive);
            tcountry = (TextView) itemView.findViewById(R.id.txtcardcountry);
            tx = (TextView) itemView.findViewById(R.id.txtcardcompt);
            im = (ImageView) itemView.findViewById(R.id.imgcard);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = FragmentMatchsCompet.newInstance(list_compt.get(getAdapterPosition() - 1));

                    FragmentTransaction transaction = ff.getActivity().getSupportFragmentManager().beginTransaction();
                     transaction.replace(R.id.activity_main, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.addToBackStack(null);
                    transaction.commit();
                    //ff.getActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public View View;
        TextView all, alllive;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            View = itemView;
            all = (TextView) itemView.findViewById(R.id.allnb);
            alllive = (TextView) itemView.findViewById(R.id.allnblive);

            // add your ui components here like this below
            //txtName = (TextView) View.findViewById(R.id.txt_name);

        }
    }
}
