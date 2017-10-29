package com.arthurnagy.droidcon.feature.agenda

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidcon.MyAgendaBinding
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.architecture.DroidconFragment
import com.arthurnagy.droidcon.util.color
import com.arthurnagy.droidcon.util.setupToolbar

class MyAgendaFragment : DroidconFragment() {

    private lateinit var binding: MyAgendaBinding
    private val viewModel: MyAgendaViewModel by lazy { getViewModel(MyAgendaViewModel::class.java) }
    private var snackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_agenda, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)
        binding.viewModel = viewModel

        binding.refreshLayout.setColorSchemeColors(context.color(R.color.accent), context.color(R.color.primary))

//        binding.recyclerView.adapter = viewModel.adapter
//        binding.recyclerView.addItemDecoration(object : StickyHeaderItemDecoration(context) {
//            override fun isHeader(position: Int) = viewModel.isHeaderItem(position)
//
//            override fun getHeaderTitle(position: Int) = viewModel.getHeaderItemTitle(position)
//        })
//        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder)
//                    = if (viewModel.adapter.canRemoveItem(binding.recyclerView.getChildAdapterPosition(viewHolder.itemView))) super.getSwipeDirs(recyclerView, viewHolder) else 0
//
//
//            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                viewModel.adapter.removeItem(binding.recyclerView.getChildAdapterPosition(viewHolder.itemView))
//                snackbar = Snackbar.make(binding.root, R.string.session_removed, Snackbar.LENGTH_LONG).setAction(R.string.undo) {
//                            viewModel.adapter.undoItemRemoval()
//                            binding.recyclerView.smoothScrollToPosition(viewModel.adapter.positionOfItemToBeRemoved)
//                        }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
//                    override fun onDismissed(snackbar: Snackbar, @BaseTransientBottomBar.BaseCallback.DismissEvent event: Int) {
//                        viewModel.removeSavedSessionFromAgenda()
//                    }
//                })
//                snackbar?.show()
//            }
//        }).attachToRecyclerView(binding.recyclerView)
//
//        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                when (propertyId) {
//                    BR.sessionClick -> SessionDetailActivity.start(context, viewModel.sessionClick!!.id)
//                }
//            }
//        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.subscribe()
        viewModel.load()
    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
        viewModel.unsubscribe()
    }
}