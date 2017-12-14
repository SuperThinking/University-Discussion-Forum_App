package straightforwardapps.first_networking;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class AnswerActivity extends AppCompatActivity {


    ListView ls;
    TextView sq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        ls = (ListView) findViewById(R.id.ans);
        sq = (TextView) findViewById(R.id.sq);
        //Toast.makeText(this, getIntent().getStringExtra("qid"), Toast.LENGTH_SHORT).show();

        new ansfetch().execute();
    }

    public class ansfetch extends AsyncTask<Void, String, String[]>
    {

        String qid=getIntent().getStringExtra("qid");
        String question="";

        @Override
        protected String[] doInBackground(Void... voids) {
            try {
                URL url = new URL("http://192.168.43.38/IWP+SE/API/answerJson.php");

                HttpURLConnection x = (HttpURLConnection) url.openConnection();
                x.connect();

                BufferedReader reader;
                InputStream stream = x.getInputStream();
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
                int k=0, len=0;
                for(int i=0; i<par.length(); i++)
                {
                    JSONObject y = par.getJSONObject(i);
                    if(qid.equals(y.getString("Q_id"))) {
                        len++;
                    }
                }

                if(len>0)
                {
                String[] boom=new String[len];
                for(int i=0; i<par.length(); i++)
                {
                    JSONObject y = par.getJSONObject(i);
                    if(qid.equals(y.getString("Q_id"))) {
                        boom[k] = y.getString("Answer");
                        k++;
                    }
                }

                    x.disconnect();

                    url = new URL("http://192.168.43.38/IWP+SE/API/ConverttoJson.php");
                    x = (HttpURLConnection) url.openConnection();
                    x.connect();


                    stream = x.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    line = "";
                    buffer = new StringBuffer( );
                    while((line=reader.readLine())!=null)
                    {
                        buffer.append(line);
                    }

                    lala = buffer.toString();
                    //JSONObject x = new JSONObject(lala);

                    par = new JSONArray(lala);
                    k=0; len=0;
                    for(int i=0; i<par.length(); i++)
                    {
                        JSONObject y = par.getJSONObject(i);
                        if(qid.equals(y.getString("Q_id"))) {
                            question = y.getString("ques");
                            break;
                        }
                    }



            return boom;}
                else
                {
                    x.disconnect();

                    url = new URL("http://192.168.43.38/IWP+SE/API/ConverttoJson.php");
                    x = (HttpURLConnection) url.openConnection();
                    x.connect();


                    stream = x.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    line = "";
                    buffer = new StringBuffer( );
                    while((line=reader.readLine())!=null)
                    {
                        buffer.append(line);
                    }

                    lala = buffer.toString();
                    //JSONObject x = new JSONObject(lala);

                    par = new JSONArray(lala);
                    k=0; len=0;
                    for(int i=0; i<par.length(); i++)
                    {
                        JSONObject y = par.getJSONObject(i);
                        if(qid.equals(y.getString("Q_id"))) {
                            question = y.getString("ques");
                            break;
                        }
                    }



                    String[] boom=new String[1];
                    boom[0] = "poop";
                    return boom;
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);

            sq.setText(question);

            if(strings[0].equals("poop")) {
                strings[0] = "NOT YET ANSWERED";
                ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), R.layout.questions, strings);
                ls.setAdapter(ad);
                ls.setDividerHeight(40);
            }
            else {
                ArrayAdapter<String> ad = new ArrayAdapter<String>(getApplicationContext(), R.layout.questions, strings);
                ls.setAdapter(ad);
                ls.setDividerHeight(40);
            }
        }
    }
}
