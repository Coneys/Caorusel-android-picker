package github.com.coneey.carouselpicker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        button.setOnClickListener {
            val dialog = TestPickerFragment()
            dialog.show(supportFragmentManager, "TAG")
        }

    }
}
