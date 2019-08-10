package com.sepideh.onlinemarket.navigationview.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.sepideh.onlinemarket.R;
import com.sepideh.onlinemarket.adapter.MessagesAdapter;
import com.sepideh.onlinemarket.data.NotifItem;
import com.sepideh.onlinemarket.room.MyRoomDatabase;

import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{

    MyRoomDatabase roomDatabase;
    List<NotifItem> notifItemList;

    RelativeLayout sabad;
    ImageView back;
    TextView toolbartitle;
    RecyclerView recyclerView;

    MessagesAdapter adapter;
    TextView emptyAlarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        setUpView();

    }

    void setUpView(){
        sabad=findViewById(R.id.rel_toolbar_sabad);
        sabad.setVisibility(View.GONE);

        back=findViewById(R.id.img_toolbar_back);
        back.setOnClickListener(this);

        toolbartitle=findViewById(R.id.txv_toolbar_title);
        toolbartitle.setText(getString(R.string.title_messages));

        emptyAlarm=findViewById(R.id.txv_message_emptyText);

        roomDatabase=MyRoomDatabase.getAppDatabase(this);

        NotifItem notifItem= Hawk.get("remoteMessageH");
        if( notifItem!=null  ){
            //it comes from push content tap
            roomDatabase.getItemDao().insert(notifItem);
            Hawk.put("remoteMessageH",null);
        }


        notifItemList=roomDatabase.getItemDao().getItems();
        recyclerView=findViewById(R.id.rec_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        adapter=new MessagesAdapter(notifItemList,this);
        recyclerView.setAdapter(adapter);
        if(notifItemList.size()>0){
            recyclerView.setVisibility(View.VISIBLE);
            emptyAlarm.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==back.getId())
            onBackPressed();
    }
}
