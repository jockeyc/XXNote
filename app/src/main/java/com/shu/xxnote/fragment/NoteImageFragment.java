package com.shu.xxnote.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.StackView;
import android.widget.Toast;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.shu.xxnote.Bmob.Note;
import com.shu.xxnote.Bmob.Notebook;
import com.shu.xxnote.Main2Activity;
import com.shu.xxnote.MainActivity;
import com.shu.xxnote.NoteActivity;
import com.shu.xxnote.R;
import com.shu.xxnote.adapter.TestStackAdapter;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
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

public class NoteImageFragment extends BaseFragment implements CardStackView.ItemExpendListener{
    String picStringUrl,type="picture",title="上传是什么sb",comment="是我没错";
    private static final int REQUEST_CODE_SELECT_PHOTO = 1;
    int choice;
    View rootView;
    CardStackView cardStackView;
    ImageButton button_add;
    TestStackAdapter mTestStackAdapter;
    private int[] test = new int[]{1,2,3,4,5};
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
        rootView = getSubView(inflater,container);
        initBmob();


        return rootView;

    }


    private void showSingDialog(){
        final String[] items = {"图片","音频","视频"};
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(getActivity());
        singleChoiceDialog.setIcon(R.drawable.icon);
        singleChoiceDialog.setTitle("选择插入笔记类型");
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice= which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice!=-1){
                    Toast.makeText(getActivity(),
                            "你选择了" + items[choice],
                            Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        }
                    },1000);
                }
                if (choice==0){
               //Intent intent = new Intent(NoteImageFragment.this, OrcResult.class);
                    //startActivity(intent);
                    Intent intent = new Intent();
                    intent.setType("image/*");// 开启Pictures画面Type设定为image
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_PHOTO);

              BmobInsterPic();
                }

            }
        });
        singleChoiceDialog.show();
    }


    private  void BmobInsterPic() {
        //final String imagePath = getActivity().getExternalCacheDir() + "/output_image.jpg";
        //final BmobFile pic = new BmobFile(new File(imagePath));
        final Note note = new Note();
        //pic.uploadblock(new UploadFileListener() {
           // @Override
            //public void done(BmobException e) {
                //if (e == null) {
                    Log.w(TAG, "上传成功");
                    // picStringUrl= pic.getFileUrl();
                    //note.setBmobfile(pic);
        notebookId = ((NoteActivity)getActivity()).getNotebookId();
                    note.setNotebookId(notebookId);
                    note.setType(type);
                    note.setComment(comment);
                    note.setTitle(title);

        note.save(new SaveListener<String>() {
                      @Override
                      public void done(String s, BmobException e) {
                          if (e == null) {
                              Toast.makeText(getActivity(), "上传图片成功" , Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(getActivity(), "上传图片失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                          }

                      }
                  });


                //} else {
                   // Log.w(TAG, "上传失败" + e.getErrorCode());
                //}
            //}

       // });
    }

    private void initBmob() {
        notebookId = ((NoteActivity)getActivity()).getNotebookId();
        Bmob.initialize(getActivity(),"b69650c5254cf30c803d7b958a002ef1");
        BmobQuery<Note> query = new BmobQuery<Note>();
        query.addWhereEqualTo("notebook",notebookId);
        query.findObjects(new FindListener<Note>() {
            @Override
            public void done(List<Note> list, BmobException e) {
                if(e==null){
                    notes = new Note[list.size()];
                    DATAS = new Integer[list.size()];
                    for (int i=0;i<list.size();i++) {
                        notes[i] = new Note();
                        notes[i].setObjectId(list.get(i).getObjectId());
                        notes[i].setType(list.get(i).getType());
                        notes[i].setComment(list.get(i).getComment());
                        notes[i].setTitle(list.get(i).getTitle());
                        notes[i].setBmobfile(list.get(i).getBmobfile());
                        notes[i].setDate(list.get(i).getCreatedAt());
                    }
                    int i=0,j=0;
                    while (i<list.size()){
                        DATAS[i++]=TEST_DATAS[i];
                        if(j++ ==26)j=0;
                    }
                    initView(rootView);
                }else{
                    System.out.println(e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void initView(View rootView) {
        cardStackView = (CardStackView)rootView.findViewById(R.id.stackview);

        cardStackView.setItemExpendListener(this);
        mTestStackAdapter = new TestStackAdapter(getActivity());
        mTestStackAdapter.setNotes(notes);
        mTestStackAdapter.setContext(getActivity());
        cardStackView.setAdapter(mTestStackAdapter);
        cardStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(cardStackView));
        button_add = (ImageButton)rootView.findViewById(R.id.imageButton3);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSingDialog();
                if(choice==0){
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
        View rootView = inflater.inflate(R.layout.fragment_note_image,container,false);
        return rootView;
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        /*if (resultCode == RESULT_OK) {
            Uri uri = Crop.getOutput(data);
            Bitmap bm;
            try {
                bm = ImageUtils.getZoomOutBitmap(this.getContentResolver(), uri, 750, 750);
            } catch (FileNotFoundException e) {
                Toast.makeText(this, "图片找不到", Toast.LENGTH_SHORT).show();
                return;
            }
//这里上传图片到服务器
            //HttpUtils.uploadCircleImg(rQueue, new BitmapUploadParam(uri.getPath() + ".jpg", bm, 70), this,
            //CODE_EVAL_UPLOAD);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(data).getMessage(), Toast.LENGTH_SHORT).show();
        }*/

    }
}
