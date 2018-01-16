package com.hotstavropol.urchallenge1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Created by maximgran on 13.01.2018.
 */

public class Desk3 extends Fragment {
    final static int REQUEST_CODE = 1337;
    private View inflate;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.contains("VK_user_token")) {
            HashMap<String, String> d = new HashMap<>();
            d.put(VKAccessToken.USER_ID, sharedPreferences.getString("VK_user_id", null));
            d.put(VKAccessToken.EMAIL, sharedPreferences.getString("VK_user_email", null));
            d.put(VKAccessToken.SECRET, sharedPreferences.getString("VK_user_secret", null));
            d.put(VKAccessToken.ACCESS_TOKEN, sharedPreferences.getString("VK_user_token", null));
            d.put(VKAccessToken.EXPIRES_IN, sharedPreferences.getString("VK_user_expires", null));
            VK.vkAccessToken = VKAccessToken.tokenFromParameters(d);
            DataBase.vk_permission = true;
            FetchVkData();
        }

        inflate = inflater.inflate(R.layout.desk333, container, false);

        if (VkBase.name.equals("") == false) {
            TextView textView = inflate.findViewById(R.id.name);
            textView.setText(VkBase.name);
        }

        Button vkbutton = inflate.findViewById(R.id.VkButton);
        vkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), VK.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return inflate;
    }

    private void FetchVkData() {
        if (DataBase.vk_permission) {
            if (VkBase.name.equals("")) {
                VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_ID, VK.vkAccessToken.userId));
                VKRequest vkRequest_photo = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS, "photo_200"));

                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    //Добавка Имени пользователя из ВК.
                    public void onComplete(VKResponse response) {
                        VKApiUser vkApiUser = ((VKList<VKApiUser>) response.parsedModel).get(0);
                        TextView textView = inflate.findViewById(R.id.name);
                        textView.setText(vkApiUser.first_name + " " + vkApiUser.last_name);
                        super.onComplete(response);
                    }
                });

                vkRequest_photo.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        VKApiUser vkApiUser = ((VKList<VKApiUser>) response.parsedModel).get(0);
                        ImageView imageView = inflate.findViewById(R.id.profile_photo);
                        String urlImage = vkApiUser.photo_200;
                        Picasso.with(getContext())
                                .load(urlImage)
                                .placeholder(R.drawable.land)
                                .error(R.drawable.land)
                                .into(imageView);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE) {
            FetchVkData();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("VK_user_token", VK.vkAccessToken.accessToken);
            editor.putString("VK_user_secret", VK.vkAccessToken.secret);
            editor.putString("VK_user_id", VK.vkAccessToken.userId);
            editor.putString("VK_user_email", VK.vkAccessToken.email);
            editor.putString("VK_user_expires", String.valueOf(VK.vkAccessToken.expiresIn));

            editor.apply();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
