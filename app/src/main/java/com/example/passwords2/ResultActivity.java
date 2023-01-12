/*
https://developer.android.com/guide
https://developer.android.com/reference/
Standard Android developer and references. Unfortunately, often written in language too difficult for someone less comfortable like me.
https://startandroid.ru/ru/uroki/vse-uroki-spiskom.html
Basic Android Java and XML course. Written in 2011-2012, a lot have changed since then concerning Android Java development. Had to optimize and modify.
Combining these two sources helped in building the majority of an application.
 */

package com.example.passwords2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] from = new String[] {DataBaseHelper._ID, DataBaseHelper.ACCOUNT, DataBaseHelper.PASSWORD,
    DataBaseHelper.FILTER};

    final int[] to = new int[] {R.id.id, R.id.account, R.id.password, R.id.filter};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        DataBaseManager dataBaseManager = new DataBaseManager(this);
        dataBaseManager.open();

        Cursor cursor = dataBaseManager.crawl();

        ListView passList = findViewById(R.id.passList);

        Button btnAdd = findViewById(R.id.button_add);
        btnAdd.setOnClickListener(this);

        /* Activity for displaying list */
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_view_record, cursor, from, to, 0);
        adapter.notifyDataSetChanged();

        passList.setAdapter(adapter);

        /* Set listener for short click */
        passList.setOnItemClickListener((parent, view, position, _id) -> {

            TextView passwordTextView = view.findViewById(R.id.password);

            String password = passwordTextView.getText().toString();

            /* Show password */
            new AlertDialog.Builder(ResultActivity.this)
                    .setTitle("Password is: ")
                    .setMessage(password)
                    .setNeutralButton("Copy", (dialog, which) -> {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("pass", password);
                        clipboard.setPrimaryClip(clip);
                    })
                    .show();
        });

        /* Set listener for long click */
        passList.setOnItemLongClickListener((parent, view, position, viewId) -> {
            TextView idTextView = view.findViewById(R.id.id);
            TextView accountTextView = view.findViewById(R.id.account);
            TextView passwordTextView = view.findViewById(R.id.password);
            TextView filterTextView = view.findViewById(R.id.filter);

            String _id =idTextView.getText().toString();
            String account = accountTextView.getText().toString();
            String password = passwordTextView.getText().toString();
            String filter = filterTextView.getText().toString();

            Intent modify_intent = new Intent(getApplicationContext(), ModifyId.class);
            modify_intent.putExtra("_id", _id);
            modify_intent.putExtra("account", account);
            modify_intent.putExtra("password", password);
            modify_intent.putExtra("filter", filter);

            startActivity(modify_intent);
            return true;
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add) {
                /* Activity for adding an id */
                 Intent add_id = new Intent(this, AddId.class);
                 startActivity(add_id);
        }
    }

}