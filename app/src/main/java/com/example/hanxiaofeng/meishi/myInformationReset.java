package com.example.hanxiaofeng.meishi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lihongzheng.meishi.R;

import java.io.File;

public class myInformationReset extends Activity {
    //个人信息属性
    private TextView name;
    private TextView sex;
    private TextView signature;
    private TextView psd;
    //修改头像属性
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information_reset);

        //iv_photo用来监听修改头像事件
        iv_personal_icon = (ImageView) findViewById(R.id.iv_photo);
        iv_personal_icon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });
        //用tv_sex_reset监听修改性别事件
        sex=(TextView)findViewById(R.id.tv_sex_reset);
        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
        //用name_reset,signature_reset来监听修改昵称和个性签名事件
        name=(EditText)findViewById(R.id.name_reset);
        signature=(EditText)findViewById(R.id.signature_reset);
/*
        Button btn=(Button) findViewById(R.id.btn_regist);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Information_reset.this, Information.class);
                Bundle bundle = new Bundle();
                Person person = new Person();
                bundle.putSerializable("person", person); //存放序列化对象
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
    }

    /**
     * 功能：修改头像函数
     * 作者：晓峰
     * 时间：2018.7.10
     */
    //显示修改头像的对话框
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_PICK);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    //裁剪图片方法实现
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    //保存裁剪之后的图片数据
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            iv_personal_icon.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

        String imagePath = Utils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
        }
    }

    /**
     * 功能：用AlertDialog单选框来实现性别的选择
     * 作者：晓峰
     * 时间：2018.7.11
     */
    String cursex;//记录当前所选择的性别
    // 用单选对话框来进行对性别的选择
    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(myInformationReset.this);
        //builder.setIcon(R.drawable.ic_launcher);//设置图标
        builder.setTitle("选择性别");
        final String[] sex_names = {"男", "女","保密"};

        //设置一个单项选择下拉框
        /**
         * 第一个参数指定我们要显示的一组下拉单选框的数据集合
         * 第二个参数代表索引，指定默认哪一个单选框被勾选上，2表示默认'保密' 会被勾选上
         * 第三个参数给每一个单选项绑定一个监听器
         */
        builder.setSingleChoiceItems(sex_names, 2, new DialogInterface.OnClickListener() {

            /**
             * @param dialog
             * @param which 此处的which只在本函数中起作用，在下面的按钮中不能调用该值，如若调用，该值会显示为垃圾值
             *              也正是因为如此，所以才定义了cursex，用来记录用户所选的which值。如果在确定按钮中写成如下形式：
             *              sex.setText(sex_name[which];会出现bug.因为which是垃圾值！！！
             */
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(myInformationReset.this, "性别为：" + sex_names[which], Toast.LENGTH_SHORT).show();

                try {
                    cursex = sex_names[which];
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)//注意：此处的which与数组下标which不同
            {
                try {
                    sex.setText(cursex);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }

}
