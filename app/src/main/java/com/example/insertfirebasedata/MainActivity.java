package com.example.insertfirebasedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    EditText name, email, contact;
    Button send;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        send = findViewById(R.id.btn_send);

        databaseReference = FirebaseDatabase.getInstance().getReference("user");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String NAME = name.getText().toString().trim();
                String EMAIL = email.getText().toString().trim();
                String CONTACT = contact.getText().toString().trim();

                if (NAME.isEmpty()){
                    name.setError("Name can't be empty");
                    name.requestFocus();
                    return;
                }

                if (EMAIL.isEmpty()){
                    email.setError("Email can't be empty");
                    email.requestFocus();
                    return;
                }

                if (CONTACT.isEmpty()){
                    contact.setError("Contact can't be empty");
                    contact.requestFocus();
                    return;
                }

                String ID = databaseReference.push().getKey();

                Model model = new Model(ID, NAME, EMAIL, CONTACT);

                databaseReference.child(ID).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {

                        if (task.isSuccessful()){

                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}