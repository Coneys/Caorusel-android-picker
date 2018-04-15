package github.com.coneey.carouselpicker

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        disposable = viewModel.pickSubject.subscribe {
            println("Something arrived!")
            button.text = it
        }

        button.setOnClickListener {
            TestPickerFragment().show(supportFragmentManager, "TAG")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}
