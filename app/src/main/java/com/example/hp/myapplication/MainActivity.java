package com.example.hp.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeTransform;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import static com.example.hp.myapplication.R.layout.custom_layout;

public class MainActivity extends AppCompatActivity {
    DatabaseReference myRef;
    EditText msgText;
    private String TAG;
    Button sndButton;
    ListView msgList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("cod");
        sndButton=(Button)findViewById(R.id.sndButton);
        msgText= (EditText) findViewById(R.id.msgText);
       msgList=(ListView)findViewById(R.id.msgList);
        sndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMsg chat=new ChatMsg("Vikas",msgText.getText().toString());
                myRef.push().setValue(chat);
            }

        });
        final List<ChatMsg> messages =new LinkedList<>();
       final ArrayAdapter <ChatMsg> adapter =new ArrayAdapter<ChatMsg> (
                this,R.layout.custom_layout,messages
        )
        {
            @Override
            public View getView(int position,View view,ViewGroup parent)
            {
                if(view==null) {

                    view = getLayoutInflater().inflate(custom_layout,null);
                }
                ChatMsg chat=messages.get(position);
                ((ImageView)view.findViewById(R.id.imageView)).setImageResource(R.drawable.vikas1);
                ((TextView)view.findViewById(R.id.textView_name)).setText(chat.getName());
                ((TextView)view.findViewById(R.id.textView_message)).setText(chat.getMessage());
                return view;
            }

        };
        msgList.setAdapter(adapter);
        myRef.addChildEventListener(new ChildEventListener(){
        @Override
        public void onChildAdded (DataSnapshot dataSnapshot, String s){
            ChatMsg chat = dataSnapshot.getValue(ChatMsg.class);
            messages.add(chat);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onChildChanged (DataSnapshot dataSnapshot, String s){

        }

        @Override
        public void onChildRemoved (DataSnapshot dataSnapshot){

        }

        @Override
        public void onChildMoved (DataSnapshot dataSnapshot, String s){

        }

        @Override
        public void onCancelled (DatabaseError databaseError){

        }
        });
    }
}
