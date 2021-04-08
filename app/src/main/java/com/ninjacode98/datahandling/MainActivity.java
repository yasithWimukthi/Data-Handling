package com.ninjacode98.datahandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.ListIterator;

import database.DBHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "USERNAME";
    private Button selectAllBtn;
    private Button addBtn;
    private Button signInBtn;
    private Button deleteBtn;
    private Button updateBtn;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectAllBtn = findViewById(R.id.select_all_btn);
        addBtn = findViewById(R.id.add_btn);
        signInBtn = findViewById(R.id.signin_btn);
        deleteBtn = findViewById(R.id.delete_btn);
        updateBtn = findViewById(R.id.update_btn);
        usernameEditText = findViewById(R.id.user_name_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        DBHelper = new DBHelper(this);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long row = 0;

                if (!TextUtils.isEmpty(usernameEditText.getText().toString().trim()) && !TextUtils.isEmpty(passwordEditText.getText().toString().trim())){
                   row = DBHelper.addInfo(usernameEditText.getText().toString().trim(),passwordEditText.getText().toString().trim());
                }

                if(row>0){
                    Toast.makeText(MainActivity.this,"Insert Successfully.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Not Inserted.",Toast.LENGTH_LONG).show();
                }
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUserExist = false;

                if(!TextUtils.isEmpty(usernameEditText.getText().toString().trim()) && !TextUtils.isEmpty(passwordEditText.getText().toString().trim())){
                    isUserExist = DBHelper.readInfo(usernameEditText.getText().toString().trim(),passwordEditText.getText().toString().trim());
                }

                if(isUserExist){
                    Toast.makeText(MainActivity.this,"User is already exist.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Can not find user.",Toast.LENGTH_LONG).show();
                }
            }
        });

        selectAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List <String>usenames = DBHelper.readAllInfo();
                int i = 0;
                while (i<usenames.size()) {
                    Log.d(TAG, usenames.get(i));
                    i++;
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDelete = false;
                if(!TextUtils.isEmpty(usernameEditText.getText().toString().trim())){
                    isDelete = DBHelper.deleteInfo(usernameEditText.getText().toString().trim());
                }

                if(isDelete){
                    Toast.makeText(MainActivity.this,"Delete successfully.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Not Deleted.",Toast.LENGTH_LONG).show();
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;

                if(!TextUtils.isEmpty(usernameEditText.getText().toString().trim()) && !TextUtils.isEmpty(passwordEditText.getText().toString().trim())){
                    count = DBHelper.updateInfo(usernameEditText.getText().toString().trim(),passwordEditText.getText().toString().trim());
                }

                if(count > 0){
                    Toast.makeText(MainActivity.this,"Update Successfully.",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Not Updated.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}