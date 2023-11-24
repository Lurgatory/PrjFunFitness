package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.prjweightrecords.R;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    Context context;
    ArrayList<Contact> contactArrayList;

    Contact contact;

    public ContactAdapter() {
    }

    public ContactAdapter(Context context, ArrayList<Contact> contactArrayList) {
        this.context = context;
        this.contactArrayList = contactArrayList;
    }

    @Override
    public int getCount() {
        return contactArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return contactArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View oneContact;

        TextView tvFirstName, tvLastName;

        LayoutInflater inflater = LayoutInflater.from(context);

        oneContact = inflater.inflate(R.layout.one_contact,viewGroup,false);

        tvFirstName = oneContact.findViewById(R.id.tvFirstName);
        tvLastName = oneContact.findViewById(R.id.tvLastName);

        contact = (Contact) getItem(i);

        tvFirstName.setText(contact.getFirstName());
        tvLastName.setText(contact.getLastName());

        return oneContact;
    }
}
