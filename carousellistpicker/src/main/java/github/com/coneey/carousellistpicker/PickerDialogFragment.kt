package github.com.coneey.carousellistpicker

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import io.reactivex.subjects.Subject

abstract class PickerDialogFragment<T> : DialogFragment() {

    abstract val carouselAdapter: CarouselAdapter
    abstract val itemGetter: (Int) -> T
    abstract val subject: Subject<T>

    open val getLayoutId = R.layout.base_list_layout
    open val recyclerViewId: Int = R.id.list
    open val removeDialogBackground = true
    open val centerScroll = true
    open val layoutManager: () -> RecyclerView.LayoutManager = { CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = layoutManager.invoke()
        val recyclerView = view.findViewById<RecyclerView>(recyclerViewId)
        (manager as? CarouselLayoutManager)?.setPostLayoutListener(CarouselZoomPostLayoutListener())

        val getter = { position: Int ->
            dismiss()
            subject.onNext(itemGetter.invoke(position))
        }
        carouselAdapter.itemGetter = getter
        recyclerView.adapter = carouselAdapter
        recyclerView.layoutManager = manager
        recyclerView.setHasFixedSize(true)
        if (centerScroll)
            recyclerView.addOnScrollListener(CenterScrollListener())


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (removeDialogBackground)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }


}