package github.com.coneey.carouselpicker

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
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
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.test_row.view.*

class TestPickerFragment : PickerDialogFragment<String>() {
    val months = listOf("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień")
    val quickAdapter = QuickAdapter(months)
    val viewModel by lazy { ViewModelProviders.of(activity!!).get(MainViewModel::class.java) }


    override val subject: Subject<String> by lazy { viewModel.pickSubject}
    override val itemGetter: (Int) -> String = { months[it] }
    override val carouselAdapter by lazy { CarouselAdapter(quickAdapter) }
   // override val recyclerView: RecyclerView by lazy { list }


    inner class QuickAdapter(val list: List<String>) : RecyclerView.Adapter<QuickViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuickViewHolder {
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
