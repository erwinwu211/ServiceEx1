package jp.ac.titech.itpro.sdl.serviceex1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static int REQCODE_TEST3 = 3333;

    private BroadcastReceiver bcReceiver;
    private IntentFilter bcScanFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate in " + Thread.currentThread());
        setContentView(R.layout.activity_main);

        bcReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "onReceive in " + Thread.currentThread());
                String action = intent.getAction();
                if (action == null) return;
                switch (action) {
                case TestService3.ACTION_ANSWER:
                    Log.d(TAG, "bcReceiver in " + Thread.currentThread());
                    String answer = intent.getStringExtra(TestService3.EXTRA_ANSWER);
                    Log.d(TAG, "answer = " + answer);
                    Toast.makeText(context, "answer = " + answer,
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.d(TAG, "bcReceiver [unexpected action] in " + Thread.currentThread());
                    break;
                }
            }
        };
        bcScanFilter = new IntentFilter();
        bcScanFilter.addAction(TestService3.ACTION_ANSWER);
    }

    public void pressTest1(View v) {
        testService1();
    }

    public void pressTest2(View v) {
        testService2();
    }

    public void pressTest3(View v) {
        testService3();
    }

    private void testService1() {
        Log.d(TAG, "testService1 in " + Thread.currentThread());
        Intent intent = new Intent(this, TestService1.class);
        intent.putExtra(TestService1.EXTRA_MYARG, "Hello, Service1");
        startService(intent);
    }

    private void testService2() {
        Log.d(TAG, "testService2 in " + Thread.currentThread());
        Intent intent = new Intent(this, TestService2.class);
        intent.putExtra(TestService2.EXTRA_MYARG, "Hello, Service2");
        startService(intent);
    }

    private void testService3() {
        Log.d(TAG, "testService3 in " + Thread.currentThread());
        Intent intent = new Intent(this, TestService3.class);
        intent.putExtra(TestService3.EXTRA_MYARG, "Hello, Service3");
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        registerReceiver(bcReceiver, bcScanFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        unregisterReceiver(bcReceiver);
    }
}
