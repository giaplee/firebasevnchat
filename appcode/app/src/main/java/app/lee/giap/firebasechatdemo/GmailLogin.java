package app.lee.giap.firebasechatdemo;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GmailLogin extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient mGoogleApiClient;
    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_gmail_login);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //init firebase
        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://gdgchatapps.firebaseio.com");
        ref.authWithOAuthToken("google", "<OAuth Token>", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // the Google user is now authenticated with your Firebase app
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.i("FB", firebaseError.toString());
            }
        });


        signIn(); //start login by gmail
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) { //nếu mã request hợp lệ

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data); //lấy thông tin user
            handleSignInResult(result); //xử lý theo mong muốn
//            Log.i("FB", result.isSuccess() ? "ok" : "notok");
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("FB", connectionResult.getErrorMessage());
    }


    /**
     * Hiển thị hộp thoại chọn email và đăng nhập
     */
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Xử lý thông tin user đăng nhập thành công ở onResult
     * @param result
     */
    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Log.i("FB", acct.getPhotoUrl().toString());
            Log.i("FB", acct.getDisplayName());
            Log.i("FB", acct.getEmail());

        } else {
            // Signed out, show unauthenticated UI.

        }
    }
    // Picasso.with(activity).load(url).transform(new CircleTransform()).into(image);
}