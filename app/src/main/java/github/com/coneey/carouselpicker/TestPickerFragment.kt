package github.com.coneey.carouselpicker

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import github.com.coneey.carousellistpicker.CarouselAdapter
import github.com.coneey.carousellistpicker.PickerDialogFragment
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.test_row.view.*

class TestPickerFragment : PickerDialogFragment<String>() {
    val months = listOf("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień")
    val subject: Subject<String> = BehaviorSubject.create()
    val quickAdapter = QuickAdapter(months)

    var disposable: Disposable? = null

    override val carouselAdapter = CarouselAdapter(quickAdapter, { months[it] }, subject)
    override val recyclerView: RecyclerView by lazy { list }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true)
        layoutManager.setPostLayoutListener(CarouselZoomPostLayoutListener())
        list.layoutManager = layoutManager
        list.setHasFixedSize(true)
        list.addOnScrollListener(CenterScrollListener())

        disposable = subject.subscribe {
            println("CLICKED IN !! $it")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    inner class QuickAdapter(val list: List<String>) : RecyclerView.Adapter<QuickViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuickViewHolder {
            return QuickViewHolder(layoutInflater.inflate(R.layout.test_row, parent, false))
        }

        override fun getItemCount() = list.size


        override fun onBindViewHolder(holder: QuickViewHolder, position: Int) {
            holder.bind(list[position])
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    class QuickViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(s: String) {
            itemView.tittle.text = s
        }

    }


}
