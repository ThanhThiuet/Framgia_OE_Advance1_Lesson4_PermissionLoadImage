package framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.screen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.R;
import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.adapters.ContactAdapter;
import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.adapters.ImageAdapter;
import framgia.uet.nguyenthanhthi.advance1_lesson4_permissionloadimage.data.Contact;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_READ_CONTACT = 1;
    private static final int REQUEST_READ_STORAGE = 2;

    private RecyclerView mRecyclerContact;
    private Button button_show_contact;
    private Button button_show_image;

    private List<Contact> mContacts;
    private List<String> mImagePaths;

    private ContactAdapter mContactAdapter;
    private ImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_show_contact:
                requestContactPermission();
                break;
            case R.id.button_show_image:
                requestImagePermission();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, 
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACT:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImages();
                } else {
                    Toast.makeText(this, "Permission Read Contact Deny!", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_READ_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showImages();
                } else {
                    Toast.makeText(this, "Permission Read Storage Deny!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void requestContactPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                "Manifest.permission.READ_CONTACTS");
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            showContacts();
        } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Request Contact Permission")
                        .setMessage("We want to access to your contact data")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{ Manifest.permission.READ_CONTACTS },
                                        REQUEST_READ_CONTACT);
                            }
                        })
                        .setNegativeButton("No", null)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{ Manifest.permission.READ_CONTACTS },
                        REQUEST_READ_CONTACT);
            }
        }
    }

    private void requestImagePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                "Manifest.permistion.READ_EXTERNAL_STORAGE");
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            showImages();
        } else if (permissionCheck == PackageManager.PERMISSION_DENIED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Request Image Permission")
                        .setMessage("We want to access to your external storage to get image")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                                        REQUEST_READ_STORAGE);
                            }
                        })
                        .setNegativeButton("No", null)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE },
                        REQUEST_READ_STORAGE);
            }
        }
    }

    private void showContacts() {
        mContacts.clear();
        mContacts.addAll(getContacts());
        mContactAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerContact.setLayoutManager(layoutManager);
        mRecyclerContact.setAdapter(mContactAdapter);
    }

    private void showImages() {
        mImagePaths.clear();
        mImagePaths.addAll(getImagePaths());
        mImageAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerContact.setLayoutManager(layoutManager);
        mRecyclerContact.setAdapter(mImageAdapter);
    }

    private List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String colName = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;
        String colPhone = ContactsContract.CommonDataKinds.Phone.NUMBER;
        String[] projection = { colName, colPhone };

        Cursor cursor = getContentResolver().query(uri, projection,
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                String phone = cursor.getString(1);
                contacts.add(new Contact(name, phone));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return contacts;
    }

    private List<String> getImagePaths() {
        List<String> imagePaths = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(uri, projection,
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int indexImagePathColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                imagePaths.add(cursor.getString(indexImagePathColumn));
            } while (cursor.moveToNext());

            cursor.close();
        }

        return imagePaths;
    }

    private void initView() {
        mRecyclerContact = findViewById(R.id.recycler_contact);
        button_show_contact = findViewById(R.id.button_show_contact);
        button_show_image = findViewById(R.id.button_show_image);

        mContacts = new ArrayList<>();
        mImagePaths = new ArrayList<>();

        mContactAdapter = new ContactAdapter(mContacts);
        mImageAdapter = new ImageAdapter(mImagePaths);

        button_show_contact.setOnClickListener(this);
        button_show_image.setOnClickListener(this);
    }

}
