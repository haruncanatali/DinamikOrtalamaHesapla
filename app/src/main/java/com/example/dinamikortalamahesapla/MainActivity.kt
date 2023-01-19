package com.example.dinamikortalamahesapla

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast

class MainActivity : AppCompatActivity() {

    lateinit var ekleBtn : Button
    lateinit var anasayfaScroll : ScrollView
    lateinit var rootLayout: LinearLayout
    lateinit var dersAdiEdx : AutoCompleteTextView
    lateinit var krediSpin : Spinner
    lateinit var harfNotuSpin: Spinner
    lateinit var temizleBtn : Button
    lateinit var hesaplaBtn : Button

    private val Dersler = arrayOf("Matematik","Fizik","Kimya","Lojik","Algoritma","Tarih","Edebiyat")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Tanimla()
        AdapterTanimla()
        EkleBtnExecute()
        TemizleBtnExecute()
        HesaplaBtnExecute()
    }

    fun Tanimla(){
        ekleBtn = findViewById(R.id.ekleBtn)
        anasayfaScroll = findViewById(R.id.anasayfaScrollView)
        rootLayout = findViewById(R.id.rootLayout)
        dersAdiEdx = findViewById(R.id.dersAdiEdx)
        krediSpin = findViewById(R.id.spnKredi)
        harfNotuSpin = findViewById(R.id.spnHarfNotu)
        temizleBtn = findViewById(R.id.temizleBtn)
        hesaplaBtn = findViewById(R.id.ortHesaplaBtn)

        SetButtonInvisible()
    }

    fun AdapterTanimla()
    {
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,Dersler)
        dersAdiEdx.setAdapter(adapter)
    }

    fun HesaplaBtnExecute(){
        hesaplaBtn.setOnClickListener {
            if(rootLayout.childCount > 0){

                var ortalama = 0.0
                var toplamKredi = 0.0
                var toplamHarfNotuDegeri = 0.0

                for (i in 0..rootLayout.childCount-1){
                    var element = rootLayout.getChildAt(i)

                    val kredi = element.findViewById<TextView>(R.id.krediTxt).text.toString().split(' ')[0].toDouble()
                    val harfNotu = harfNotuDegerHesapla(element.findViewById<TextView>(R.id.harfNotuTxt).text.toString())

                    toplamKredi += kredi
                    toplamHarfNotuDegeri += harfNotu*kredi
                }

                ortalama = (toplamHarfNotuDegeri / toplamKredi)

                FancyToast.makeText(this,"Ortalama : "+ortalama.toString(),FancyToast.LENGTH_LONG,FancyToast.INFO,true).show()
            }
        }
    }

    fun harfNotuDegerHesapla(v:String):Double{
        when (v){
            "AA" ->{
                return 4.0
            }
            "BA" ->{
                return 3.5
            }
            "BB" ->{
                return 3.0
            }
            "CB" ->{
                return 2.5
            }
            "CC" ->{
                return 2.0
            }
            "DC" ->{
                return 1.5
            }
            "DD" ->{
                return 1.0
            }
            "FF" ->{
                return 0.0
            }
        }
        return 0.0
    }

    fun EkleBtnExecute(){
        ekleBtn.setOnClickListener{

            var inflater = LayoutInflater.from(this)
            var yeni_ders_layout = inflater.inflate(R.layout.yeni_ders_layout,null)

            var dersAdi = dersAdiEdx.text.toString()
            var kredi = krediSpin.selectedItem.toString()
            var harfNotu = harfNotuSpin.selectedItem.toString()

            if(dersAdi.isNullOrEmpty()){
                FancyToast.makeText(this,"Ders adını giriniz.",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show()
            }
            else{
                yeni_ders_layout.findViewById<TextView>(R.id.dersAdiTxt).text = dersAdi
                yeni_ders_layout.findViewById<TextView>(R.id.krediTxt).text = kredi
                yeni_ders_layout.findViewById<TextView>(R.id.harfNotuTxt).text = harfNotu

                yeni_ders_layout.findViewById<Button>(R.id.silBtnYeni).setOnClickListener {
                    rootLayout.removeView(yeni_ders_layout)

                    if(rootLayout.childCount == 0){
                        SetButtonInvisible()
                    }
                }

                rootLayout.addView(yeni_ders_layout)

                dersAdiEdx.setText("")
                krediSpin.setSelection(0)
                harfNotuSpin.setSelection(0)

                if(hesaplaBtn.visibility == View.INVISIBLE){
                    SetButtonVisible()
                }
            }
        }
    }

    fun TemizleBtnExecute(){
        temizleBtn.setOnClickListener {
            rootLayout.removeAllViews()
            SetButtonInvisible()
        }
    }

    fun SetButtonInvisible(){
        hesaplaBtn.visibility = View.INVISIBLE
        temizleBtn.visibility = View.INVISIBLE
    }

    fun SetButtonVisible(){
        hesaplaBtn.visibility = View.VISIBLE
        temizleBtn.visibility = View.VISIBLE
    }
}