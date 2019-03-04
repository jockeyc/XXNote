package com.shu.xxnote.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.shu.xxnote.NoteActivity;
import com.shu.xxnote.Bmob.Notebook;
import com.shu.xxnote.Pointer;
import com.shu.xxnote.R;
import com.shu.xxnote.Bmob.Users;

import java.util.List;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class NoteBookAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Notebook> list;
    private String userId;
    int[] coverId = new int[5];
    public NoteBookAdapter(String userid,Context context, List<Notebook> list) {
        //获得LayoutInflater的实例
        this.userId=userid;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
        coverId[0] = R.drawable.cover1;
        coverId[1] = R.drawable.cover2;
        coverId[2] = R.drawable.cover3;
        coverId[3] = R.drawable.cover4;
        coverId[4] = R.drawable.cover5;
    }

    @Override
    public int getCount() {
        return list.size(); //返回列表长度
    }

    @Override
    public Notebook getItem(int position) {
        return list.get(position);//返回该位置的Map<>
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    public final class ViewHolder {
        public Button btn_book;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //设置/获取holder
        ViewHolder holder = null;
        EditText newName;
        if (convertView == null) {
            //convertView表示某一行的view布局，是已经inflate了的布局
            convertView = layoutInflater.inflate(R.layout.item, null);

            //实例化holder中的weight
            holder = new ViewHolder();
            holder.btn_book = (Button) convertView.findViewById(R.id.btn_book);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int p = position;
        final Notebook book = getItem(position);
        final String id = book.getObjectId();
        final int cover = list.get(position).getCover();
        String bookname = list.get(position).getNotename();
        int i =coverId[cover];
        holder.btn_book.setBackgroundResource(i);
        holder.btn_book.setText(bookname);
        //单击书籍
        holder.btn_book.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //跳转到笔记界面
                Intent intent = new Intent();
                intent.setClass(context,NoteActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("notebookId",book.getObjectId());
                context.startActivity(intent);

            }
        });

        //长按书籍
        holder.btn_book.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.HorizonDialog);
                dialog.setContentView(R.layout.long_click_dialog);
                dialog.show();

                Button btn_delete = (Button) dialog.findViewById(R.id.btn_delete);
                Button btn_rename = (Button) dialog.findViewById(R.id.btn_rename);
                Button add_notes = (Button) dialog.findViewById(R.id.add_notes);
                //删除
                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        final Dialog dialog1 = new Dialog(context, R.style.HorizonDialog);
                        dialog1.setContentView(R.layout.delete_notebook);
                        dialog1.show();
                        dialog.cancel();
                        Button btn_ok1 = (Button) dialog1.findViewById(R.id.btn_ok);
                        Button btn_cancel1 = (Button) dialog1.findViewById(R.id.btn_cancel);

                        btn_ok1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                Pointer pointer = new Pointer();
                                pointer.delete(id);

                                BmobQuery<Notebook> query = new BmobQuery<>();
                                Users users1 = new Users();
                                users1.setObjectId(userId);
                                query.addWhereEqualTo("users",users1);
                                query.findObjects(new FindListener<Notebook>() {
                                    @Override
                                    public void done(List<Notebook> list1, BmobException e) {
                                        if(e==null){
                                            //动态刷新
                                            setList(list1);
                                            notifyDataSetChanged();
                                        }
                                    }
                                });
                                dialog1.cancel();
                            }
                        });
                        btn_cancel1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                dialog1.cancel();
                                dialog.show();
                            }
                        });
                    }
                });
                //重命名
                btn_rename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        final Dialog dialog2 = new Dialog(context, R.style.HorizonDialog);
                        dialog2.setContentView(R.layout.rename);
                        dialog2.show();
                        dialog.cancel();
                        Button btn_ok2 = (Button) dialog2.findViewById(R.id.btn_ok);
                        Button btn_cancel2 = (Button) dialog2.findViewById(R.id.btn_cancel);
                        final EditText newName = (EditText) dialog2.findViewById(R.id.theName);
                        btn_ok2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                String newNameStr = newName.getText().toString();
                                if (newNameStr.length() != 0) {
                                    Pointer pointer = new Pointer();
                                    int cover1 = list.get(p).getCover();
                                    pointer.rename(id, newNameStr,cover1);

                                    BmobQuery<Notebook> query = new BmobQuery<>();
                                    Users users1 = new Users();
                                    users1.setObjectId(userId);
                                    query.addWhereEqualTo("users",users1);
                                    query.findObjects(new FindListener<Notebook>() {
                                        @Override
                                        public void done(List<Notebook> list1, BmobException e) {
                                            if(e==null){
                                                //动态刷新
                                                setList(list1);
                                                notifyDataSetChanged();
                                            }
                                        }
                                    });

                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "请输入笔记本名", Toast.LENGTH_SHORT).show();
                                }
                                dialog2.cancel();
                                dialog.cancel();
                            }
                        });
                        btn_cancel2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                                dialog2.cancel();
                                dialog.show();
                            }
                        });
                    }
                });
                //添加笔记
                add_notes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        final Dialog dialog3 = new Dialog(context, R.style.HorizonDialog);
                        dialog3.setContentView(R.layout.add_note);
                        dialog3.show();
                        dialog.cancel();
                        Button photo = (Button) dialog3.findViewById(R.id.photo);
                        Button word = (Button) dialog3.findViewById(R.id.word);
                        Button video = (Button) dialog3.findViewById(R.id.video);

                        photo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                            }
                        });
                        word.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                            }
                        });
                        video.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {

                            }
                        });
                    }
                });
                return true;
            }
        });

        return convertView;

    }
    public void addItems(List<Notebook> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    /**获取此adapter的list数据集合*/
    public List<Notebook> getList() {
        return list;
    }

    /**设置此adapter的list数据集合*/
    public void setList(List<Notebook> list) {
        this.list = list;
    }




}