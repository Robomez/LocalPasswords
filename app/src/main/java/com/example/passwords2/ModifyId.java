package com.example.passwords2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class ModifyId extends Activity implements View.OnClickListener {

    private EditText etAccount, etPassword, etFilter;

    private long _id;

    private DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id_modify);

        setTitle("Modify account: ");

        dataBaseManager = new DataBaseManager(this);
        dataBaseManager.open();

        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        etFilter = findViewById(R.id.etFilter);

        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("_id");
        String account = intent.getStringExtra("account");
        String password = intent.getStringExtra("password");
        String filter = intent.getStringExtra("filter");

        _id = Long.parseLong(id);

        etAccount.setText(account);
        etPassword.setText(password);
        etFilter.setText(filter);

        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                String filter = etFilter.getText().toString();

                dataBaseManager.update(_id, account, password, filter);
                this.returnToResult();
                break;

            case R.id.btnDelete:
                dataBaseManager.delete(_id);
                this.returnToResult();
                break;
        }
    }

    public void returnToResult() {
        Intent result_intent = new Intent(getApplicationContext(), ResultActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(result_intent);
    }
}
