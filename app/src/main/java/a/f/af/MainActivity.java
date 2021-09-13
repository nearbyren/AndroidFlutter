package a.f.af;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import io.flutter.app.FlutterActivity;
import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.FlutterFragmentActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.view.FlutterMain;

public class MainActivity extends FlutterActivity {
    String sharedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FlutterMain.startInitialization(this); //在这个位置添加这一行即可
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        FlutterFragment fragment=FlutterFragment.withNewEngine().initialRoute("home").build();
//        getSupportFragmentManager().beginTransaction().add(R.id.flutter_container, fragment).commit();
        GeneratedPluginRegistrant.registerWith(new FlutterEngine(this));
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }

        new MethodChannel(getFlutterView(), "app.channel.shared.data").setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                if (methodCall.method.contentEquals("getSharedText")) {
                    result.success(sharedText);
                    sharedText = null;
                }
            }
        });
    }


    void handleSendText(Intent intent) {
        sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
    }
}