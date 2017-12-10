package straightforwardapps.first_networking;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SuccessLogin extends AppCompatActivity {


    TextView reg_disp;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_login);
        reg_disp = (TextView) findViewById(R.id.reg_disp);

        lv = (ListView) findViewById(R.id.lv);
        String toptop = getIntent().getStringExtra("regno");
        reg_disp.setText(toptop);

        String lru = "http://192.168.43.38/IWP+SE/API/ConverttoJson.php";
        new JSONTask().execute(lru);
    }

    /*public void question_load(View view)
    {
        //loginurl = new URL("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");//("192.168.43.38/IWP+SE/API/answerJson.php");
        String lru = "http://192.168.43.38/IWP+SE/API/FindName.php";
        new JSONTask().execute(lru);
    }*/


    public class JSONTask extends AsyncTask<Object, String, String[]>
    {

        String toptop = getIntent().getStringExtra("regno");
        @Override
        protected String[] doInBackground(Object... urls) {
            HttpURLConnection y = null;
            HttpURLConnection y1 = null;
            BufferedReader reader = null;
            BufferedReader reader1 = null;
            try {
                //Toast.makeText(LoginActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                URL loginurl = new URL(urls[0].toString());
                y = (HttpURLConnection) loginurl.openConnection();
                y.connect();

                InputStream stream = y.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                StringBuffer buffer = new StringBuffer( );
                while((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }

                String lala = buffer.toString();
                //JSONObject x = new JSONObject(lala);
                JSONArray par = new JSONArray(lala);
                String[] boom=new String[par.length()+1];
                boom[0] = "poop";
                int k=1;
                for(int i=0; i<par.length(); i++)
                {
                    JSONObject x = par.getJSONObject(i);
                    boom[k] = x.getString("ques");k++;
                }



                URL loginurl1 = new URL("http://192.168.43.38/IWP+SE/API/FindName.php");
                y1 = (HttpURLConnection) loginurl1.openConnection();
                y1.connect();

                InputStream stream1 = y1.getInputStream();
                reader1 = new BufferedReader(new InputStreamReader(stream1));

                String line1 = "";
                StringBuffer buffer1 = new StringBuffer( );
                while((line1=reader1.readLine())!=null)
                {
                    buffer1.append(line1);
                }

                String lala1 = buffer1.toString();
                JSONArray par1 = new JSONArray(lala1);
                for(int i=0; i<par1.length(); i++)
                {
                    JSONObject x = par1.getJSONObject(i);

                    if(x.getString("RegNo").equals(toptop)) {
                        boom[0] = x.getString("Name");
                        break;
                    }
                }





                return boom;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(y!=null){
                    y.disconnect();}
                try {
                    if(reader!=null){
                        reader.close();}
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] s) {
            super.onPostExecute(s);

            reg_disp.setText(s[0]);

            String ss[] = new String[s.length-1];
            int k = 0;
            for(int i=1; i<s.length; i++)
            {
                ss[k] = s[i];k++;
            }
            ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, ss);
            lv.setAdapter(ad);
        }
    }
}

/*
    public void logincheck(View view)
    {
        //loginurl = new URL("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");//("192.168.43.38/IWP+SE/API/answerJson.php");
        new JSONTask().execute("http://192.168.43.38/IWP+SE/API/answerJson.php");
    }

public class JSONTask extends AsyncTask<String, String, String>
{

    @Override
    protected String doInBackground(String... urls) {
        HttpURLConnection y = null;
        BufferedReader reader = null;
        try {
            //Toast.makeText(LoginActivity.this, "Yes", Toast.LENGTH_SHORT).show();
            URL loginurl = new URL(urls[0]);
            y = (HttpURLConnection) loginurl.openConnection();
            y.connect();

            InputStream stream = y.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            String line = "";
            StringBuffer buffer = new StringBuffer( );
            while((line=reader.readLine())!=null)
            {
                buffer.append(line);
            }

            String lala = buffer.toString();
            //JSONObject x = new JSONObject(lala);
            JSONArray par = new JSONArray(lala);
            String boom="";
            for(int i=0; i<par.length(); i++)
            {
                JSONObject x = par.getJSONObject(i);
                boom = boom+x.getString("Answer")+"\t";
            }
            return boom;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(y!=null){
                y.disconnect();}
            try {
                if(reader!=null){
                    reader.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        tv.setText(s);

    }
}
*/