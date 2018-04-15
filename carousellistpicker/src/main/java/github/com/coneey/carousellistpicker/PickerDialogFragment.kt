package github.com.coneey.carousellistpicker

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
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment
import io.reactivex.disposables.Disposable

abstract class PickerDialogFragment<T> : SupportBlurDialogFragment() {

    abstract val recyclerView: RecyclerView
    abstract val carouselAdapter: CarouselAdapter<T>


    open val getLayoutId = R.layout.base_list_layout
    open val removeDialogBackground = true
    open val centerScroll = true
    open val layoutManager: RecyclerView.LayoutManager = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true)

    private var disposable: Disposable? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (layoutManager as? CarouselLayoutManager)?.setPostLayoutListener(CarouselZoomPostLayoutListener())

        recyclerView.adapter = carouselAdapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        if (centerScroll)
            recyclerView.addOnScrollListener(CenterScrollListener())
        disposable = carouselAdapter.subject.subscribe { dismiss() }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId, container, false)
    }

    override fun getBlurRadius(): Int = 2

    override fun getDownScaleFactor(): Float = 8.0F

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        if (removeDialogBackground)
            dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

}