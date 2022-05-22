package com.example.myfavoriteplaces;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login();
    }

    private void login() {
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        if (usuario != null) {
            Toast.makeText(this, "inicia sesion: " +
                usuario.getDisplayName() + " - " + usuario.getEmail() + " - " +
                usuario.getProviderId(), Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            Intent intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build())).setIsSmartLockEnabled(false).build();
            startActivityForResult(intent, RC_SIGN_IN);
        }
    }

    public void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                login();
                finish();
            }
            else
            {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null)
                {
                    Toast.makeText(this,"Cancelado",Toast.LENGTH_LONG).show();
                    return;
                }
                else if
                (
                        response.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
                {
                    Toast.makeText(this,"Sin conexi?n a Internet", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR)
                {
                    Toast.makeText(this,"Error desconocido", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}