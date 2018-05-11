package example.caojiehang.com.graduationproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import example.caojiehang.com.graduationproject.db.MyDatabaseHelper;
import example.caojiehang.com.graduationproject.db.User;
import example.caojiehang.com.graduationproject.utils.EditTextClearTools;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private CircleImageView logoIcon;
    private TextView logoTitle;
    private ImageView usernameIcon;
    private View viewName;
    private EditText etUsername;
    private ImageView usernameClear;
    private ImageView passwordIcon;
    private View viewPassword;
    private EditText etPassword;
    private ImageView passwordClear;
    private CheckBox check;
    private Button lgButton;
    private Button rgButton;
 /*   private List<User> userList;
    private List<User> dataList;*/
    private SharedPreferences pref;
    private  SharedPreferences.Editor editor;
    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new MyDatabaseHelper(this,"UserManage.db",null,2);
        init();
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean  isremember = pref.getBoolean("cb_checkbox",false);
        if(isremember) {
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            etUsername.setText(account);
            etPassword.setText(password);
            check.setChecked(true);
        }
        lgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(login(account,password)) {
                    editor = pref.edit();
                    if(check.isChecked()) {
                        editor.putBoolean("cb_checkbox",true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,"账号不存在或者密码错误",Toast.LENGTH_SHORT).show();
                }

            }
        });
        rgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public boolean  login(String name,String passwordd) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select * from Users where name = ? and pwd =?";
        Cursor cursor = db.rawQuery(sql,new String[] {name,passwordd});
        if(cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;



    }

    private void init() {
        logoIcon = (CircleImageView) findViewById(R.id.icon_logo);
        logoTitle = (TextView) findViewById(R.id.logo_title);
        usernameIcon = (ImageView) findViewById(R.id.icon_userName);
        viewName = (View) findViewById(R.id.viewName);
        etUsername = (EditText) findViewById(R.id.et_userName);
        etPassword = (EditText) findViewById(R.id.et_password);
        viewPassword = (View) findViewById(R.id.viewPwd);
        passwordIcon = (ImageView) findViewById(R.id.icon_password);
        usernameClear = (ImageView) findViewById(R.id.iv_unameClear);
        passwordClear = (ImageView) findViewById(R.id.iv_pwdClear);
        check = (CheckBox) findViewById(R.id.cb_checkbox);
        lgButton = (Button) findViewById(R.id.btn_login);
        rgButton = (Button) findViewById(R.id.btn_register);
        EditTextClearTools.addClearListener(etUsername, usernameClear);
        EditTextClearTools.addClearListener(etPassword, passwordClear);
    }
//测试短代码
   /* private void checkLogin() {
        String account = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if (account.equals("admin") && password.equals("123456")) {
            editor = pref.edit();
            if(check.isChecked()) {
                editor.putBoolean("cb_checkbox",true);
                editor.putString("account",account);
                editor.putString("password",password);
            } else {
                editor.clear();
            }
            editor.apply();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "账号或者密码错误", Toast.LENGTH_SHORT)
                    .show();
        }
    }*/

    }


