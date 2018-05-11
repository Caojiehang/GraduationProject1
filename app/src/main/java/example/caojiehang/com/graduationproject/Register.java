package example.caojiehang.com.graduationproject;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.caojiehang.com.graduationproject.db.MyDatabaseHelper;
import example.caojiehang.com.graduationproject.db.User;

public class Register extends AppCompatActivity {
    private EditText inputname;
    private EditText inputpassword;
    private EditText inputemail;
    private Button rgButton;
    private TextView linkLogin;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new MyDatabaseHelper(this,"UserManage.db",null,2);
        inputname = (EditText) findViewById(R.id.input_name);
        inputpassword = (EditText) findViewById(R.id.input_password);
        inputemail = (EditText) findViewById(R.id.input_email);
        rgButton = (Button) findViewById(R.id.btn_signup);
        linkLogin = (TextView) findViewById(R.id.link_login);
        rgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputname.getText().toString().trim();
                String userPwd = inputpassword.getText().toString().trim();
                String userEmail = inputemail.getText().toString();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //添加数据
                if(CheckUsername(username)) {
                    values.put("name", username);
                    values.put("pwd", userPwd);
                    values.put("email", userEmail);
                    db.insert("Users", null, values);
                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                    values.clear();
                    Intent intent = new Intent(Register.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Register.this,"用户名已存在，注册失败",Toast.LENGTH_SHORT).show();

                }
            }
        });
        linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public boolean CheckUsername(String userName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Users",null,"name=?", new String[]{userName},null,null,null);
        while(cursor.moveToNext()) {
            String user = cursor.getString(1);
            if(user.equals(userName)) {
                return false;
            }else
                return true;
            }
            return true;

    }
}









