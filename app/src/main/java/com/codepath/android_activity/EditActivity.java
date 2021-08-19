package com.codepath.android_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);

        etItem= findViewById(R.id.etItem);
        btnSave= findViewById(R.id.btnSave);

        getSupportActoinBar().setTitle("Edit item");

        etItem.getIntent().getStringArrayExtra(MainActivity.KEY_ITEM_TEXT);
        btnSave.setOnClickListener(new View.onClickListener(){
            @Override
            public void onClick(View v){
            Intent intent = new Intent();

            intent.putExtra(MainActivity.KEY_ITEM_TEXT,etItem.getText().toString());
            intent.putExtra(MainActivity.KEY_ITEM_POSITION, getIntent().getInt(MainActivity.KEY_ITEM_POSITION));
            setResult(RESULT_OK,intent);
            finish();
            }
        });
    }
}