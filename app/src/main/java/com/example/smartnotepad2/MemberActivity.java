package com.example.smartnotepad2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class MemberActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_member);

            RecyclerView recyclerView = findViewById(R.id.recycle_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            registerForContextMenu(recyclerView);
            final PicAdapter picAdapter = new PicAdapter(this);
            recyclerView.setAdapter(picAdapter);
            picAdapter.addFood();
            picAdapter.addFood();
            picAdapter.addFood();



        /*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picAdapter.addFood();
            }
        });
        */
        }

    }
