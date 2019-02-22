package com.ammar.saifiyahschool.Gallery;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ammar.saifiyahschool.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.iceteck.silicompressorr.SiliCompressor;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.listener.OnSelectedListener;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;

public class AddGallery extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
//    private ImageView imageView;
    private EditText editText;

    //Image request code
    private int PICK_IMAGE_REQUEST = 144;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private static  final int STORAGE_PERMISSION_CODE_Write =456;
    private static final int REQUEST_CODE_CHOOSE = 23;
    String mCurrentPhotoPath;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;
    //    private UriAdapter mAdapter;
    List<Uri> mSelected;
    SharedPreferences sharedPreferences;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    ArrayList<StorageReference> storageReferenceArrayList;
    DatabaseReference databaseReference,albumNames;

    private File actualImage;
    private File compressedImage;
    Toolbar toolbar;
    TextView numberOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gallery);
        //getting the FirebaseStorage & FirebaseDatabase reference
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Add Photos and Videos");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        numberOfItems = findViewById(R.id.numberOfItems);

        //Initializing views
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
//        imageView = (ImageView) findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.editTextName);

        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        //Setting clicklistener
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart(final Uri filePath, final int number) {

        //Set Album names
        if (!TextUtils.isEmpty(editText.getText().toString())){

            albumNames = databaseReference.child(editText.getText().toString());

            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("username",editText.getText().toString());
            edit.apply();
        }else{
            albumNames = databaseReference.child(sharedPreferences.getString("username",null));
        }



        Log.d("Size--->", String.valueOf(mSelected.size()));
        if (filePath != null){


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            String ConvertedPath = getRealPathFromURI(filePath);
            if (ConvertedPath.contains("mp4")){


                String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();

                String f = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();


                new VideoCompressAsyncTask(this,number).execute(ConvertedPath, f);
                progressDialog.dismiss();

            }else{

                try {

                    actualImage = FileUtil.from(this, filePath);
                    compressedImage = new Compressor(this).compressToFile(actualImage);


                    storageReference = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_UPLOADS+editText.getText().toString().trim()+" "+number+"."+getFileExtension(filePath));
                    storageReference.putFile(Uri.fromFile(compressedImage)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            final String sdownload_url = String.valueOf(downloadUrl);
                            Log.i("DownloadURL-->", sdownload_url);
                            Upload upload = new Upload(editText.getText().toString().trim()+" "+number+"."+getFileExtension(filePath), sdownload_url);

                            //adding an upload to firebase database
                            String uploadId = albumNames.push().getKey();
                            albumNames.child(uploadId).setValue(upload);
                        }
                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Toast.makeText(AddGallery.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                UploadTask.TaskSnapshot downloadUri = task.getResult();


                            }else {
                                Toast.makeText(AddGallery.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Log.i("Error--->",String.valueOf(e));
                            Toast.makeText(AddGallery.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }



    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Video.Media.DATA };

        //This method was deprecated in API level 11
        //Cursor cursor = managedQuery(contentUri, proj, null, null, null);

        CursorLoader cursorLoader = new CursorLoader(
                this,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        int column_index =
                cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //method to show file chooser
    private void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        String[] mimetypes = {"image/*", "video/*"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);


    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mSelected = Matisse.obtainResult(data);

            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("items", String.valueOf(mSelected.size()));
            edit.apply();

        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            if (mediaType == TYPE_IMAGE) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
//            } else {

        }else{
            Matisse.from(this)
                    .choose(MimeType.ofAll(),false)
                    .countable(true)
                    .maxSelectable(9)
                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new Glide4Engine())
                    .setOnSelectedListener(new OnSelectedListener() {
                        @Override
                        public void onSelected(
                                @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                            // DO SOMETHING IMMEDIATELY HERE
                            Log.e("onSelected", "onSelected: pathList=" + pathList);

                        }
                    })
                    .forResult(REQUEST_CODE_CHOOSE);


        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Pause--->", "onPause: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Start--->", "onStart: "+ sharedPreferences.getString("items",null));

        String items = sharedPreferences.getString("items",null);
//        Log.i("item->",items);
//        numberOfItems.setText("45");

        if (items !=  null){

        if (Integer.parseInt(items) <= 9){
            numberOfItems.setText("0"+items);
            numberOfItems.setTextSize(200);
            numberOfItems.setTextColor(getResources().getColor(R.color.gray_color));
        }else {
            numberOfItems.setText(items);
            numberOfItems.setTextSize(200);
            numberOfItems.setTextColor(getResources().getColor(R.color.gray_color));
        }

    }else {
        numberOfItems.setTextSize(24);
        numberOfItems.setText("No Pictures and Videos you have been selected yet!");
        numberOfItems.setTextColor(getResources().getColor(R.color.cell_background_color));

    }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stop--->", "onStop: ");
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove("items");
        edit.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Resume--->", "onResume: ");

//        String items = String.valueOf(mSelected.size());
//        Log.i("item->",items);


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Matisse.from(this)
                            .choose(MimeType.ofAll(),false)
                            .countable(true)
                            .maxSelectable(9)
                            .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                            .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new Glide4Engine())
                            .setOnSelectedListener(new OnSelectedListener() {
                                @Override
                                public void onSelected(
                                        @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                                    // DO SOMETHING IMMEDIATELY HERE
                                    Log.e("onSelected", "onSelected: pathList=" + pathList);

                                }
                            })
                            .forResult(REQUEST_CODE_CHOOSE);
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();



                } else {
                    Toast.makeText(this, "You need to enable the permission for External Storage Write" +
                            " to test out this library.", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            }

            default:
        }
    }



    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {

            requestStoragePermission();

        }
        if (v == buttonUpload) {

            for (int i=0;i<mSelected.size();i++){
                uploadMultipart(mSelected.get(i),i);
            }
        }
    }

    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;
        ProgressDialog progressDialog;
        int Number;
        ArrayList<Uri> uriStore = new ArrayList<>();


        public VideoCompressAsyncTask(Context context,int Number) {
            mContext = context;
            Number = Number;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddGallery.this);
            progressDialog.setTitle("Compressing Video!");
            progressDialog.setMessage("Please Wait....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {

                filePath = SiliCompressor.with(mContext).compressVideo(paths[0], paths[1]);


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Log.i("Test_URI-->",filePath);

            return filePath;

        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);



            final File fileOnly = new File(compressedFilePath);
            MediaScannerConnection.scanFile(mContext, new String[] { fileOnly.getAbsolutePath() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("let's Try==>", "Scanned " + uri);
                            storageReference = FirebaseStorage.getInstance().getReference(Constants.STORAGE_PATH_UPLOADS+fileOnly.getName());

                            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                    while (!urlTask.isSuccessful());
                                    Uri downloadUrl = urlTask.getResult();

                                    final String sdownload_url = String.valueOf(downloadUrl);
                                    Log.i("DownloadURL-->", sdownload_url);
                                    Upload upload = new Upload(fileOnly.getName(), sdownload_url);
//                            Upload upload = new Upload(editText.getText().toString().trim(), Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl().toString());

                                    //adding an upload to firebase database
                                    String uploadId = albumNames.push().getKey();
                                    albumNames.child(uploadId).setValue(upload);
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(AddGallery.this, "Uploaded", Toast.LENGTH_SHORT).show();
                                        UploadTask.TaskSnapshot downloadUri = task.getResult();



                                    }else {
                                        Toast.makeText(AddGallery.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Log.i("Error--->",String.valueOf(e));
                                    Toast.makeText(AddGallery.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                                    progressDialog.setTitle("Uploading Video");
                                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                }
                            });

                        }
                    });



            Log.i("Silicompressor", "Path: " + compressedFilePath);


        }

    }


}