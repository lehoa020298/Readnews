package lehoa.com.example.readnews;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Customadapter extends ArrayAdapter<Doctintuc>{
    public Customadapter(Context context, int resource, List<Doctintuc> items){
        super (context, resource, items);
    }
    @Override
    public View getView( int position,  View convertView, ViewGroup parent){
        View view = convertView;
        if(view ==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.dong_layout_listview, null);
        }
        Doctintuc d = getItem(position);
        if( d != null){
            TextView txttitle = (TextView) view.findViewById(R.id.textviewtitle);
            txttitle.setText(d.title);
            ImageView imageView = view.findViewById(R.id.imageView);
            Picasso.with(getContext()).load(d.image).into(imageView);
        }
        return view;
    }
}
