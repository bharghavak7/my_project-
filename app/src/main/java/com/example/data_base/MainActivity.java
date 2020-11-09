package com.example.data_base;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        EditText usn,name,phone,email;
        Button add,search,delete,clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText usn =(EditText)findViewById(R.id.usnid);
        final EditText name =(EditText)findViewById(R.id.nameid);
        final EditText phone =(EditText)findViewById(R.id.phoneid);
        final EditText email=(EditText)findViewById(R.id.emailid);

        add=(Button)findViewById(R.id.button);
        delete=(Button)findViewById(R.id.button2);
        search=(Button)findViewById(R.id.button3);
        clear=(Button)findViewById(R.id.button4);

        final SQLiteDatabase db=openOrCreateDatabase("student",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(USN VARCHAR,Name VARCHAR,Email VARCHAR,Mobile VARCHAR);");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usn.getText().toString().trim().length()==0||name.getText().toString().trim().length()==0||phone.getText().toString().trim().length()==0||email.getText().toString().trim().length()==0 )
                {
                    Toast.makeText(MainActivity.this,"Error Enter a Data",Toast.LENGTH_LONG).show();
                }
                else{
                    db.execSQL("insert into student values('"+usn.getText()+"','"+name.getText()+"','"+email.getText()+"','"+phone.getText()+"')");
                    Toast.makeText(MainActivity.this,"Data Saved Successfully",Toast.LENGTH_LONG).show();

                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usn.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this,"Error Enter a usn",Toast.LENGTH_LONG).show();
                }
                else{
                    Cursor c=db.rawQuery("SELECT * FROM student where USN '"+usn.getText()+"'",null);
                    if (c.moveToFirst()){
                        usn.setText(c.getString(0));
                        name.setText(c.getString(1));
                        email.setText(c.getString(2));
                        phone.setText(c.getString(3));
                                            }
                    else {
                        Toast.makeText(MainActivity.this,"Error! Invalid USN",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usn.getText().toString().trim().length()==0){
                    Toast.makeText(MainActivity.this,"Error Invalid USN",Toast.LENGTH_LONG).show();
                }else {
                    Cursor c = db.rawQuery("SELECT * FROM student where USN '" + usn.getText() + "'", null);
                    if (c.moveToFirst()) {
                        db.execSQL("DELETE FROM student where USN = '" + usn.getText() + "'");
                        Toast.makeText(MainActivity.this, "Record deleted successfully", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this,"Error! Invalid USN",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usn.getText().clear();
                name.getText().clear();
                email.getText().clear();
                phone.getText().clear();

            }
        });

    }
}