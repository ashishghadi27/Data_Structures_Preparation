package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rootdevs.ashish.datastructures.MainActivity;
import com.rootdevs.ashish.datastructures.R;

import java.util.List;

import Interface.ItemClickListener;
import Model.Qts_list;

public class QTS_Adapter extends RecyclerView.Adapter<QTS_Adapter.MyViewHolder>  {

    private List<Qts_list> listItems;
    private Context context;
    public String id;
    String TAG = "Check Onclick";
    private ItemClickListener itemClickListener;





    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, ans;
        public ImageView imgbutton;
        public CardView cv;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.qts);
            imgbutton = (ImageView)view.findViewById(R.id.type_img);
            view.setOnClickListener(this);
            cv = (CardView)view.findViewById(R.id.cv);
            ans = (TextView)view.findViewById(R.id.ans);
            context = view.getContext();

        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            intent.putExtra("Question", title.getText());
            intent.putExtra("Answer",ans.getText());
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity)context,imgbutton, ViewCompat.getTransitionName(imgbutton));
            v.getContext().startActivity(intent,optionsCompat.toBundle());

        }
    }


    public QTS_Adapter(List<Qts_list> title, Context context) {
        this.listItems = title;
        this.context = context;


    }






    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.qts_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.Landing).playOn(holder.cv);
        Qts_list list = listItems.get(position);
        holder.title.setText(list.getQts());
        holder.ans.setText(list.getAns());

        if(holder.title.getText().toString().contains("Binary trees"))
        {
            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1b5e20")));
            holder.imgbutton.setImageResource(R.drawable.binary_tree);
        }
        else if(holder.title.getText().toString().contains("Stack")){
            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#263238")));
            holder.imgbutton.setImageResource(R.drawable.stack);
        }
        else if(holder.title.getText().toString().contains("queue")){
            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c51162")));
            holder.imgbutton.setImageResource(R.drawable.queue);
        }
        else if(holder.title.getText().toString().contains("binary search")){
            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
            holder.imgbutton.setImageResource(R.drawable.bsearch);
        }
        else{
            holder.cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#000000")));
            holder.imgbutton.setImageResource(R.drawable.nullvoid1);
        }


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}


