package framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.R;
import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.data.Contact;

/**
 * Created by thanhthi on 02/11/2017.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<Contact> mContacts;
    private LayoutInflater mInflater;

    public ContactAdapter(List<Contact> contacts) {
        mContacts = contacts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mContacts.get(position));
    }

    @Override
    public int getItemCount() {
        return mContacts != null ? mContacts.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private TextView mTextPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextName = itemView.findViewById(R.id.text_name);
            mTextPhone = itemView.findViewById(R.id.text_phone);
        }

        public void bindData(Contact contact) {
            if (contact == null) return;
            mTextName.setText(contact.getName());
            mTextPhone.setText(contact.getPhone());
        }
    }
}
