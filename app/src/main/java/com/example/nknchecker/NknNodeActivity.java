package com.example.nknchecker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class NknNodeActivity extends AppCompatActivity {

    public static final String LOG_TAG = NknNodeActivity.class.getName();

    NknNodeAdapter nknNodeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nkn_node_activity);

        // Extract a list of partial NknNode
        ArrayList<NknNode> nknNodes = QueryUtils.extractNknNodes();

        RecyclerView rvNknNodes = (RecyclerView) findViewById(R.id.rvNknNodes);

        nknNodeAdapter = new NknNodeAdapter(this, nknNodes);
        rvNknNodes.setLayoutManager(new LinearLayoutManager(this));
        rvNknNodes.setAdapter(nknNodeAdapter);
        rvNknNodes.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        nknNodeAdapter.startFetchingNodesDetail();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.node_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imRefresh:
                nknNodeAdapter.startFetchingNodesDetail();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
