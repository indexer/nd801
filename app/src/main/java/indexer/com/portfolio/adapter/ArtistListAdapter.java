package indexer.com.portfolio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import indexer.com.portfolio.R;
import indexer.com.portfolio.view.ArtistListRowHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by indexer on 6/6/15.
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListRowHolder> {

  private List<String> artistItemList;
  private Context mContext;

  public ArtistListAdapter(Context context) {
    this.artistItemList = new ArrayList<>();
    this.mContext = context;
  }

  public void setArtist(List<String> artist) {
    this.artistItemList = artist;
    notifyDataSetChanged();
  }

  @Override public ArtistListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_list_item, null);
    ArtistListRowHolder mh = new ArtistListRowHolder(v);
    return mh;
  }

  @Override public void onBindViewHolder(ArtistListRowHolder feedListRowHolder, int i) {

  }

  @Override public int getItemCount() {
    return (null != artistItemList ? artistItemList.size() : 0);
  }
}
