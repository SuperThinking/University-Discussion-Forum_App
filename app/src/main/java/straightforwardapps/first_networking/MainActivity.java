package straightforwardapps.first_networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            //Toast.makeText(getApplicationContext(), "BACK", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            //finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void login(View view)
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void register(View view)
    {
       Intent i = new Intent(this, Register_Act.class);
        startActivity(i);
    }
}
