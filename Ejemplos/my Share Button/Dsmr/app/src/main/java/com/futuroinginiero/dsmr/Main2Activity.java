package com.futuroinginiero.dsmr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;


public class Main2Activity extends AppCompatActivity {


    CallbackManager callbackManager;
    ShareDialog shareDialog;


    String url_imagen;

    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String name=getIntent().getStringExtra("NOMBRE");

        TextView user_name=(TextView)findViewById(R.id.text_name_user);
        user_name.setText(name);

        imagen=(ImageView)findViewById(R.id.imageView_user);

        url_imagen=getIntent().getStringExtra("FOTO");

        DescargaImagen m=new DescargaImagen();
        m.execute();



        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);




        ShareButton shareButtonLink = (ShareButton)findViewById(R.id.fb_share_link);
        ShareButton shareButtonImagen = (ShareButton)findViewById(R.id.fb_share_imagen);



        ShareLinkContent content_link = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();


        Bitmap image = BitmapFactory.decodeResource(Main2Activity.this.getResources(),R.drawable.android);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .build();

        SharePhotoContent content_imagen = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();



        shareButtonLink.setShareContent(content_link);
        shareButtonImagen.setShareContent(content_imagen);





    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }




    public void compartir_link(View v){

        if (ShareDialog.canShow(ShareLinkContent.class)){
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Hello Facebook")
                    .setContentDescription(
                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

            shareDialog.show(linkContent);

        }

        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    public void compartir_imagen(View v){

        if (ShareDialog.canShow(SharePhotoContent.class)){

            Bitmap bitmap =BitmapFactory.decodeResource(Main2Activity.this.getResources(),R.drawable.android);

            SharePhoto foto=new SharePhoto.Builder().setBitmap(bitmap).build();

            SharePhotoContent content= new SharePhotoContent.Builder().addPhoto(foto).build();

            shareDialog.show(content);

        }





        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }



    class DescargaImagen extends AsyncTask<Void,Void,Void>{

        Bitmap descarga =null;
        @Override
        protected Void doInBackground(Void... params) {

            try {//descargamos la imagen de perfil del usuario
                descarga = Picasso.with(Main2Activity.this).load(url_imagen).get();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {

           if(descarga!=null){
               imagen.setImageBitmap(descarga);
           }
            super.onPostExecute(aVoid);
        }
    }



}
