package github.com.coneey.carouselpicker

import android.arch.lifecycle.ViewModel
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class MainViewModel : ViewModel() {
    val pickSubject: Subject<String> = BehaviorSubject.create()
}