package myapplication.example.mapinproject.business;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

import myapplication.example.mapinproject.data.entities.DateRange;
import myapplication.example.mapinproject.data.entities.Reply;
import myapplication.example.mapinproject.data.entities.SearchConditions;
import myapplication.example.mapinproject.data.entities.Tag;
import myapplication.example.mapinproject.data.entities.Tweeit;
import myapplication.example.mapinproject.data.entities.User;

public class DatabaseManager {

    private static final String TAG = "DatabaseManager";
    private static FirebaseDatabase database;
    private static FirebaseStorage storage;

    public static void addTag(Tag tag) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tag").child(tag.getTag());
        ref.setValue(tag);
    }

    public static void setTag() {

    }

    public static void tagSearch(String tagText, final TagCallBack tagCallBack) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tag").child(tagText);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Tag>> indicator = new GenericTypeIndicator<HashMap<String, Tag>>() {
                };
                HashMap<String, Tag> value = dataSnapshot.getValue(indicator);
                tagCallBack.getTagCallBack(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getTag:onCancelled", databaseError.toException());
                tagCallBack.getTagCallBack(new HashMap<String, Tag>());
            }
        });
    }

    public static void getTag(String tagId, final TagCallBack databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tag").child(tagId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Tag>> indicator = new GenericTypeIndicator<HashMap<String, Tag>>() {
                };
                HashMap<String, Tag> value = dataSnapshot.getValue(indicator);
                databaseCallback.getTagCallBack(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getTag:onCancelled", databaseError.toException());
                databaseCallback.getTagCallBack(new HashMap<String, Tag>());
            }
        });
    }

    public static void editTag(String tagText, final TagCallBack databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tag").child(tagText);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Tag>> indicator = new GenericTypeIndicator<HashMap<String, Tag>>() {
                };
                HashMap<String, Tag> value = dataSnapshot.getValue(indicator);
                databaseCallback.getTagCallBack(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getTag:onCancelled", databaseError.toException());
                databaseCallback.getTagCallBack(new HashMap<String, Tag>());
            }
        });
    }

    public static void addUser(User user) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user").child(user.getUserId());
        ref.setValue(user);
    }

    public static void getUser(String userId, final UserCallBack databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user").child(userId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, User>> indicator = new GenericTypeIndicator<HashMap<String, User>>() {
                };
                HashMap<String, User> value = dataSnapshot.getValue(indicator);
                databaseCallback.getUserCallBack(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                databaseCallback.getUserCallBack(new HashMap<String, User>());
            }
        });
    }

    public static void addReply(Reply reply) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("reply").child(reply.getReplayId());
        ref.setValue(reply);
    }

    public static void getReply(String replyId, final ReplyCallBack databaseCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("user").child(replyId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Reply>> indicator = new GenericTypeIndicator<HashMap<String, Reply>>() {
                };
                HashMap<String, Reply> value = dataSnapshot.getValue(indicator);
                databaseCallback.getReplyCallBack(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                databaseCallback.getReplyCallBack(new HashMap<String, Reply>());
            }
        });
    }

    public static void addTweeit(Tweeit tweeit) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("tweeit").child(tweeit.getTimestamp());
        ref.setValue(tweeit);
    }

    public static void getTweeit(final SearchConditions conditions, final TweeitCallback databaseCallback) {
        database = FirebaseDatabase.getInstance();
        final DateRange range = conditions.getRange();
        DatabaseReference ref = database.getReference("tweeit")
                .orderByKey().startAt(range.getStartDate()).endAt(range.getEndDate()).getRef();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, Tweeit>> indicator = new GenericTypeIndicator<HashMap<String, Tweeit>>() {
                };
                HashMap<String, Tweeit> value = dataSnapshot.getValue(indicator);
                if (value == null) {
                    databaseCallback.getTweeitCallBack(new HashMap<String, Tweeit>());
                } else {
                    databaseCallback.getTweeitCallBack(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "getTweeit:onCancelled", databaseError.toException());
                databaseCallback.getTweeitCallBack(new HashMap<String, Tweeit>());
            }
        });
    }

    public static void addImage(Uri filePath, OnFailureListener failureListener, OnCompleteListener completeListener) {
        if (filePath == null) {
            //画像がない場合
            failureListener.onFailure(new Exception());
        }
        storage = FirebaseStorage.getInstance();
        String uuid = UUID.randomUUID().toString();
        final StorageReference storageRef = storage.getReference().child("images").child(uuid);
        UploadTask uploadTask = storageRef.putFile(filePath);

        @SuppressWarnings("unchecked")
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return storageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(completeListener);
    }

    public static void getImage(String url) {
        storage = FirebaseStorage.getInstance();

    }

}
