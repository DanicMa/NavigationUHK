package uhk.kikm.navigationuhk.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uhk.kikm.navigationuhk.R;
import uhk.kikm.navigationuhk.dataLayer.CouchDBManager;
import uhk.kikm.navigationuhk.dataLayer.Fingerprint;

/**
 * Aktivita urcena na zobrazeni seznamu vsech porizenych fingerprintu
 *
 * Dominik Matoulek 2015
 */


public class ListPositionsActivity extends ActionBarActivity {

    CouchDBManager dbManager;
    List<Fingerprint> fingerprints;
    Map<String, String> positionsMap;
    ArrayList<String> positionsStrings;
    ListView lv;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_positions);

        dbManager = new CouchDBManager(this);
        Log.d(getClass().getName(), "Open db connection");

        positionsMap = new HashMap<>();

        makeDataForView();

        lv = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, positionsStrings);

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDetailsOfFingerprint(positionsStrings.get(position));

            }
        });
    }

    /**
     * Pripravuje data pro zobrazeni
     */
    private void makeDataForView() {
        positionsMap.clear();

        fingerprints = dbManager.getAllFingerprints();

        for (Fingerprint p : fingerprints)
        {
              positionsMap.put(String.valueOf(p.getX()) + " " + String.valueOf(p.getY()) + " " + p.getId(), p.getId());
        }

        positionsStrings = new ArrayList<>(positionsMap.keySet());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_positions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_upload)
        {
            dbManager.uploadDBToServer(this);


        }
        else if (id == R.id.action_download)
        {
            dbManager.downloadDBFromServer(this);
            makeDataForView();
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        dbManager.closeConnection();
        Log.d(getClass().getName(), "Close db connection");
    }

    /**
     * Zobrazi novou aktivitu obsahujici informace o fingerprintu
     * @param position ID fingerprintu v DB
     */
    private void showDetailsOfFingerprint(final String position)
    {

        Intent intent = new Intent(this, PositionInfoActivity.class);
        intent.putExtra("id", positionsMap.get(position));
        startActivity(intent);

    }

}
