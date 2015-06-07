package indexer.com.portfolio.view;

/**
 * Created by indexer on 6/6/15.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import indexer.com.portfolio.R;

public class ArtistListRowHolder extends RecyclerView.ViewHolder {
  public ImageView thumbnail;
  public TextView title;
  public TagListView tagView;

  public ArtistListRowHolder(View view) {
    super(view);
    this.title = (TextView) view.findViewById(R.id.artist_name);
    this.thumbnail = (ImageView) view.findViewById(R.id.person_photo);
    this.tagView = (TagListView) view.findViewById(R.id.tag_view);
  }
}
