package app.lee.giap.firebasechatdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import app.lee.giap.firechatdemo.models.ChatMessage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //public variables:
    private Firebase mFirebaseRef;
    private static final String FIREBASE_APP_NAME = "vnchatapp";
    private static  final String FIREBASE_LOG_NAME = "FIREBASE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                firebaseInsertData("giaplee", "hello");

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Init firebase
        initFirebase(FIREBASE_APP_NAME); //must call before do other task with mFirebaseRef




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //My code block==========================================================================>

    /**
     * Init a connection to firebase server
     * @param firebase_app_name
     * Firebase app name which you created on firebase server
     */
    private void initFirebase(String firebase_app_name){
        Firebase.setAndroidContext(this);
        mFirebaseRef = new Firebase("https://" + firebase_app_name +".firebaseio.com/chathistory");

        mFirebaseRef.authWithCustomToken("SQXxFElgHhbMuyT8kSY5RmRYu3CRej7szdWOOqt0", new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.i(FIREBASE_LOG_NAME, "authen -> successed");
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Log.i(FIREBASE_LOG_NAME, "authen -> not successed");
            }
        });
    }

    /**
     * Insert a record with key, value to firebase server
     * @param user_name String
     * @param msg String
     */
    private void firebaseInsertData(String user_name, String msg){

        Map<String, String> chat_map = new HashMap<>();

        ChatMessage chat_msg = new ChatMessage();
        chat_msg.setMessage(msg);
        chat_msg.setUser_name(user_name);
        chat_msg.setTime("" + System.currentTimeMillis());

        mFirebaseRef.child("chattemp").push().setValue(chat_msg);

    }

    //<====================================================== End my code block
}
