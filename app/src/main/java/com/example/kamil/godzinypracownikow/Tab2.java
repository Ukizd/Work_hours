package com.example.kamil.godzinypracownikow;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



public class Tab2 extends Activity implements MessageSource.MessagesCallbacks, View.OnClickListener {

    public static final String USER_EXTRA = "USER";
    public static final String TAG = "ChatActivity";
    private ArrayList<Message> mMessages;
    private MessagesAdapter mAdapter;
    private String mRecipient;
    private ListView mListView;
    private Date mLastMessageDate = new Date();
    private String mConvoId;
    private MessageSource.MessagesListener mListener;

    public FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private LinearLayout singleMessageContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);

        firebaseAuth = FirebaseAuth.getInstance();

        user = firebaseAuth.getCurrentUser();

        mRecipient = "Rohit";
        mListView = (ListView)findViewById(R.id.message_list);
        mMessages = new ArrayList<>();
        mAdapter = new MessagesAdapter(mMessages);
        mListView.setAdapter(mAdapter);
        setTitle(mRecipient);
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        Button sendMessage = (Button)findViewById(R.id.send_message);
        sendMessage.setOnClickListener(this);
        String[] ids = {"Barun","-", "Rohit"};
        Arrays.sort(ids);
        mConvoId = ids[0]+ids[1]+ids[2];
        mListener = MessageSource.addMessagesListener(mConvoId, this);

        Log.d(TAG, "onCreate: "+ mMessages.size());
        for (int i = 0; i < mMessages.size(); i++) {
            Log.d(TAG, "Co jest w wiadomosciach"+ mMessages.get(i));
        }



    }

    @Override
    public void onMessageAdded(Message message) {
        mMessages.add(message);

        mAdapter.notifyDataSetChanged();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MessageSource.stop(mListener);
    }

    @Override
    public void onClick(View v) {

        EditText newMessageView = (EditText)findViewById(R.id.new_message);
        String newMessage = newMessageView.getText().toString();
        newMessageView.setText("");
        Message msg = new Message();
        msg.setmDate(new Date());
        msg.setmText(newMessage);
        msg.setmSender("Rohit");

        MessageSource.saveMessage(msg, mConvoId, user.getEmail());

    }


private class MessagesAdapter extends ArrayAdapter<Message> {
    MessagesAdapter(ArrayList<Message> messages){
        super(Tab2.this, R.layout.item, R.id.msg, messages);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        Message message = getItem(position);
        String dateczka = "31/12/2016 15:36";
        TextView nameView = (TextView)convertView.findViewById(R.id.msg);
//        TextView datee = (TextView)convertView.findViewById(R.id.dateczka);
//        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.GlownyLinear);

        nameView.setText(message.getmText());
//        datee.setText(dateczka);

// dziala        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)nameView.getLayoutParams();

// te 4 linijki to moj dodatek        LinearLayout layoutParams = (LinearLayout) findViewById(R.id.singleMessageContainer);
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        params.weight = 1.0f;
//        params.gravity = Gravity.LEFT;

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) nameView.getLayoutParams();


        String stringUserEmail=String.valueOf(user.getEmail());

        String[] stringUserEmailWithout = stringUserEmail.split("@");


        int sdk = Build.VERSION.SDK_INT;
        if (message.getmSender().equals(stringUserEmailWithout[0])){
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
               nameView.setBackgroundResource(R.drawable.bubble_right_green);

            } else{
                nameView.setBackgroundResource(R.drawable.bubble_right_green);
            }
//            linearLayout.setGravity(Gravity.RIGHT);
            layoutParams.gravity = Gravity.RIGHT;

        }else{
            if (sdk >= Build.VERSION_CODES.JELLY_BEAN) {
                nameView.setBackgroundResource(R.drawable.bubble_left_gray);

            } else{
               nameView.setBackgroundResource(R.drawable.bubble_left_gray);
            }
//            linearLayout.setGravity(Gravity.LEFT);
            layoutParams.gravity = Gravity.LEFT;
        }

        nameView.setLayoutParams(layoutParams);


        return convertView;
    }
}
}
