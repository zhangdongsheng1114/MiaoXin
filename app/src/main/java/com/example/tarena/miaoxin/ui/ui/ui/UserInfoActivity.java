package com.example.tarena.miaoxin.ui.ui.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tarena.miaoxin.R;
import com.example.tarena.miaoxin.ui.ui.model.MyUser;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.iv_userinfo_avatar)
    ImageView ivAvatar;
    @BindView(R.id.iv_userinfo_avatareditor)
    ImageView ivAvatarEditor;

    @BindView(R.id.tv_userinfo_nickname)
    TextView tvNickname;
    @BindView(R.id.iv_userinfo_nicknameeditor)
    ImageView ivNicknameEditor;
    @BindView(R.id.ll_userinfo_editnicknamecontainer)
    LinearLayout llNicknameContainer;
    @BindView(R.id.et_userinfo_nickname)
    EditText etNickname;
    @BindView(R.id.ib_userinfo_confirm)
    ImageButton ibConfirm;
    @BindView(R.id.ib_userinfo_cancel)
    ImageButton ibCancel;

    @BindView(R.id.tv_userinfo_username)
    TextView tvUsername;

    @BindView(R.id.iv_userinfo_gender)
    ImageView ivGender;
    // 如果要修改性别，请参考修改昵称的做法

    @BindView(R.id.btn_userinfo_update)
    Button btnUpdate;
    @BindView(R.id.btn_userinfo_chat)
    Button btnChat;
    @BindView(R.id.btn_userinfo_black)
    Button btnBlack;

    //me：从SettingFragment跳转过来
    //friend: 从FriendFragmetn跳转过来
    //stranger: 从AddFriendActivity或者NewFriendActivity跳转过来
    String from;
    String username;

    MyUser user; // 根据username属性获得相对应的用户
    String cameraPath;  // 拍摄头像照片时SD卡的路径
    String avatarUrl;  // 上传头像照片完毕后，头像照片在服务器上的网址
    private Uri imageUri;

    @Override
    public int getLayoutID() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        from = getIntent().getStringExtra("from");
        if ("me".equals(from)) {
            username = BmobUser.getCurrentUser(this, MyUser.class).getUsername();
            // log(from+"/"+username)
        } else {
            username = getIntent().getStringExtra("username");
            // log(from+"/"+username)
        }
        initView();
        initActionbar();
    }

    private void initView() {
        BmobQuery<MyUser> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.findObjects(this, new FindListener<MyUser>() {

            @Override
            public void onError(int i, String s) {
                toastAndLog("查询用户信息失败", i, s);
            }

            @Override
            public void onSuccess(List<MyUser> list) {
                user = list.get(0);
                // 根据user中的内容设定界面
                // 设定用户头像
                String avatar = user.getAvatar();
                if (TextUtils.isEmpty(avatar)) {
                    ivAvatar.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.getInstance().displayImage(avatar, ivAvatar);
                }
                // 头像的铅笔
                if ("me".equals(from)) {
                    ivAvatarEditor.setVisibility(View.VISIBLE);
                } else {
                    ivAvatarEditor.setVisibility(View.INVISIBLE);
                }
                // user的昵称
                String nickname = user.getNick();
                tvNickname.setText(nickname);
                llNicknameContainer.setVisibility(View.INVISIBLE);
                // 昵称的铅笔
                if ("me".equals(from)) {
                    ivNicknameEditor.setVisibility(View.VISIBLE);
                } else {
                    ivNicknameEditor.setVisibility(View.INVISIBLE);
                }
                // user的用户名
                String username = user.getUsername();
                tvUsername.setText(username);
                // user的性别
                Boolean gender = user.getGender();
                if (gender) {
                    ivGender.setImageResource(R.mipmap.boy);
                } else {
                    ivGender.setImageResource(R.mipmap.girl);
                }

                // 设置按钮
                if ("me".equals(from)) {
                    btnUpdate.setVisibility(View.VISIBLE);
                } else {
                    btnUpdate.setVisibility(View.GONE);
                }

                if ("friend".equals(from)) {
                    btnChat.setVisibility(View.VISIBLE);
                    btnBlack.setVisibility(View.VISIBLE);
                } else {
                    btnChat.setVisibility(View.INVISIBLE);
                    btnBlack.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initActionbar() {
        titleTV.setText("个人资料");
        showBackBtn();
    }

    @OnClick(R.id.iv_userinfo_avatareditor)
    public void setAvatar(View v) {
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + ".jpg");
        cameraPath = file.getAbsolutePath();
        imageUri = Uri.fromFile(file);
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        Intent chooser = Intent.createChooser(intent1, "选择头像...");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(chooser, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 101) {
            String filePath = "";
            if (data != null) {
                // 图库选图
                // 对于部分手机来说，在安卓原生的拍照程序基础上做了修改
                // 导致拍照的照片会随着data返回到这里
                Uri uri = data.getData();
                if (uri != null) {
                    if (!uri.getPath().equals(imageUri.getPath())) {
                        // 图库
                        Cursor cursor = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                        cursor.moveToNext();
                        filePath = cursor.getString(0);
                        cursor.close();
                    } else {
                        // 拍照
                        // 拍照的路径依然是cameraPath
                        filePath = cameraPath;
                    }
                } else {
                    Bundle bundle = data.getExtras();
                    // bitmap是拍照回来的照片
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    // TODO 将bitmap存储到SD卡
                }
            } else {
                // 相机拍照
                filePath = cameraPath;
            }

            final BmobFile bf = new BmobFile(new File(filePath));
            bf.uploadblock(UserInfoActivity.this, new UploadFileListener() {
                @Override
                public void onSuccess() {
                    avatarUrl = bf.getFileUrl(UserInfoActivity.this);
                    ImageLoader.getInstance().displayImage(avatarUrl, ivAvatar);
                }

                @Override
                public void onFailure(int i, String s) {
                    toastAndLog("上传头像失败", i, s);
                }
            });
        }
    }

    @OnClick(R.id.iv_userinfo_nicknameeditor)
    public void setNickname(View v) {
        String nickname = tvNickname.getText().toString();
        tvNickname.setVisibility(View.INVISIBLE);
        llNicknameContainer.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(nickname)) {
            etNickname.setHint("请输入昵称");
        } else {
            etNickname.setText(nickname);
        }
        ivNicknameEditor.setVisibility(View.GONE);
    }

    @OnClick(R.id.ib_userinfo_confirm)
    public void saveNickname(View v) {
        String nickname = etNickname.getText().toString();
        etNickname.setText("");
        tvNickname.setVisibility(View.VISIBLE);
        tvNickname.setText(nickname);
        llNicknameContainer.setVisibility(View.INVISIBLE);
        ivNicknameEditor.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ib_userinfo_cancel)
    public void cancelNickname(View v) {
        etNickname.setText("");
        tvNickname.setVisibility(View.VISIBLE);
        llNicknameContainer.setVisibility(View.INVISIBLE);
        ivNicknameEditor.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_userinfo_update)
    public void update(View v) {
        if (avatarUrl != null) {
            user.setAvatar(avatarUrl);
        }
        user.setNick(tvNickname.getText().toString());
        user.update(this, new UpdateListener() {
            @Override
            public void onSuccess() {
                toast("资料更新完成");
            }

            @Override
            public void onFailure(int i, String s) {
                log(i, s);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 1500);
            }
        });
    }

    @OnClick(R.id.btn_userinfo_chat)
    public void chat(View v) {

    }
}
