package fuzion24.safetynettest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private static void checkSafteyNet(Context ctx){
        final String SAFETYNETTEST = "SafetyNetTest";
        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(ctx)
                .addApi(SafetyNet.API)
                .build();


        mGoogleApiClient.connect();
        //byte[] nonce = getRequestNonce(); // Should be at least 16 bytes in length.
        byte[] nonce = "Should be at least 16 bytes in length".getBytes();
        SafetyNet.SafetyNetApi.attest(mGoogleApiClient, nonce)
                .setResultCallback(new ResultCallback<SafetyNetApi.AttestationResult>() {

                    @Override
                    public void onResult(@NonNull SafetyNetApi.AttestationResult result) {
                        Status status = result.getStatus();
                        if (status.isSuccess()) {
                            // Indicates communication with the service was successful.
                             Log.d(SAFETYNETTEST, result.getJwsResult());

                        } else {
                            Log.d(SAFETYNETTEST, status.getStatusMessage());
                            // An error occurred while communicating with the service
                        }
                    }
                });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
