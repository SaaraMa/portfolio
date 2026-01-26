package lut.student.mybookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ItemAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    String[] names;
    String[] authors;

    public ItemAdapter(Context c, String[] n, String[] a) {
        names = n;
        authors = a;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return names[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.my_listview_detail, null);
        TextView nameTextView = (TextView) v.findViewById(R.id.nameTextView);
        TextView authorTextView = (TextView) v.findViewById(R.id.authorTextView);

        String name = names[i];
        String author = authors[i];

        nameTextView.setText(name);
        authorTextView.setText(author);
        return v;
    }
}
