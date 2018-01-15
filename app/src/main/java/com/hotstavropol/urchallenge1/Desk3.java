package com.hotstavropol.urchallenge1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by maximgran on 13.01.2018.
 */

public class Desk3 extends Fragment {
    final static int REQUEST_CODE = 1337;
    private View inflate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.desk3, container, false);

        if (VkBase.name.equals("") == false) {
            TextView textView = inflate.findViewById(R.id.name);
            textView.setText(VkBase.name);
        }

        Button vkbutton = inflate.findViewById(R.id.VkButton);
        vkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VK.class);
                startActivity(intent);
            }
        });

        Button sync_with_vk = (Button) inflate.findViewById(R.id.sync_with_vk);
        sync_with_vk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataBase.vk_permission) {
                    if (VkBase.name.equals("")) {
                        VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, VK.vkAccessToken.userId));
                        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                            @Override
                            //Добавка Имени и Аватара пользователя из ВК.
                            public void onComplete(VKResponse response) {
                                VKApiUser vkApiUser = ((VKList<VKApiUser>) response.parsedModel).get(0);
                                TextView textView = inflate.findViewById(R.id.name);
                                textView.setText(vkApiUser.first_name + " " + vkApiUser.last_name);
                                //  ImageView imageView = inflate.findViewById(R.id.profile_photo);
                                //  imageView.setImageBitmap();
                                super.onComplete(response);
                            }
                        });
                    } else {
                        TextView textView = inflate.findViewById(R.id.name);
                        textView.setText(VkBase.name);
                    }
                } else {
                    Toast.makeText(getContext(), "Авторизуйтесь через ВКОНТАКТЕ", Toast.LENGTH_LONG).show();
                }
            }
        });
        return inflate;
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            ImageView imageView = (ImageView)inflate.findViewById(R.id.profile_photo);
            uri = data.getData();

            try {
                InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
