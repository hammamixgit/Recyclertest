package recorder.appss.cool.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import recorder.appss.cool.Holder.HeaderViewHolderCompetitionAdap;
import recorder.appss.cool.Holder.ViewHolderCompetitionAdap;
import recorder.appss.cool.model.Competition;
import recorder.appss.cool.model.Constants;
import recorder.appss.cool.ui.fragment.TabFragmentCompetitionList;
import recorder.appss.cool.ui.fragment.FragmentMatchsCompet;
import recorder.appss.cool.recyclertest.R;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by work on 29/09/2017.
 */

public class CompetitionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Competition> list_compt = new ArrayList<>();
    LinkedHashMap<Competition, String> ks = new LinkedHashMap<Competition, String>();

    TabFragmentCompetitionList ff;

    private int nbmatch_total, nbmatch_live;

    public CompetitionAdapter(LinkedHashMap<Competition, String> k, TabFragmentCompetitionList f) {
        ks.putAll(k);

        ff = f;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return Constants.TYPE_HEADER;

        } else if (isPositionFooter(position)) {
            return Constants.TYPE_FOOTER;
        }

        return Constants.TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position > ks.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == Constants.TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.compet_list_item, parent, false);
            ViewHolderCompetitionAdap mvh = new ViewHolderCompetitionAdap(v);
            return mvh;

        } else if (viewType == Constants.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout_competetion, parent, false);
            return new HeaderViewHolderCompetitionAdap(view);

        } else if (viewType == Constants.TYPE_FOOTER) {

        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //   imageLoader.displayImage(ks.get(position).getCompetition().getFlagUrl(), holder.im);
        if (holder instanceof ViewHolderCompetitionAdap) {
            Set<Map.Entry<Competition, String>> mapSet = ks.entrySet();
            Map.Entry<Competition, String> element = (Map.Entry<Competition, String>) mapSet.toArray()[position - 1];


            Competition c = element.getKey();
            list_compt.add(c);
            String[] str_array_nbmatch_nblive = element.getValue().split(":");
            ((ViewHolderCompetitionAdap) holder).txtmatch.setText(str_array_nbmatch_nblive[0]);
            ((ViewHolderCompetitionAdap) holder).txtnblive.setText(str_array_nbmatch_nblive[1]);
            String[] str_array_compet_country = c.getName().split(":");
            ((ViewHolderCompetitionAdap) holder).tx.setText(str_array_compet_country[1]);
            ((ViewHolderCompetitionAdap) holder).tcountry.setText(str_array_compet_country[0]);

            MultiTransformation multi = new MultiTransformation(
                    new BlurTransformation(1),
                    new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM));
            Glide
                    .with(((ViewHolderCompetitionAdap) holder).im.getContext())
                    .load(c.getFlagUrl())
                    .apply(bitmapTransform(multi))
                    .into(((ViewHolderCompetitionAdap) holder).im);

            ((ViewHolderCompetitionAdap) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = FragmentMatchsCompet.newInstance(list_compt.get(position - 1));

                    FragmentTransaction transaction = ff.getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_main, fragment);
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                    transaction.addToBackStack(null);
                    transaction.commit();
                    //ff.getActivity().getSupportFragmentManager().popBackStack();
                }
            });

        } else if (holder instanceof HeaderViewHolderCompetitionAdap) {
            ((HeaderViewHolderCompetitionAdap) holder).all.setText(nbmatch_total + "");
            ((HeaderViewHolderCompetitionAdap) holder).alllive.setText(nbmatch_live + "");

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



}
