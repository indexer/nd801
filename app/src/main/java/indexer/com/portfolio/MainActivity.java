package indexer.com.portfolio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import kaaes.spotify.webapi.android.models.Pager;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity implements View.OnClickListener {
  private Button spotify, scoreapp, liberayapp, buildbigger, xyzreader, capstone;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    capstone = (Button) findViewById(R.id.capstone);
    spotify = (Button) findViewById(R.id.spotifysteamer);
    scoreapp = (Button) findViewById(R.id.scoresapp);
    liberayapp = (Button) findViewById(R.id.liberayapp);
    xyzreader = (Button) findViewById(R.id.xyzreader);
    buildbigger = (Button) findViewById(R.id.buildbigger);
    handleClcik(capstone, spotify, scoreapp, liberayapp, xyzreader, buildbigger);
  }

  protected void handleClcik(Button... buttons) {
    for (Button button : buttons) {
      button.setOnClickListener(this);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
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

  @Override public void onClick(View view) {
    if (view instanceof Button) {
      if (view.getId() == spotify.getId()) {
        Intent intentToSpotify = new Intent(MainActivity.this, SpotifyActivity.class);
        startActivity(intentToSpotify);
      }
    }
  }

  public void getArtistAlbums() {
    SpotifyApi api = new SpotifyApi();

    // Most (but not all) of the Spotify Web API endpoints require authorisation.
    // If you know you'll only use the ones that don't require authorisation you can skip this step
    api.setAccessToken(getResources().getString(R.string.spotify_api));
    SpotifyService spotify = api.getService();
    spotify.getArtistAlbums("0TnOYISbd1XYRBk9myaseg", new Callback<Pager<Album>>() {
      @Override public void success(Pager<Album> albumPager, Response response) {
        Log.e("Album Pager", "" + albumPager.items.size());
      }

      @Override public void failure(RetrofitError retrofitError) {

      }
    });
  }
}
