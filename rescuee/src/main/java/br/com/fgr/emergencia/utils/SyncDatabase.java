package br.com.fgr.emergencia.utils;

import android.util.Log;
import br.com.fgr.emergencia.models.general.Hospital;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import io.realm.Realm;
import java.util.List;

public class SyncDatabase {

    public void sync() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("results");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<Hospital>> t =
                    new GenericTypeIndicator<List<Hospital>>() {
                    };
                List<Hospital> hospitals = dataSnapshot.getValue(t);

                Realm.getDefaultInstance()
                    .executeTransactionAsync(realm -> realm.copyToRealmOrUpdate(hospitals),
                        () -> Log.i("Realm", "onSuccess"),
                        error -> Log.e("Realm", "onError", error));
            }

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.e("Firebase", "onCancelled", databaseError.toException());
            }
        });
    }
}
