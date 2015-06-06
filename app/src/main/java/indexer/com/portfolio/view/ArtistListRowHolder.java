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
  protected ImageView thumbnail;
  public TextView title;

  public ArtistListRowHolder(View view) {
    super(view);
    this.title = (TextView) view.findViewById(R.id.artist_name);
  }
}
