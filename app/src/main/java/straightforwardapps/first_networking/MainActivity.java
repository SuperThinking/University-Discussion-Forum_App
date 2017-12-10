package straightforwardapps.first_networking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view)
    {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public void register(View view)
    {
        Toast.makeText(this, "Yet to be built", Toast.LENGTH_SHORT).show();
    }
}
