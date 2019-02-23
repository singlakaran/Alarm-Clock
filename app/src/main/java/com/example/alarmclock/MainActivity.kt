package com.example.alarmclock
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var db: SQLiteDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = SQLiteDatabaseHandler(this);
        initView()
    }

    private fun initView() {
        
        setOnClickListeners();
    }

    private fun navigateSetAlarmFragment() {

    }

    private fun setOnClickListeners() {
        btn_add_alarm.setOnClickListener {
            navigateSetAlarmFragment()
        }
    }
}
