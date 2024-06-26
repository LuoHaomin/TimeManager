package cn.edu.ustc.timemanager.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.timemanager.R;

import cn.edu.ustc.timemanager.adapter.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private RadioGroup tab_bar;
    Integer pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar title=findViewById(R.id.header_title);
        setSupportActionBar(title);
        title.setBackgroundResource(R.color.gray);
        title.setNavigationIcon(R.drawable.ic_back);
        title.setNavigationOnClickListener(view -> {
            finish();
        });

        viewPager=findViewById(R.id.vp);
        tab_bar=findViewById(R.id.tab_bar);
        PagerAdapter adapter=new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int i){
                tab_bar.check(tab_bar.getChildAt(i).getId());
            }
        });

        tab_bar.setOnCheckedChangeListener((group,checkedId)->{
            for(int pos=0;pos<tab_bar.getChildCount();pos++){
                RadioButton tab=(RadioButton) tab_bar.getChildAt(pos);
                if(tab.getId()==checkedId){
                    viewPager.setCurrentItem(pos);
                }
            }
        });
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
            int pos=bundle.getInt("PresentPage");
            viewPager.setCurrentItem(pos);
        }

//        Drawable self_comment = getResources().getDrawable(R.drawable.self_comment);
//        self_comment.setBounds(0, 0, 48, 48);

    }
    @Override
    public void onPause(){
        super.onPause();
        pos = viewPager.getCurrentItem();
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//
//        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
//        viewPager.setCurrentItem(pos);
//
//        int entrance = getIntent().getIntExtra("entrancc", 0);
//        if (entrance == 1){
//            viewPager.setCurrentItem(0);
//        } else if (entrance == 2) {
//            viewPager.setCurrentItem(1);
//        } else if (entrance == 3) {
//            viewPager.setCurrentItem(2);
//        } else if (entrance == 4) {
//            viewPager.setCurrentItem(3);
//        }
//    }

}