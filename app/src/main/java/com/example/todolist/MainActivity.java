package com.example.todolist;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.todolist.model.DBService;

public class MainActivity extends AppCompatActivity {

    // Model
    private DBService DBService;

    //Controller
    private ListAdapter listAdapter;

    // View
    private ListView itemListView;

    public void updateView() {

        if (listAdapter == null) {
            listAdapter = new ListAdapter(this, DBService.getAllItems(), DBService);
            itemListView.setAdapter(listAdapter);
        } else {
            listAdapter.clear();
            listAdapter.addAll(DBService.getAllItems());
            listAdapter.notifyDataSetChanged();
        }

    }

    public void showDialog() {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Pridėti naują įrašą")
                .setMessage("Ką norite dar nuveikti?")
                .setView(taskEditText)
                .setPositiveButton("Pridėti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        DBService.post(task);
                        updateView();
                    }
                })
                .setNegativeButton("Atšaukti", null)
                .create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        showDialog();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DBService = new DBService(this);

        itemListView = (ListView) findViewById(R.id.listView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        updateView();
    }

}