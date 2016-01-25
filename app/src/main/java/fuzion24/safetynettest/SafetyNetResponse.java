package fuzion24.safetynettest;

/**
 * Created by fuzion24 on 1/25/16.
 */
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * SafetyNet API payload Response (once unencoded from JSON Web token)
 *
 * {
 "nonce": "iBnt4sI4KCA5Vqh7yDxzUVJYxBYUIQG396Wgmu6lA/Y=",
 "timestampMs": 1432658018093,
 "apkPackageName": "com.scottyab.safetynet.sample",
 "apkDigestSha256": "WN2ADq4LZvMsd0CFBIkGRl8bn3mRKIppCmnqsrJzUJg=",
 "ctsProfileMatch": false,
 "extension": "CY+oATrcJ6Cr",
 "apkCertificateDigestSha256": [
 "Yao6w7Yy7/ab2bNEygMbXqN9+16j8mLKKTCsUcU3Mzw="
 ]
 }
 *
 * Created by scottab on 26/05/2015.
 */
// From :
// https://github.com/scottyab/safetynethelper/blob/master/safetynetlib/src/main/java/com/scottyab/safetynet/SafetyNetResponse.java
public class SafetyNetResponse {

    private static final String TAG = SafetyNetResponse.class.getSimpleName();
    private String nonce;
    private long timestampMs;
    private String apkPackageName;
    private String[] apkCertificateDigestSha256;
    private String apkDigestSha256;
    private boolean ctsProfileMatch;

    //forces the parse()
    private SafetyNetResponse(){}

    /**
     *
     * @return BASE64 encoded
     */
    public String getNonce() {
        return nonce;
    }

    public long getTimestampMs() {
        return timestampMs;
    }

    public String getApkPackageName() {
        return apkPackageName;
    }

    /**
     *
     * @return BASE64 encoded
     */
    public String[] getApkCertificateDigestSha256() {
        return apkCertificateDigestSha256;
    }

    /**
     *
     * @return BASE64 encoded
     */
    public String getApkDigestSha256() {
        return apkDigestSha256;
    }


    public boolean isCtsProfileMatch() {
        return ctsProfileMatch;
    }

    /**
     * Parse the JSON string into populated SafetyNetResponse object
     * @param decodedJWTPayload JSON String (always a json string according to JWT spec)
     * @return populated SafetyNetResponse
     */
    public static @Nullable SafetyNetResponse parse(@NonNull String decodedJWTPayload) {

        Log.d(TAG, "decodedJWTPayload json:" +decodedJWTPayload);

        SafetyNetResponse response = new SafetyNetResponse();
        try {
            JSONObject root = new JSONObject(decodedJWTPayload);
            if(root.has("nonce")) {
                response.nonce = root.getString("nonce");
            }

            if(root.has("apkCertificateDigestSha256")) {
                JSONArray jsonArray = root.getJSONArray("apkCertificateDigestSha256");
                if(jsonArray!=null){
                    String[] certDigests = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        certDigests[i]=jsonArray.getString(i);
                    }
                    response.apkCertificateDigestSha256 = certDigests;
                }
            }

            if(root.has("apkDigestSha256")) {
                response.apkDigestSha256 = root.getString("apkDigestSha256");
            }

            if(root.has("apkPackageName")) {
                response.apkPackageName = root.getString("apkPackageName");
            }

            if(root.has("ctsProfileMatch")) {
                response.ctsProfileMatch = root.getBoolean("ctsProfileMatch");
            }

            if(root.has("timestampMs")) {
                response.timestampMs = root.getLong("timestampMs");
            }

            return response;
        } catch (JSONException e) {
            Log.e(TAG, "problem parsing decodedJWTPayload:"+ e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String toString() {
        return "SafetyNetResponse{" +"\n"+
                "nonce='" + nonce + '\'' + "\n"+
                ", timestampMs=" + timestampMs +"\n"+
                ", apkPackageName='" + apkPackageName + '\'' +"\n"+
                ", apkCertificateDigestSha256='" + apkCertificateDigestSha256 + '\'' +"\n"+
                ", apkDigestSha256='" + apkDigestSha256 + '\'' +"\n"+
                ", ctsProfileMatch=" + ctsProfileMatch +"\n"+
                '}';
    }
}