package com.shu.xxnote.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.StackView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.shu.xxnote.Bmob.Note;
import  com.shu.xxnote.utils.UriTofilePath;
import com.shu.xxnote.Bmob.Notebook;
import com.shu.xxnote.Main2Activity;
import com.shu.xxnote.MainActivity;
import com.shu.xxnote.NoteActivity;
import com.shu.xxnote.R;
import com.shu.xxnote.adapter.TestStackAdapter;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static android.app.Activity.RESULT_OK;
import static cn.bmob.v3.BmobRealTimeData.TAG;

public class NoteImageFragment extends BaseFragment implements CardStackView.ItemExpendListener {
    String picStringUrl, type = "picture";
    private static final int REQUEST_CODE_SELECT_PHOTO = 1;
    String title,comment;
    Uri uri;
    int choice;
    View rootView;
    CardStackView cardStackView;
    ImageButton button_add;
    TestStackAdapter mTestStackAdapter;
    private int[] test = new int[]{1, 2, 3, 4, 5};
    public Note[] notes;
    public Integer[] DATAS;
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1, R.color.color_2, R.color.color_3, R.color.color_4,
            R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8,
            R.color.color_9, R.color.color_10, R.color.color_11, R.color.color_12,
            R.color.color_13, R.color.color_14, R.color.color_15, R.color.color_16,
            R.color.color_17, R.color.color_18, R.color.color_19, R.color.color_20,
            R.color.color_21, R.color.color_22, R.color.color_23, R.color.color_24,
            R.color.color_25, R.color.color_26
    };
    private String notebookId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getSubView(inflater, container);
        initBmob();


        return rootView;

    }


    private void showSingDialog() {
        final String[] items = {"图片", "音频", "视频"};
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(getActivity());
        singleChoiceDialog.setIcon(R.drawable.icon);
        singleChoiceDialog.setTitle("选择插入笔记类型");
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice = which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice != -1) {
                    Toast.makeText(getActivity(),
                            "你选择了" + items[choice],
                            Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    }, 1000);
                }
                if (choice == 0) {
                    //Intent intent = new Intent(NoteImageFragment.this, OrcResult.class);
                    //startActivity(intent);
                    Intent intent = new Intent();
                    intent.setType("image/*");// 开启Pictures画面Type设定为image
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);


                }

            }
        });
        singleChoiceDialog.show();
    }


    private void BmobInsterPic(final  String title, final String comment, Uri uri) {
        final Note note = new Note();

       System.out.println(uri.getPath()+"&&&&&&&&&&");
        final String imagePath=new UriTofilePath().getFilePathByUri(getActivity(),uri);
        final BmobFile bmobFile = new BmobFile(new File(imagePath));
        Log.w(TAG, imagePath+":这是imagepath");

        bmobFile.uploadblock(new UploadFileListener() {
            public void onProgress(Integer arg0) {
                // TODO Auto-generated method stub
            }
            @Override
            public void done(BmobException e) {
                    if(e==null){
                        Log.w(TAG, "上传成功");
                        notebookId = ((NoteActivity) getActivity()).getNotebookId();
                        Notebook notebook= new Notebook();
                        notebook.setObjectId(notebookId);
                        note.setBook(notebook);
                        note.setNotebookId(notebookId);
                        note.setType(type);
                        note.setComment(comment);
                        note.setTitle(title);
                        note.setBmobfile(bmobFile);
                        note.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "上传图片成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "上传图片失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }else{
                        Log.w(TAG, "上传失败");
                    }
                }

        });


    }

    private void initBmob() {
        notebookId = ((NoteActivity) getActivity()).getNotebookId();
        Bmob.initialize(getActivity(), "b69650c5254cf30c803d7b958a002ef1");
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereEqualTo("notebook", notebookId);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if (e == null) {
                    notes = new Note[list.size()];
                    DATAS = new Integer[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        notes[i] = new Note();
                        notes[i].setObjectId(list.get(i).getObjectId());
                        notes[i].setType(list.get(i).getType());
                        notes[i].setComment(list.get(i).getComment());
                        notes[i].setTitle(list.get(i).getTitle());
                        notes[i].setBmobfile(list.get(i).getBmobfile());
                        notes[i].setDate(list.get(i).getCreatedAt());
                    }
                    int i = 0, j = 0;
                    while (i < list.size()) {
                        DATAS[i++] = TEST_DATAS[i];
                        if (j++ == 26) j = 0;
                    }
                    initView(rootView);
                } else {
                    System.out.println(e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    private void initView(View rootView) {
        cardStackView = (CardStackView) rootView.findViewById(R.id.stackview);

        cardStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(getActivity());
        mTestStackAdapter.setNotes(notes);
        mTestStackAdapter.setContext(getActivity());
        cardStackView.setAdapter(mTestStackAdapter);
        cardStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardStackView));
        button_add = (ImageButton) rootView.findViewById(R.id.imageButton3);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingDialog();
                if (choice == 0) {
                    //调用本地存储空间，获取图片，得到本地图片的URL
                    //成功的话弹出dialog设置title和comment,type
                    //点击确定之后
                    //调用
                }
            }

        });
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(DATAS));
                    }
                }
                , 200
        );
    }

    @Override
    protected void setSubListener() {

    }

    @Override
    protected View getSubView(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_note_image, container, false);
        return rootView;
    }

    @Override
    public void onItemExpend(boolean expend) {

    }

    public void showImageUplodDialog(Uri uri){
       // BitmapDrawable bd= new BitmapDrawable(getResource(), bit);
        //Drawable drawable = new BitmapDrawable(bit);
        final Uri u=uri;
        final  Dialog uplodDialog=new Dialog(getActivity());
        View view = getLayoutInflater().from(getActivity()).inflate(R.layout.uplod_diago,null);
        ///uplodDialog.setIcon(drawable);
        uplodDialog.setContentView(view);
        uplodDialog.setTitle("输入笔记信息");
        final ImageView imageview = view.findViewById(R.id.imageView2);
        imageview.setImageURI(uri);
        final EditText textTitle =(EditText)view.findViewById(R.id.textView7);
        final EditText textComment =(EditText)view.findViewById(R.id.textView9);
        final Button cancle = view.findViewById(R.id.button6);
        final Button confirm = view.findViewById(R.id.button8);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        uplodDialog.dismiss();

                }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 title=  textTitle.getText().toString();
                 comment= textComment.getText().toString();

                BmobInsterPic(title,comment,u);
                uplodDialog.dismiss();
                rootView.postInvalidate();
            }
        });
                //uplodDialog.setIcon(bit)
                uplodDialog.show();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK ) {
            Toast.makeText(getActivity(), "获取图片返回", Toast.LENGTH_SHORT).show();
            uri = data.getData();

            System.out.println("URI？？？？？？"+uri.toString());
           // Toast.makeText(getActivity(), "图片uri"+  uri.toString(), Toast.LENGTH_SHORT).show();

            showImageUplodDialog(uri);
            System.out.println("URI！！！！！！"+uri.toString());
//这里上传图片到服务器
            //HttpUtils.uploadCircleImg(rQueue, new BitmapUploadParam(uri.getPath() + ".jpg", bm, 70), this,
            //CODE_EVAL_UPLOAD);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
