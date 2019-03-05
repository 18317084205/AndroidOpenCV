package com.liang.androidopencv

import android.Manifest
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.liang.media.Media
import com.liang.media.MediaRequest
import com.liang.media.MediaType
import com.liang.media.RequestConfig
import com.liang.media.listener.MediaRequestCallback
import com.liang.permission.annotation.Permission
import kotlinx.android.synthetic.main.activity_main.*
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.imgproc.Imgproc

class MainActivity : AppCompatActivity(), MediaRequestCallback {
    override fun onMediaCallback(uris: MutableList<Uri>?, paths: MutableList<String>?) {
        RequestConfig.getInstance().imageLoader.loadNormalImage(this, uris?.get(0), imageView)
    }

    private var mediaRsq: MediaRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Example of a call to a native method
        sample_text.text = stringFromJNI()

        mediaRsq = Media.with(this)
            .mimeType(MediaType.ofImage())
            .canTakePicture(false)
            .selectedMaxCount(1)
            .setMediaRequestCallback(this)

        initOpenCv()

        initView()

    }


    private fun initView() {
        image_selector.setOnClickListener {
            mediaRsq?.startSelector()
        }

        image_capture.setOnClickListener {
            var test: Test = JSON.parseObject<Test>(Test.TEST_JSON, object : TypeReference<Test>() {
            })
            var bean: Bean = test.parseObject(Bean::class.java);
            Log.e("MainActivity", "JsonFactory data: " + test.data)
            Log.e("MainActivity", "JsonFactory studentName: " + bean.studentName)

        }



        button.setOnClickListener {

            var bitmap = (imageView.drawable as BitmapDrawable).bitmap
            var src = Mat()
            var dst = Mat()
            Utils.bitmapToMat(bitmap, src)
            Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGRA2GRAY)
            Utils.matToBitmap(dst, bitmap)
            imageView.setImageBitmap(bitmap)
            src.release()
            dst.release()
        }

    }

    @Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    private fun initOpenCv() {
        var success = OpenCVLoader.initDebug()
        if (success) {
            Log.e("MainActivity", "OpenCv init ok")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mediaRsq?.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to loadMedia the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
