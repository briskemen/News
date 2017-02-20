package com.itheima.zhbj52.base.menudetail;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj52.R;
import com.itheima.zhbj52.base.BaseMenuDetailPager;
import com.itheima.zhbj52.domain.PhotoData;
import com.itheima.zhbj52.global.GlobalContants;
import com.itheima.zhbj52.utils.CacheUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.ArrayList;

/**
 * 菜单详情页-组图
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

    private ListView    lvPhoto;
    private GridView    gvPhoto;
    private ImageButton btnPhoto;

    private ArrayList<PhotoData.PhotoInfo> mPhotoList;
    private PhoteAdapter                   mAdapter;

    public PhotoMenuDetailPager(Activity activity, ImageButton btnPhoto) {
        super(activity);
        this.btnPhoto = btnPhoto;

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDisplay();
            }
        });
    }

    private boolean isListDisplay = true;// 是否是列表显示

    /**
     * 切换展现方法
     */
    private void changeDisplay() {
        if (isListDisplay) {
            isListDisplay = false;//这里为什么是false
            lvPhoto.setVisibility(View.GONE);
            gvPhoto.setVisibility(View.VISIBLE);

            btnPhoto.setImageResource(R.mipmap.icon_pic_list_type);
        } else {
            isListDisplay = true;
            lvPhoto.setVisibility(View.VISIBLE);
            gvPhoto.setVisibility(View.GONE);

            btnPhoto.setImageResource(R.mipmap.icon_pic_grid_type);
        }
    }

    /**
     * 初始化布局
     *
     * @return
     */
    @Override
    public View initViews() {
        View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
        lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
        gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

        return view;
    }

    @Override
    public void initData() {
        String cache = CacheUtils.getCache(GlobalContants.PHOTOS_URL, mActivity);
        if (!TextUtils.isEmpty(cache)) {

        }

        getDataFromServer();
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, GlobalContants.PHOTOS_URL,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        String result = (String) responseInfo.result;
                        parseData(result);
                        // 设置缓存
                        CacheUtils.SetCache(GlobalContants.PHOTOS_URL, result,
                                mActivity);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
                                .show();
                        error.printStackTrace();
                    }
                });
    }


    /**
     * 解析数据
     *
     * @param result
     */
    protected void parseData(String result) {
        Gson gson = new Gson();
        PhotoData data = gson.fromJson(result, PhotoData.class);
        mPhotoList = data.data.news;

        if (mPhotoList != null) {
            mAdapter = new PhoteAdapter();
            lvPhoto.setAdapter(mAdapter);
            gvPhoto.setAdapter(mAdapter);
        }
    }

    class PhoteAdapter extends BaseAdapter {

        private final BitmapUtils mUtils;

        public PhoteAdapter() {
            mUtils = new BitmapUtils(mActivity);
            mUtils.configDefaultLoadingImage(R.mipmap.news_pic_default);
        }

        @Override
        public int getCount() {
            return mPhotoList.size();
        }

        @Override
        public PhotoData.PhotoInfo getItem(int position) {
            return mPhotoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.list_photo_item, null);
                holder = new ViewHolder();
                holder.ivPic = (ImageView) convertView.findViewById(R.id.iv_pic);
                holder.tvTile = (TextView) convertView.findViewById(R.id.tv_title);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PhotoData.PhotoInfo info = getItem(position);

            holder.tvTile.setText(info.title);
            mUtils.display(holder.ivPic, info.listimage);
            return convertView;
        }
    }

    static class ViewHolder {
        public ImageView ivPic;
        public TextView  tvTile;
    }
}
