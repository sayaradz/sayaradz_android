package com.sayaradz.views.fragments.DialogFragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.views.adapters.ModelChooseComposeCarAdapter
import kotlinx.android.synthetic.main.fragment_compose_model_dialog.view.*


class ComposeModelDialogFragment : DialogFragment(), ModelChooseComposeCarAdapter.ButtonStateListener {

    // Use this instance of the interface to deliver action events
    private lateinit var listener: ComposeDialogListener
    private lateinit var confirmChoice: TextView
    private lateinit var modelChoiceList: RecyclerView

    private lateinit var noInternetTextView: TextView
    private lateinit var content: ConstraintLayout
    private lateinit var progressBar: ProgressBar


    private lateinit var adapter: ModelChooseComposeCarAdapter
    private lateinit var title: TextView


    interface ComposeDialogListener {
        fun onNextClick(
            dialog: DialogFragment,
            progressBar: ProgressBar,
            noInternet: TextView,
            content: ConstraintLayout
        )

        fun onPopulateModels(recyclerView: RecyclerView)

    }


    // Override the Fragment.onAttach() method to instantiate the ComposeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ComposeDialogListener so we can send events to the host
            listener = context as ComposeDialogListener

        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement ComposeDialogListener")
            )
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val container: ViewGroup = ConstraintLayout(it)
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_compose_model_dialog, container)
            modelChoiceList = view.choice_model
            confirmChoice = view.next_button
            title = view.dialog_title_model

            listener.onPopulateModels(modelChoiceList)

            adapter = modelChoiceList.adapter as ModelChooseComposeCarAdapter
            adapter.setOnButtonStateListener(this)
            progressBar = view.progressBarDialog
            noInternetTextView = view.no_internet_dialog
            content = view.content_view_Dialog


            title.setText(R.string.Choose_your_model)
            confirmChoice.setOnClickListener {
                listener.onNextClick(
                    this, progressBar, noInternetTextView, content
                )
            }

            builder.setView(view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onButtonStateChanged() {
        confirmChoice.isEnabled = adapter.tracker!!.hasSelection()
        if (adapter.tracker!!.hasSelection()) confirmChoice.alpha = 1F
        else confirmChoice.alpha = 0.4F
    }


}
