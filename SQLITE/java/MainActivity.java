package com.s1.sqlite;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
    EditText editstu_Rollno,stu_editName,stu_editMarks;
    Button btnAdd,btnDelete,btnUpdate,btnView,btnViewAll,btnShowInfo;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editstu_Rollno=(EditText)findViewById(R.id.editRollno);
        stu_editName=(EditText)findViewById(R.id.editName);
        stu_editMarks=(EditText)findViewById(R.id.editMarks);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnUpdate=(Button)findViewById(R.id.btnModify);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnShowInfo=(Button)findViewById(R.id.btnShowInfo);
        //set onclick listener on button
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);

        //creating database name StudentRecordsDB
        db=openOrCreateDatabase("StudentRecordsDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR primary key,name VARCHAR,marks VARCHAR);");
    }
    public void onClick(View view)
    {
        if(view==btnAdd)
        {
            //checking for blank fields
            if(editstu_Rollno.getText().toString().trim().length()==0||
                    stu_editName.getText().toString().trim().length()==0||
                    stu_editMarks.getText().toString().trim().length()==0)
            {
                alert("Error", "Please enter all fields");
                return;
            }
            // inserting values to DB.
            db.execSQL("INSERT INTO student VALUES('"+editstu_Rollno.getText()+"','"+stu_editName.getText()+
                    "','"+stu_editMarks.getText()+"');");
            alert("Success", "Record added");
            clearText();
        }
        if(view==btnDelete)
        {
            if(editstu_Rollno.getText().toString().trim().length()==0)
            {
                alert("Error", "Please enter Rollno");
                return;
            }

            //calling cursor object
            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editstu_Rollno.getText()+"'", null);
            if(c.moveToFirst())
            {
                // delete data from table
                db.execSQL("DELETE FROM student WHERE rollno='"+editstu_Rollno.getText()+"'");
                alert("Success", "Record Deleted");
            }
            else
            {
                alert("Error", "Invalid Rollno");
            }
            clearText();
        }
        if(view==btnUpdate)
        {
            if(editstu_Rollno.getText().toString().trim().length()==0)
            {
                alert("Error", "Please enter Rollno");
                return;
            }

            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editstu_Rollno.getText()+"'", null);
            if(c.moveToFirst())
            {
                //update table
                db.execSQL("UPDATE student SET name='"+stu_editName.getText()+"',marks='"+stu_editMarks.getText()+
                        "' WHERE rollno='"+editstu_Rollno.getText()+"'");
                alert("Success", "Record Modified");
            }
            else
            {
                alert("Error", "Invalid Rollno");
            }
            clearText();
        }
        if(view==btnView)
        {
            if(editstu_Rollno.getText().toString().trim().length()==0)
            {
                alert("Error", "Please enter Rollno");
                return;
            }

            Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+editstu_Rollno.getText()+"'", null);

            if(c.moveToFirst())
            {
                //view with the help of cursor object
                stu_editName.setText(c.getString(1));
                stu_editMarks.setText(c.getString(2));
            }
            else
            {
                alert("Error", "Invalid Rollno");
                clearText();
            }
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM student", null);
            if(c.getCount()==0)
            {
                alert("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                //view all
                buffer.append("Rollno: "+c.getString(0)+"\n");
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Marks: "+c.getString(2)+"\n\n");
            }
            alert("Student Details", buffer.toString());
        }
        if(view==btnShowInfo)
        {
            alert("Simple Sqlite in Android", "Thank you");
        }
    }
    public void alert(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editstu_Rollno.setText("");
        stu_editName.setText("");
        stu_editMarks.setText("");
        editstu_Rollno.requestFocus();
    }
}

