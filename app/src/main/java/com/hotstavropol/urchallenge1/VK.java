package com.hotstavropol.urchallenge1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

/**
 * Created by maximgran on 13.01.2018.
 */

public class VK extends AppCompatActivity {
    private String[] scope = new String[]{VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};
    private ListView listView;
    public String id;
    public static VKAccessToken vkAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //       setContentView(R.layout.vkapi);
        VKSdk.login(this, scope);
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                vkAccessToken = res;
                DataBase.vk_permission = true;
                Toast.makeText(getApplicationContext(), "Авторизация прошла успешно", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(VK.this, MenuActivity.class);
                startActivity(intent);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(getApplicationContext(), "Ошибка авторизации!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(VK.this, MenuActivity.class);
                startActivity(intent);
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}




/*VKRequest vkRequest = VKApi.users().get(VKParameters.from(VKApiConst.USER_IDS, id));
                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(VKResponse response) {
                        final VKApiUser vkApiUser = ((VKList<VKApiUser>) response.parsedModel).get(0);

                        new Thread() {
                            public void run() {
                                try {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final TextView textView = findViewById(R.id.profile_name);
                                            textView.setText(vkApiUser.first_name + " " + vkApiUser.last_name);
                                            Log.d("NAME", textView.getText().toString());
                                        }
                                    });
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
*/