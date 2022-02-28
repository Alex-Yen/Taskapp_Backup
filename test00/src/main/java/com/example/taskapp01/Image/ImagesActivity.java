package com.example.taskapp01.Image;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taskapp01.Admin.Admin;
import com.example.taskapp01.R;
import com.example.taskapp01.User.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//homepage
public class ImagesActivity extends Activity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGES = 2;
    public static final int STORAGE_PERMISSION = 100;

    ArrayList<ImageModel> imageList;
    ArrayList<String> selectedImageList;
    RecyclerView imageRecyclerView, selectedImageRecyclerView;
    int[] resImg = {R.drawable.ic_camera_white_30dp, R.drawable.ic_folder_white_30dp};
    String[] title = {"Camera", "Folder"};
    String mCurrentPhotoPath, image0, image1, image2;
    SelectedImageAdapter selectedImageAdapter;
    ImageAdapter imageAdapter;
    String[] projection = {MediaStore.MediaColumns.DATA};
    File image;
    Button done;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        if (verifyPermission()) {
            init();
            getAllImages();
            setImageList();
            setSelectedImageList();
        }
    }

    public void init() {
        imageRecyclerView = findViewById(R.id.recycler_view);
        selectedImageRecyclerView = findViewById(R.id.selected_recycler_view);
        done = findViewById(R.id.done);
        selectedImageList = new ArrayList<>();
        imageList = new ArrayList<>();

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImageList.size() <= 3) {
                    SelectedImageList();
                    //shared preferences (Name, Mode)
                    sharedPreferences = getSharedPreferences("User_type", MODE_PRIVATE);
                    //get data from shared preference
                    String User_type = sharedPreferences.getString("User_type", "");
                    assert User_type != null;
                    if (User_type.contentEquals("Admin")) {
                        startActivity(new Intent(ImagesActivity.this, Admin.class));
                    } else if (User_type.contentEquals("User")) {
                        startActivity(new Intent(ImagesActivity.this, User.class));
                    }
                }
                else {
                    Toast.makeText(ImagesActivity.this, "Please pick 3 images ONLY", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //bottom recycleview
    public void setImageList() {
        imageRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        imageAdapter = new ImageAdapter(getApplicationContext(), imageList);
        imageRecyclerView.setAdapter(imageAdapter);

        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (position == 0) {
                    takePicture();
                } else if (position == 1) {
                    getPickImageIntent();
                } else {
                    try {
                        if (!imageList.get(position).isSelected) {
                            selectImage(position);
                        } else {
                            unSelectImage(position);
                        }
                    } catch (ArrayIndexOutOfBoundsException ed) {
                        ed.printStackTrace();
                    }
                }
            }
        });
        setImagePickerList();
    }

    //upper recycleview
    public void setSelectedImageList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        selectedImageRecyclerView.setLayoutManager(layoutManager);
        selectedImageAdapter = new SelectedImageAdapter(this, selectedImageList);
        selectedImageRecyclerView.setAdapter(selectedImageAdapter);
    }

    // Add Camera and Folder in ArrayList
    public void setImagePickerList() {
        for (int i = 0; i < resImg.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setResImg(resImg[i]);
            imageModel.setTitle(title[i]);
            imageList.add(i, imageModel);
        }
        imageAdapter.notifyDataSetChanged();
    }

    // ADD all images from external storage
    public void getAllImages() {
        imageList.clear();
        final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC LIMIT 20";
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, orderBy);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = "file://" + cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            ImageModel ImageModel = new ImageModel();
            ImageModel.setImage(absolutePathOfImage);
            imageList.add(ImageModel);
        }
        cursor.close();
    }

    // start the image capture Intent
    public void takePicture() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Camera need permission to proceed", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // Continue only if the File was successfully created;
            File photoFile = createImageFile();
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void getPickImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES);
    }

    // Add image in selectedImageList
    public void selectImage(int position) {
        // Check before add new item in ArrayList;
        if (!selectedImageList.contains(imageList.get(position).getImage())) {
            imageList.get(position).setSelected(true);
            selectedImageList.add(0, imageList.get(position).getImage());
            selectedImageAdapter.notifyDataSetChanged();
            imageAdapter.notifyDataSetChanged();
            if (!imageList.get(position).getImage().contains("file://")) {
                Toast.makeText(ImagesActivity.this, "This image might be corrupted! Please select replacement...", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Remove image from selectedImageList
    public void unSelectImage(int position) {
        for (int i = 0; i < selectedImageList.size(); i++) {
            if (imageList.get(position).getImage() != null) {
                if (selectedImageList.get(i).equals(imageList.get(position).getImage())) {
                    imageList.get(position).setSelected(false);
                    selectedImageList.remove(i);
                    selectedImageAdapter.notifyDataSetChanged();
                    imageAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public File createImageFile() {
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + dateTime + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file:// path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file://" + image;
        Toast.makeText(ImagesActivity.this, mCurrentPhotoPath, Toast.LENGTH_LONG).show();
        return image;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    addImage(mCurrentPhotoPath);
                }
            } else if (requestCode == PICK_IMAGES) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        getImageFilePath(uri);
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    getImageFilePath(uri);
                }
            }
        }
    }

    // Get image file path
    public void getImageFilePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                if (absolutePathOfImage != null) {
                    checkImage("file://" + absolutePathOfImage);
                } else {
                    checkImage("file://" + uri);
                }
            }
        }
    }

    public void checkImage(String filePath) {
        // Check before adding a new image to ArrayList to avoid duplicate images
        if (!selectedImageList.contains(filePath)) {
            for (int pos = 0; pos < imageList.size(); pos++) {
                if (imageList.get(pos).getImage() != null) {
                    if (imageList.get(pos).getImage().equalsIgnoreCase(filePath)) {
                        imageList.remove(pos);
                    }
                }
            }
            addImage(filePath);
        }
    }

    public void SelectedImageList () {
        //Check all selected image before post
        if (selectedImageList.size()==0) {
            image0 = null;
            image1 = null;
            image2 = null;
        }

        else if (selectedImageList.size()==1) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(Uri.parse(selectedImageList.get(0)));
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                encodeBitmapImage(bitmap);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Uri conversion unsuccesful", Toast.LENGTH_LONG).show();
            }
            image1 = null;
            image2 = null;
        }

        else if (selectedImageList.size()==2) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(Uri.parse(selectedImageList.get(0)));
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                encodeBitmapImage(bitmap);

                InputStream inputStream1 = getContentResolver().openInputStream(Uri.parse(selectedImageList.get(1)));
                Bitmap bitmap1= BitmapFactory.decodeStream(inputStream1);
                encodeBitmapImage1(bitmap1);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Uri conversion unsuccesful", Toast.LENGTH_LONG).show();
            }
            image2 = null;
        }

        else if (selectedImageList.size()==3) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(Uri.parse(selectedImageList.get(0)));
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                encodeBitmapImage(bitmap);

                InputStream inputStream1 = getContentResolver().openInputStream(Uri.parse(selectedImageList.get(1)));
                Bitmap bitmap1= BitmapFactory.decodeStream(inputStream1);
                encodeBitmapImage1(bitmap1);

                InputStream inputStream2 = getContentResolver().openInputStream(Uri.parse(selectedImageList.get(2)));
                Bitmap bitmap2= BitmapFactory.decodeStream(inputStream2);
                encodeBitmapImage2(bitmap2);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Uri conversion unsuccesful", Toast.LENGTH_LONG).show();
            }
        }
        Image_Detail();
    }

    // add image in selectedImageList and imageList
    public void addImage(String filePath) {
        ImageModel imageModel = new ImageModel();
        imageModel.setImage(filePath);
        imageModel.setSelected(true);
        imageList.add(2, imageModel);
        selectedImageList.add(0, filePath);
        selectedImageAdapter.notifyDataSetChanged();
        imageAdapter.notifyDataSetChanged();
    }

    public boolean verifyPermission() {
        String [] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ImagesActivity.this, permissions, STORAGE_PERMISSION);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            getAllImages();
            setImageList();
            setSelectedImageList();
        }
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        image0=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void encodeBitmapImage1(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        image1=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void encodeBitmapImage2(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        image2=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void Image_Detail() {
        final ProgressDialog progressDialog = new ProgressDialog(ImagesActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering New Job Description");
        progressDialog.show();
        String url = "http://192.168.0.105/taskapp00/register_job.php";

        //shared preferences (Name, Mode)
        sharedPreferences = getSharedPreferences("Add_job_fx", MODE_PRIVATE);

        //get data from shared preference
        final String Usertype = sharedPreferences.getString("Usertype", "");
        final String Username = sharedPreferences.getString("Username", "");
        final String House = sharedPreferences.getString("House", "");
        final String House_Area = sharedPreferences.getString("House Area", "");
        final String Tenant_tel_no = sharedPreferences.getString("Tenant tel no", "");
        final String Job = sharedPreferences.getString("Job", "");
        final String Location = sharedPreferences.getString("Location", "");

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("File Uploaded Successfully")) {
                    progressDialog.dismiss();
                    Toast.makeText(ImagesActivity.this, response, Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(ImagesActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();

                param.put("Usertype", Usertype);
                param.put("Username", Username);
                param.put("House", House);
                param.put("House_Area", House_Area);
                param.put("Tenant_tel_no", Tenant_tel_no);
                param.put("Job", Job);
                param.put("Location", Location);

                //add random 6 character to name
                String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "IMG_" + dateTime + "_";

                if (image0!=null) {
                    param.put("Image0", image0);
                    param.put("Image_name0", imageFileName + "00");
                }

                else {
                    param.put("Image0", "");
                    param.put("Image_name0", "");
                }

                if (image1!=null) {
                    param.put("Image1", image1);
                    param.put("Image_name1", imageFileName + "01");
                }

                else {
                    param.put("Image1", "");
                    param.put("Image_name1", "");
                }

                if (image2!=null) {
                    param.put("Image2", image2);
                    param.put("Image_name2", imageFileName + "02");
                }

                else {
                    param.put("Image2", "");
                    param.put("Image_name2", "");
                }

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ImagesActivity.this);
        requestQueue.add(request);
    }
}
