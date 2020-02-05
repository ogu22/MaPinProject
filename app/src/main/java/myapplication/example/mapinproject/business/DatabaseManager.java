package myapplication.example.mapinproject.business;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import myapplication.example.mapinproject.data.entities.Reply;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.data.entities.User;

public class DatabaseManager {

    private static final String TAG = "DatabaseManager";
    private static FirebaseDatabase database;
    private static FirebaseStorage storage;

    public static void addTag(Tag tag) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tag").child(tag.getTagId());
        ref.setValue(tag);
    }

    public static void getTag(String tagId ,final DatabaseCallback databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tag").child(tagId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tag tag = dataSnapshot.getValue(Tag.class);
                databaseCallback.getTagCallBack(tag);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getTag:onCancelled", databaseError.toException());
                databaseCallback.getTagCallBack(null);
            }
        });
    }

    public static void addUser(User user) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user").child(user.getUserId());
        ref.setValue(user);
    }

    public static void getUser(String userId,final DatabaseCallback databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user").child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                databaseCallback.getUserCallBack(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                databaseCallback.getUserCallBack(null);
            }
        });
    }

    public static void addReply(Reply reply) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("reply").child(reply.getReplayId());
        ref.setValue(reply);
    }

    public static void getReply(String replyId, final DatabaseCallback databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user").child(replyId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Reply reply = dataSnapshot.getValue(Reply.class);
                databaseCallback.getReplyCallBack(reply);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                databaseCallback.getReplyCallBack(null);
            }
        });
    }

    public static void addTweeit(Tweeit tweeit) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tweeit").child(tweeit.getTweeitId());
        ref.setValue(tweeit);
    }

    public static void getTweeit(String tweeitId, final DatabaseCallback databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tweeit").child(tweeitId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tweeit tweeit = dataSnapshot.getValue(Tweeit.class);
                databaseCallback.getTweeitCallBack(tweeit);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getTweeit:onCancelled", databaseError.toException());
                databaseCallback.getTweeitCallBack(null);
            }
        });
    }

    public static void addImage(Uri filePath, OnSuccessListener successListener, OnCanceledListener canceledListener) {
        storage = FirebaseStorage.getInstance();
        String uuid = UUID.randomUUID().toString();
        StorageReference storageRef = storage.getReference().child("images").child(uuid);
        storageRef.putFile(filePath).addOnSuccessListener(successListener).addOnCanceledListener(canceledListener);
    }

}