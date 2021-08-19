package com.codepath.android_activity;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.nio.charset.Charset;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable

import java.util.ArrayList;
import java.util.List;

class MainActivity : AppCompatActivity() {

    public static final String KEY_ITEM_TEXT = "item text";
    public static final String KEY_ITEM_TEXT = "item position"
    public static final int EDIT_TEXT_CODE = 20;
    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecycleView rvItems;
    ItemsAdapter itemsAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd= findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);



        loadItems();

        ItemAdapter.OnLongClickListener onLongClickListener = new ItemAdapter.OnlongClickListener(){
            @Override
            public void onItemLongClicked(int position){

                items.remove(position);

                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();

                saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemAdapter.OnClickListener(){
            @Override
            public void onItemClicked(int position){
                Log.d("MainActivity", "Single click at position" + position);
                Intent i = new Intent(MainActivity.this, EditActivity.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION,position);
                startActivityForResult(i,EDIT_TEXT_CODE)
            }
        }
        final ItemsAdapter itemAdapter = new ItemAdapter(items, onLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String todItem = etItem.getText().toString();

                items.add(todoItem);

                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){
            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);

            items.set(position, itemText);
            itemsAdapter.notifyItemChanged(position);
            saveItems();
            Toast.makeText(getApplicationContext(), "Item updated succesfully!", Toast.LENGTH_SHORT).show();
        }else{
            Log.w("MainActivity", "Unknown call to onActivityResult");
        }
    }

    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }
    private void loadItems(){
        items = new ArrayList<>(FileUtils.ReadLines(getDataFile(), Charset.defaultCharset());
        Log.e("MainActivity", "Error reading items", e);

    }

private void saveItems(){
    try {
        FileUtlis.writeLines(getDataFile(), items);
    } catch (IOException e) {
        Log.e("MainActivity", "Error reading items", e);
    }
}

}