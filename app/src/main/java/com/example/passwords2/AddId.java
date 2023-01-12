package com.example.passwords2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class AddId extends Activity implements View.OnClickListener {

    private EditText etAccount, etPassword, etFilter;

    private DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_add);

        setTitle("Add account:");

        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        etFilter = findViewById(R.id.etFilter);

        Button btnAddId = findViewById(R.id.btnAddId);

        dataBaseManager = new DataBaseManager(this);
        dataBaseManager.open();
        btnAddId.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnAddId) {

                final String account = etAccount.getText().toString();
                final String password = etPassword.getText().toString();
                final String filter = etFilter.getText().toString();

                dataBaseManager.insert(account, password, filter);

                Intent main = new Intent(AddId.this, ResultActivity.class)
                        .setFlags((Intent.FLAG_ACTIVITY_CLEAR_TOP));

                startActivity(main);
        }
    }
}
