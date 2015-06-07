package indexer.com.portfolio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import indexer.com.portfolio.R;
import indexer.com.portfolio.view.ArtistListRowHolder;
import java.util.ArrayList;
import java.util.List;
import kaaes.spotify.webapi.android.models.Artist;

/**
 * Created by indexer on 6/6/15.
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListRowHolder> {

  private List<Artist> artistItemList;
  private Context mContext;

  public ArtistListAdapter(Context context) {
    this.artistItemList = new ArrayList<>();
    this.mContext = context;
  }

  public void setArtist(List<Artist> artist) {
    this.artistItemList = artist;
    notifyDataSetChanged();
  }

  @Override public ArtistListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_list_item, null);
    ArtistListRowHolder mh = new ArtistListRowHolder(v);
    return mh;
  }

  @Override public void onBindViewHolder(ArtistListRowHolder artistListRowHolder, int i) {
    artistListRowHolder.title.setText(artistItemList.get(i).name);
   /* if (artistItemList.get(i).genres.size() > 0) {
      for (int j = 0; j < artistItemList.get(i).genres.size(); j++) {
        artistListRowHolder.tagView.addTag(artistItemList.get(i).genres.get(j));
      }
    }*/
    if (artistItemList.get(i).images.size() != 0) {
      Picasso.with(mContext)
          .load(artistItemList.get(i).images.get(1).url)
          .error(R.drawable.placeholder)
          .into(artistListRowHolder.thumbnail, new Callback() {
            @Override public void onSuccess() {

            }

            @Override public void onError() {

            }
          });
    }
  }

  @Override public int getItemCount() {
    return (null != artistItemList ? artistItemList.size() : 0);
  }
}
