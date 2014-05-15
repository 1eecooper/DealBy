package by.deal.app.ui;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.InputStream;
import by.deal.app.R;
import by.deal.app.adapter.OrdersCursorAdapter;
import by.deal.app.sql.Contract.*;
import by.deal.app.sql.SQLHelper;
import by.deal.app.xml.XmlParser;

public class OrdersActivity extends Activity {

    SQLHelper mSQLHelper;
    ListView mListView;
    OrdersCursorAdapter mAdapter;
    boolean mExistsCancelButton = false;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExistsCancelButton = false;
        setContentView(R.layout.orders_main);
        getActionBar().show();
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getActionBar().setCustomView(R.layout.actionbar_main);

        mSQLHelper = SQLHelper.getInstance(this.getApplicationContext());
        XmlParser xmlParser = new XmlParser();
        InputStream inputStream = getResources().openRawResource(R.raw.orders);
        mSQLHelper = xmlParser.readXml(inputStream, mSQLHelper);

        mListView = (ListView)findViewById(R.id.orders_list);
        mAdapter = new OrdersCursorAdapter(this, mSQLHelper.getAllOrders(), 0);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrdersActivity.this, ScreenSlidePagerActivity.class);
                intent.putExtra(OrderEntry.ID, (int)l);
                startActivity(intent);
            }
        });

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
            mAdapter.changeCursor(mSQLHelper.getOrdersByQuery(query));
            getActionBar().hide();
            if (!mExistsCancelButton) {
                addButtonCancel();
                mExistsCancelButton = true;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    public void addButtonCancel(){
        ViewGroup layout = (ViewGroup) findViewById(R.id.orders_line_top);
        Button button = new Button(this);
        button.setText(getResources().getString(R.string.cancel));
        button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreate(null);
            }
        });
    }
}
