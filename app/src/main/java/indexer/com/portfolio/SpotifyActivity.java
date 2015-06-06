package indexer.com.portfolio;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import indexer.com.portfolio.adapter.ArtistListAdapter;
import java.util.ArrayList;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpotifyActivity extends Activity {
  RecyclerView mRecyclerView;
  LinearLayoutManager mLayoutManager;
  ArrayList<String> artist = new ArrayList<>();
  private ArtistListAdapter mArtistListAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spotify);
    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
    // use this setting to improve performance if you know that changes
    // in content do not change the layout size of the RecyclerView
    mRecyclerView.setHasFixedSize(true);
    // use a linear layout manager
    mArtistListAdapter = new ArtistListAdapter(this);
    mLayoutManager = new LinearLayoutManager(SpotifyActivity.this);
    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    mRecyclerView.setLayoutManager(mLayoutManager);
    mRecyclerView.setAdapter(mArtistListAdapter);

    searchArtist();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_spotify, menu);
    return true;
  }

  public void searchArtist() {
    final SpotifyApi api = new SpotifyApi();
    // Most (but not all) of the Spotify Web API endpoints require authorisation.
    // If you know you'll only use the ones that don't require authorisation you can skip this step
    api.setAccessToken(getResources().getString(R.string.spotify_api));
    SpotifyService spotify = api.getService();
    spotify.searchArtists("coldplay", new Callback<ArtistsPager>() {
      @Override public void success(ArtistsPager artistsPager, Response response) {

        for (int i = 0; i < artistsPager.artists.items.size(); i++) {
          artist.add(artistsPager.artists.items.get(i).name);
        }
        mArtistListAdapter.setArtist(artist);
      }

      @Override public void failure(RetrofitError retrofitError) {

      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
