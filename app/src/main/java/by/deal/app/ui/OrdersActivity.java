package by.deal.app.ui;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import java.io.InputStream;
import by.deal.app.R;
import by.deal.app.adapter.CustomCursorAdapter;
import by.deal.app.sql.SQLHelper;
import by.deal.app.xml.XmlParser;

public class OrdersActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_list);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar);

        SQLHelper sqlHelper = new SQLHelper(this);
        XmlParser xmlParser = new XmlParser();
        InputStream inputStream = getResources().openRawResource(R.raw.orders);
        sqlHelper = xmlParser.readXml(inputStream, sqlHelper);

        ListView listView = (ListView)findViewById(R.id.orders_list);
        listView.setAdapter(new CustomCursorAdapter(this, sqlHelper.getAllOrders(), 0, sqlHelper));

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //doMySearch(query);
        }
    }
}
