package com.example.kamil.godzinypracownikow;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Kamil on 15.10.2016.
 */

public class MessageSource {

    private static final Firebase sRef = new Firebase("https://socoffee-2ec22.firebaseio.com/CHAT");
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddmmss");
    private static final String TAG = "MessageDataSource";
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_SENDER = "sender";

    public static void saveMessage(Message message, String convoId, String sender){
        Date date = message.getmDate();
        String key = sDateFormat.format(date);
        HashMap<String, String> msg = new HashMap<>();
        String stringUserEmail=sender;

        String[] stringUserEmailWithout = stringUserEmail.split("@");
        msg.put(COLUMN_TEXT, message.getmText());
        msg.put(COLUMN_SENDER, stringUserEmailWithout[0]);
        sRef.child(convoId).child(key).setValue(msg);
    }

    public static MessagesListener addMessagesListener(String convoId, final MessagesCallbacks callbacks){
        MessagesListener listener = new MessagesListener(callbacks);
        sRef.child(convoId).addChildEventListener(listener);
        return listener;
    }

    public static void stop(MessagesListener listener){
        sRef.removeEventListener(listener);
    }

    public static class MessagesListener implements ChildEventListener {
        private MessagesCallbacks callbacks;
        MessagesListener(MessagesCallbacks callbacks){
            this.callbacks = callbacks;
        }

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            HashMap<String,String> msg = (HashMap)dataSnapshot.getValue();
            Message message = new Message();
            message.setmSender(msg.get(COLUMN_SENDER));
            message.setmText(msg.get(COLUMN_TEXT));
            try {
                message.setmDate(sDateFormat.parse(dataSnapshot.getKey()));
            }catch (Exception e){
                //LOG.d(TAG, "Couldn't parse date"+e);
            }
            if(callbacks != null){
                callbacks.onMessageAdded(message);
            }
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
        }
        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
        }
        @Override
        public void onCancelled(FirebaseError firebaseError) {
        }
    }

    public interface MessagesCallbacks{
        public void onMessageAdded(Message message);
    }
}
