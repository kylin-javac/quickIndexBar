package ligang.huse.cn.quickindexbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Collections;

import ligang.huse.cn.quickindexbar.bean.Friend;

public class MainActivity extends AppCompatActivity {
    private QuickIndexBar quickIndexBar;
    private ListView list;
    ArrayList<Friend> friends;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏标题栏(ActionBar)
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    /**
     * 初始化布局
     */
    public void initView() {
        quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar);
        list = (ListView) findViewById(R.id.list);
        textView= (TextView) findViewById(R.id.textView);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        quickIndexBar.setListern(new QuickIndexBar.OntouchWordListen() {
            //接口回调 第五步，外部类调用接口实现
            @Override
            public void onTochWord(String word) {
                //根据传过来的字母去frends集合中查找，找到之后就顶格显示在屏幕上方
                for (int i = 0; i < friends.size(); i++) {
                    String firstWord = friends.get(i).getPinyin().charAt(0) + "";
                    if (word.equals(firstWord)) {
                        list.setSelection(i);
                        break;//只需要找到第一个就行
                    }
                }
                showFirstWord(word);
            }
        });

        friends = new ArrayList<>();
        fillData();
        list.setAdapter(new MyAdapter());

        //通过缩小来影藏
        ViewHelper.setScaleX(textView,0);
        ViewHelper.setScaleY(textView,0);

    }


    Handler handler=new Handler();
    boolean isScale=false;
    private void showFirstWord(String word) {
       // textView.setVisibility(View.VISIBLE);
        textView.setText(word);
        if(!isScale) {
            isScale=true;
            ViewPropertyAnimator.animate(textView)
                    .scaleY(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(450)
                    .start();
            ViewPropertyAnimator.animate(textView)
                    .scaleX(1f)
                    .setInterpolator(new OvershootInterpolator())
                    .setDuration(450)
                    .start();
        }
        //移除之前的任务
        handler.removeCallbacksAndMessages(null);
        //延时隐藏
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //textView.setVisibility(View.GONE);
                //隐藏
                ViewPropertyAnimator.animate(textView).scaleY(0f).setDuration(450).start();
                ViewPropertyAnimator.animate(textView).scaleX(0f).setDuration(450).start();
                isScale=false;
            }
        }, 1500);
    }

    /**
     * 给listView准备数据
     */
    private void fillData() {
        friends.add(new Friend("李伟"));
        friends.add(new Friend("张三"));
        friends.add(new Friend("阿三"));
        friends.add(new Friend("阿四"));
        friends.add(new Friend("段誉"));
        friends.add(new Friend("段正淳"));
        friends.add(new Friend("张三丰"));
        friends.add(new Friend("陈坤"));
        friends.add(new Friend("林俊杰1"));
        friends.add(new Friend("陈坤2"));
        friends.add(new Friend("王二a"));
        friends.add(new Friend("林俊杰a"));
        friends.add(new Friend("张四"));
        friends.add(new Friend("林俊杰"));
        friends.add(new Friend("王二"));
        friends.add(new Friend("王二b"));
        friends.add(new Friend("赵四"));
        friends.add(new Friend("杨坤"));
        friends.add(new Friend("赵子龙"));
        friends.add(new Friend("杨坤1"));
        friends.add(new Friend("李伟1"));
        friends.add(new Friend("宋江"));
        friends.add(new Friend("宋江1"));
        friends.add(new Friend("李伟3"));
    }


    /**
     * 设置适配器
     */
    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return friends.size();
        }

        @Override
        public Object getItem(int position) {
            return friends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.friends_adapter, null);
            }
            ViewHolder holder = ViewHolder.getViewHolder(convertView);
            Collections.sort(friends);
            Friend friend = friends.get(position);
            holder.name.setText(friend.getName());
            //获取当前first_word的首字母
            String CurrentWord = friend.getPinyin().charAt(0) + "";
            if (position > 0) {
                //获取上一个首字母
                String lastWord = friends.get(position - 1).getPinyin().charAt(0) + "";
                //那当前首字母与上一个首字母进行比较
                if (CurrentWord.equals(lastWord)) {
                    //说明首字母相同，需要隐藏first_word
                    holder.first_word.setVisibility(View.GONE);
                } else {
                    //不一样，就显示first_word
                    //由于布局是复用的，所以需要显示的时候，再次将first_wrod设为可见
                    holder.first_word.setVisibility(View.VISIBLE);
                    holder.first_word.setText(CurrentWord);
                }
            } else {
                holder.first_word.setVisibility(View.VISIBLE);
                holder.first_word.setText(CurrentWord);
            }
            return convertView;
        }
    }


    static class ViewHolder {
        TextView first_word, name;

        public ViewHolder(View convertView) {
            first_word = (TextView) convertView.findViewById(R.id.first_word);
            name = (TextView) convertView.findViewById(R.id.name);
        }


        public static ViewHolder getViewHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}
