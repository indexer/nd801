package indexer.com.portfolio;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
      Toast.makeText(view.getContext(),
          getResources().getString(R.string.toast_text)+((Button) view).getText(),
          Toast.LENGTH_LONG).show();
    }
  }
}
