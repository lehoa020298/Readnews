package lehoa.com.example.readnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Customadapter customadapter;
    ArrayList<Doctintuc> mangdoctintuc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listView);
        mangdoctintuc = new ArrayList<Doctintuc>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Readdata().execute("https://seoulspa.vn/dich-vu/feed/");
            }
        });
    }
    class Readdata extends AsyncTask<String, Integer, String>{
        @Override
        protected String doInBackground(String...params){
            return ReadNews_From_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeList1decription = document.getElementsByTagName("description");
            String image = "";
            String title = "";
            String link = "";
            for(int i=0; i<nodeList.getLength(); i++){
                String cdata = nodeList1decription.item(i+1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(cdata);
                if(matcher.find()){
                    image = matcher.group(1);

                }
                Element element = (Element) nodeList.item(i);
                title += parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                mangdoctintuc.add(new Doctintuc(title, link, image));
            }
            customadapter = new Customadapter(MainActivity.this,android.R.layout.simple_list_item_1,mangdoctintuc);
            listView.setAdapter(customadapter);
            super.onPostExecute(s);

        }
    }
    private String ReadNews_From_URL(String theUrl){
        StringBuilder content = new StringBuilder();
        try{
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)    {
            e.printStackTrace();
        }
        return content.toString();
    }
}
