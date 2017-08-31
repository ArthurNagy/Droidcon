package com.arthurnagy.droidconberlin.feature.agenda

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.MyAgendaBinding
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.feature.session.SessionDetailActivity
import com.arthurnagy.droidconberlin.feature.shared.StickyHeaderItemDecoration
import com.arthurnagy.droidconberlin.setupToolbar

class MyAgendaFragment : DroidconFragment() {

    private lateinit var binding: MyAgendaBinding
    private val viewModel: MyAgendaViewModel by lazy { getViewModel(MyAgendaViewModel::class.java) }
    private var snackbar: Snackbar? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_agenda, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)
        binding.viewModel = viewModel
        binding.recyclerView.addItemDecoration(object : StickyHeaderItemDecoration(context) {
            override fun isHeader(position: Int) = viewModel.isHeaderItem(position)

            override fun getHeaderTitle(position: Int) = viewModel.getHeaderItemTitle(position)
        })
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun getSwipeDirs(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?)
                    = if (viewModel.adapter.canRemoveItem()) super.getSwipeDirs(recyclerView, viewHolder) else 0


            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.adapter.removeItem(binding.recyclerView.getChildAdapterPosition(viewHolder.itemView))
                snackbar = Snackbar.make(binding.root, "The repository needs to be updated", Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            viewModel.adapter.undoItemRemoval()
                            binding.recyclerView.smoothScrollToPosition(viewModel.adapter.positionOfItemToBeRemoved)
                        }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    override fun onDismissed(snackbar: Snackbar, @BaseTransientBottomBar.BaseCallback.DismissEvent event: Int) {
                        //TODO: update repository from the adapter
                        viewModel.adapter.removeItemToBeRemoved()
                    }
                })
                snackbar?.show()
            }
        }).attachToRecyclerView(binding.recyclerView)
        viewModel.subscribe()
        viewModel.load()
        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.sessionClick -> startActivity(SessionDetailActivity.getStartIntent(context, viewModel.sessionClick!!.id))
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        snackbar?.dismiss()
    }
}