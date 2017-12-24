package straightforwardapps.first_networking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;

public class SuccessLogin extends AppCompatActivity {


    SessionManagement session;
    RelativeLayout baad;
    private static final String TAG = SuccessLogin.class.getSimpleName();
    TextView reg_disp, load;
    Boolean dadak=false;
    ListView lv;
    Button lin;
    String regreg="";//getIntent().getStringExtra("regno");;
    android.support.v7.widget.Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //if(isInternetAvailable(this)) {
            //new internetcheck().execute();
            setContentView(R.layout.activity_success_login);

            //new internetcheck().execute();



            reg_disp = (TextView) findViewById(R.id.reg_disp);
            baad = (RelativeLayout) findViewById(R.id.baad);
            lin = (Button) findViewById(R.id.lin);
            load = (TextView) findViewById(R.id.load);

            session = new SessionManagement(getApplicationContext());
            session.checkLogin();

            try {
                HashMap<String, String> user = session.getUserDetails();

                String regnumb = user.get(SessionManagement.KEY_NAME);
                String email = user.get(SessionManagement.KEY_PASS);

                regreg = regnumb;

                lv = (ListView) findViewById(R.id.lv);
                String toptop = regreg.toString();//getIntent().getStringExtra("regno");
                reg_disp.setText(toptop);

                String lru = "http://192.168.43.38/IWP+SE/API/ConverttoJson.php";
                new JSONTask().execute(lru);
            } catch (Exception e) {
                Intent lo = new Intent(this, MainActivity.class);
                startActivity(lo);
                finish();
            }
        //}

        /*else
        {
            Toast.makeText(this, "Check Internet", Toast.LENGTH_SHORT).show();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
        }*/

    }


        public static boolean isInternetAvailable(Context context) {
            NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

            if (info == null) {
                Log.d(TAG, "no internet connection");
                return false;
            } else {
                if (info.isConnected()) {
                    Log.d(TAG, " internet connection available...");
                    return true;
                } else {
                    Log.d(TAG, " internet connection");
                    return true;
                }

            }
        }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Really!?")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            //Toast.makeText(getApplicationContext(), "BACK", Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(a);
                            //return true;
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            //Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public void logout(View view)
    {
        session.logoutUser();
    }

    public void refresh(View view)
    {
        String lru = "http://192.168.43.38/IWP+SE/API/ConverttoJson.php";
        new JSONTask().execute(lru);
    }

    public class JSONTask extends AsyncTask<Object, String, String[]>
    {

        //String toptop = getIntent().getStringExtra("regno");
        String toptop1 = regreg.toString();
        String[] qid;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            load.setVisibility(View.VISIBLE);
        }

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
                qid = new String[par.length()+1];
                qid[0] = "qid";
                int k=1;
                for(int i=0; i<par.length(); i++)
                {
                    JSONObject x = par.getJSONObject(i);
                    boom[k] = x.getString("ques");
                    qid[k] = x.getString("Q_id");
                    k++;
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

                    if(x.getString("RegNo").equals(toptop1)) {
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
            load.setVisibility(View.GONE);
            baad.setVisibility(View.VISIBLE);
            reg_disp.setText(s[0]);
            String ss[] = new String[s.length-1];
            int k = 0;
            for(int i=1; i<s.length; i++)
            {
                ss[k] = s[i];k++;
            }
            ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), R.layout.questions, ss);
            lv.setAdapter(ad);
            lv.setDividerHeight(40);


            int x=0;
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent ans = new Intent(getApplicationContext(), AnswerActivity.class);

                    ans.putExtra("qid", qid[i+1]);

                    startActivity(ans);
                }
            });
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