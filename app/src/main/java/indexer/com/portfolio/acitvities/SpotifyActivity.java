package indexer.com.portfolio.acitvities;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import indexer.com.portfolio.R;
import indexer.com.portfolio.adapter.ArtistListAdapter;
import indexer.com.portfolio.listener.EndlessRecyclerOnScrollListener;
import indexer.com.portfolio.utils.NetworkChecker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SpotifyActivity extends AppCompatActivity
    implements android.support.v7.widget.SearchView.OnQueryTextListener {
  RecyclerView mRecyclerView;
  LinearLayoutManager mLayoutManager;
  ArrayList<Artist> artist = new ArrayList<>();
  ArrayList<Artist> artist_temp = new ArrayList<>();
  private ArtistListAdapter mArtistListAdapter;
  CollapsingToolbarLayout collapsingToolbar;
  CoordinatorLayout main_content;
  Toolbar toolbar;
  SearchView searchView;
  String global_search = "all";
  ConnectivityManager connectivityManager;
  private boolean loading = true; // True if we are still waiting for the last set of data to load.
  // The minimum amount of items to have below your current scroll position before loading more.
  int totalItemCount;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_spotify);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    main_content = (CoordinatorLayout) findViewById(R.id.main_content);
    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(true);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    collapsingToolbar.setTitle(getResources().getString(R.string.spotify));
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
    mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
      @Override public void onLoadMore(int current_page) {
        Log.e("Current Page", "in scoll" + current_page);
        searchArtist(global_search, current_page);
      }
    });
    connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (NetworkChecker.isNetworkConnected(connectivityManager) == true) {
      searchArtist(global_search, 0);
    } else {
      Snackbar.make(mRecyclerView, R.string.no_network, Snackbar.LENGTH_LONG).show();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_spotify, menu);
    MenuItem searchMenuItem = menu.findItem(R.id.search);
    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    searchView = (android.support.v7.widget.SearchView) searchMenuItem.getActionView();
    searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
    searchView.setOnQueryTextListener(this);
    // get my MenuItem with placeholder submenu
    return true;
  }

  public void searchArtist(String artistName, int offset) {
    artist_temp.clear();
    Map filter = new HashMap();
    filter.put(getResources().getString(R.string.offset), offset);
    filter.put(getResources().getString(R.string.limit),
        getResources().getInteger(R.integer.limit));
    final SpotifyApi api = new SpotifyApi();
    // Most (but not all) of the Spotify Web API endpoints require authorisation.
    // If you know you'll only use the ones that don't require authorisation you can skip this step
    // api.setAccessToken(getResources().getString(R.string.spotify_api));
    SpotifyService spotify = api.getService();
    spotify.searchArtists(artistName, filter, new Callback<ArtistsPager>() {
      @Override public void success(ArtistsPager artistsPager, Response response) {
        totalItemCount = artistsPager.artists.total;
        for (int i = 0; i < artistsPager.artists.items.size(); i++) {
          Artist artistSingle = new Artist();
          artistSingle.id = artistsPager.artists.items.get(i).id;
          artistSingle.name = artistsPager.artists.items.get(i).name;
          artistSingle.images = artistsPager.artists.items.get(i).images;
          artistSingle.genres = artistsPager.artists.items.get(i).genres;
          artist_temp.add(artistSingle);
          artist.add(artist_temp.get(i));
        }

        mRecyclerView.post(new Runnable() {
          @Override public void run() {
            mArtistListAdapter.setArtist(artist);
          }
        });
      }

      @Override public void failure(RetrofitError retrofitError) {

      }
    });
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
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

  @Override public boolean onQueryTextSubmit(String queryName) {
    searchView.clearFocus();
    if (NetworkChecker.isNetworkConnected(connectivityManager) == true) {
      artist.clear();
      searchArtist(queryName, 0);
    } else {
      Snackbar.make(mRecyclerView, R.string.no_network, Snackbar.LENGTH_LONG).show();
    }

    return true;
  }

  @Override public boolean onQueryTextChange(String s) {
    return false;
  }
}
