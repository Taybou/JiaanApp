package dz.btesto.upmc.jiaanapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dz.btesto.upmc.jiaanapp.R;
import dz.btesto.upmc.jiaanapp.activities.account.LoginActivity;
import dz.btesto.upmc.jiaanapp.activities.autocomplete.IngredientAuto;
import dz.btesto.upmc.jiaanapp.fragments.favoriteRecipes.FavoriteRecipesFragment;
import dz.btesto.upmc.jiaanapp.fragments.homeFragment.TabFragment;
import dz.btesto.upmc.jiaanapp.fragments.shoppingCart.ShoppingCartFragment;

/**
 * -------------------------
 * ### JI3AN APPLICATION ###
 * -------------------------
 * <p>
 * Created by :
 * ------------
 * ++ Nour Elislam SAIDI
 * ++ Mohamed Tayeb BENTERKI
 * <p>
 * ------ 2016-2017 --------
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String MY_PREFS_NAME = "USER_ID";


    private FragmentTransaction mFragmentTransaction;
    private FirebaseAuth mAuth;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get user auth
        authState();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab_search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IngredientAuto.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void authState() {

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("userID", user.getUid());
                    editor.commit();

                    DatabaseReference mUserName = mRef.child("users").child(user.getUid()).child("userName");
                    mUserName.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String userName = dataSnapshot.getValue(String.class);
                            ((TextView) findViewById(R.id.textView)).setText(userName);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }
        };
    }

    private void dialogAbout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("About");
        alertDialogBuilder.setMessage("Ji3an App <> with ♥ by BTESTO\n\n" +
                "Contributors \n" +
                "      • Nour Elislam SAIDI \n" +
                "      • Mohamed Tayeb BENTERKI\n" +
                "\n" +
                "                    2016-2017 ©");
        alertDialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //Toast.makeText(getApplicationContext(), "Great, ...", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        //alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mFragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            mFragmentTransaction.replace(R.id.containerView, new TabFragment());
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_shopping_cart) {
            toolbar.setTitle("Shopping Cart");
            fab.setVisibility(View.INVISIBLE);
            mFragmentTransaction.replace(R.id.containerView, new ShoppingCartFragment());
        } else if (id == R.id.nav_favorite_recipes) {
            toolbar.setTitle("Favorite Recipes");
            fab.setVisibility(View.INVISIBLE);
            mFragmentTransaction.replace(R.id.containerView, new FavoriteRecipesFragment());
        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        } else if (id == R.id.nav_about) {
            dialogAbout();
        } else if (id == R.id.nav_signout) {
            mAuth.signOut();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        mFragmentTransaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
