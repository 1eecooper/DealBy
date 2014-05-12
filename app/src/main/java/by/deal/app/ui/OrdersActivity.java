package by.deal.app.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.InputStream;
import by.deal.app.R;
import by.deal.app.adapter.CustomCursorAdapter;
import by.deal.app.sql.SQLHelper;
import by.deal.app.xml.XmlParser;

public class OrdersActivity extends Activity {

    SQLHelper mSQLHelper;
    ListView mListView;
    CustomCursorAdapter mAdapter;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar);

        mSQLHelper = new SQLHelper(this);
        XmlParser xmlParser = new XmlParser();
        InputStream inputStream = getResources().openRawResource(R.raw.orders);
        mSQLHelper = xmlParser.readXml(inputStream, mSQLHelper);

        mListView = (ListView)findViewById(R.id.orders_list);
        mAdapter = new CustomCursorAdapter(this, mSQLHelper.getAllOrders(), 0);
        mListView.setAdapter(mAdapter);

        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) findViewById(R.id.search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        handleIntent(getIntent());
    }

    public SQLHelper getSQLHelper() {
        return mSQLHelper;
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //searching
            mAdapter.changeCursor(mSQLHelper.getOrdersByQuery(query));
            getActionBar().hide();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }
}
