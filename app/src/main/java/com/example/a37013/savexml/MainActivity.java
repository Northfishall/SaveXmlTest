package com.example.a37013.savexml;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import Sms.*;

public class MainActivity extends AppCompatActivity {

    private ArrayList<SmsInfo> infos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int number = 100861;
        for(int i =0 ; i<10 ; i++)
        {
            infos.add(new SmsInfo(i,System.currentTimeMillis(),"短信"+i,
                    number+i+""));
        }
    }

    public void save (View view)
    {
        //假设我们已经获取的所有的短信
        //产生xml文件的序列号类（序列号器）
        XmlSerializer xmlSerializer = Xml.newSerializer();
        //定义一个保存xml文档的文件
        File file = new File(Environment.getExternalStorageDirectory(),"sms1.xml");
        try {
            //将file文件封装在输出流fos对象在
            FileOutputStream fos = new FileOutputStream(file);
            //测试序列化器指定xml数据写入到哪个文件，并指定其编码方式
            xmlSerializer.setOutput(fos, "utf-8");
            //指定xml文件的头，standalone=true（为独立的xml文件）
            xmlSerializer.startDocument("utf-8", true); //文件开头
            xmlSerializer.startTag(null, "smss"); //根节点，null为设置命名空间中，如果没有设置为null
            for (SmsInfo info : infos) {
                xmlSerializer.startTag(null, "sms");
                xmlSerializer.attribute(null, "id", info.getId() + "");
                xmlSerializer.startTag(null, "date");
                xmlSerializer.text(info.getDate() + "");
                xmlSerializer.endTag(null, "date");
                xmlSerializer.startTag(null, "address");
                xmlSerializer.text(info.getAddress());
                xmlSerializer.endTag(null, "address");
                xmlSerializer.startTag(null, "body");
                xmlSerializer.text(info.getBody());
                xmlSerializer.endTag(null, "body");
                xmlSerializer.endTag(null, "sms");
            }
            xmlSerializer.endTag(null, "smss");
            xmlSerializer.endDocument(); //文件结尾
            fos.close();
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }
    }
}
