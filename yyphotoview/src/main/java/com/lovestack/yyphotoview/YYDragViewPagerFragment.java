package com.lovestack.yyphotoview;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.lovestack.yyphotoview.base.BaseFragmentDialog;
import com.lovestack.yyphotoview.drag.DragFrameLayout;
import com.lovestack.yyphotoview.drag.DragUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * \                                                 )
 * \                                               (  (
 * \                                              ) ) )
 * \                  ......,.....................)  )
 * \                  |.....|..................... ))
 * \                                 [[[_________________________________]]]
 * \                                   Blog: https://lovestack.github.io
 * \                                         Date: 2018/7/9
 * \                                              User: 清晨
 * \
 */
public class YYDragViewPagerFragment extends BaseFragmentDialog {


    private List<View> mViewList = new ArrayList<>();
    private List<String> images = new ArrayList<>();

    private ViewPager mViewpager;

    private int nowPosition = 0;

    private DragFrameLayout dragFrameLayout;
    private TextView tvPosition, tvNum;


    @Override
    protected View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_yy_drag_viewpager, null);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);
        dragFrameLayout = (DragFrameLayout) view.findViewById(R.id.drag);
        tvPosition = view.findViewById(R.id.tv_position);
        tvNum = view.findViewById(R.id.tv_num);
        initView();
        return view;
    }

    private void initView() {
        if (images.size() == 0) {
            throw new RuntimeException("image is null");
        }
        if (images.size() < nowPosition + 1||nowPosition<0) {
            throw new RuntimeException("position is err");
        }
        tvNum.setText(String.valueOf(images.size()));
        tvPosition.setText(String.valueOf(nowPosition+1));
        dragFrameLayout.setCancelListener(new DragFrameLayout.CancelListener() {
            @Override
            public void cancel() {
                dismiss();
            }
        });
        initData();
        onBack();
        initViewPager();
    }

    private void initData() {
        for (String image : images) {
            PhotoView imageView = new PhotoView(getActivity());
            mViewList.add(imageView);
            Glide.with(this).load(image).into(imageView);
        }
    }

    private void initViewPager() {
        mViewpager.setAdapter(new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mViewList.get(position));
                return mViewList.get(position);
            }
        });
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //支持Transition的设定支持缩放
                dragFrameLayout.setDragScale(true);
                tvPosition.setText(String.valueOf(position+1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewpager.setCurrentItem(nowPosition, false);
    }

    private void onBack() {
        //拦截外部返回
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mViewpager.getCurrentItem() != nowPosition) {

                        final View decorView = getActivity().getWindow().getDecorView();
                        //利用动画来平滑过渡关闭
                        DragUtil.closeNotSupportTransition(decorView, new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                dismiss();
//                                overridePendingTransition(R.anim.x_push_activity,R.anim.push_exit);
                            }
                        });

                        if (Build.VERSION.SDK_INT >= 21) {
                            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
                        }

                    } else {
                        ActivityCompat.finishAfterTransition(getActivity());
                    }

                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 动画
     *
     * @return
     */
    @Override
    protected int initAnimations() {
        return R.style.YYAnimateDialog;
    }

    /**
     * Dialog初始化相关
     */
    @Override
    public void initDialog() {
        //点击外部不可取消,默认false
        getDialog().setCanceledOnTouchOutside(false);
    }

    /**
     * 背景透明度
     *
     * @return
     */
    @Override
    public float initBackgroundAlpha() {
        return 0.8f;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images, int position) {
        this.images = images;
        this.nowPosition = position;
    }


}
