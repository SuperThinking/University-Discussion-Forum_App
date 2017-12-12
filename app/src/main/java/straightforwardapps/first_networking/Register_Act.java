package straightforwardapps.first_networking;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Register_Act extends AppCompatActivity {

    EditText rno, name, mail, pas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rno = (EditText) findViewById(R.id.reg);
        name = (EditText) findViewById(R.id.name);
        mail = (EditText) findViewById(R.id.email);
        pas = (EditText) findViewById(R.id.pass);
    }

    public void register_start(View view)
    {
        new doRegister().execute();
    }

    public class doRegister extends AsyncTask<String, String, String>
    {
        String n1 = name.getText().toString();
        String n2 = pas.getText().toString();
        String n3 = mail.getText().toString();
        String n4 = rno.getText().toString();

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.43.38/IWP+SE/API/register.php?name="+n1+"&password="+n2+"&email="+n3+"&user_reg_no="+n4);
                HttpURLConnection x = (HttpURLConnection) url.openConnection();
                x.connect();

                InputStream stream = x.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                StringBuffer buffer = new StringBuffer();

                while((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Already Registered";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.equals("true"))
            {
                Toast.makeText(getApplicationContext(), "REGISTERED!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
            }

        }
    }


}
