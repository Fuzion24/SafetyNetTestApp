package fuzion24.safetynettest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

/**
 * Created by fuzion24 on 1/25/16.
 */
public class SafetyNetRunner {

    private @Nullable
    SafetyNetResponse parseJsonWebSignature(@NonNull String jwsResult) {
        //the JWT (JSON WEB TOKEN) is just a 3 base64 encoded parts concatenated by a . character
        final String[] jwtParts = jwsResult.split("\\.");

        if(jwtParts.length==3) {
            //we're only really interested in the body/payload
            String decodedPayload = new String(Base64.decode(jwtParts[1], Base64.DEFAULT));

            return SafetyNetResponse.parse(decodedPayload);
        }else{
            return null;
        }
    }
    public static void checkSafetyNet(Context ctx){
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
                            Log.d(SAFETYNETTEST, "Got result");


                        } else {
                            Log.d(SAFETYNETTEST, "Did not get result");
                            Log.d(SAFETYNETTEST, status.getStatusMessage());
                            // An error occurred while communicating with the service
                        }
                    }
                });


    }

}
