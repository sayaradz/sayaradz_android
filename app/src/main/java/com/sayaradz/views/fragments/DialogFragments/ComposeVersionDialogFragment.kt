package com.sayaradz.views.fragments.DialogFragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.sayaradz.R
import com.sayaradz.views.adapters.VersionChooseComposeCarAdapter
import kotlinx.android.synthetic.main.fragment_compose_version_dialog.view.*


class ComposeVersionDialogFragment : DialogFragment(), VersionChooseComposeCarAdapter.ButtonStateListener {

    // Use this instance of the interface to deliver action events
    private lateinit var listener: ComposeDialogListener
    private lateinit var confirmChoice: TextView
    private lateinit var versionChoiceList: RecyclerView
    private lateinit var title: TextView

    private lateinit var adapter: VersionChooseComposeCarAdapter


    interface ComposeDialogListener {
        fun onConfirmComposeClick(dialog: DialogFragment)
        fun onPopulateVersions(recyclerView: RecyclerView)
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
            val view = inflater.inflate(R.layout.fragment_compose_version_dialog, container)

            confirmChoice = view.confirm_next_button
            versionChoiceList = view.choice_version
            title = view.dialog_title_version

            title.setText(R.string.Choose_your_version)
            confirmChoice.setOnClickListener {
                listener.onConfirmComposeClick(
                    this
                )
            }

            listener.onPopulateVersions(versionChoiceList)
            adapter = versionChoiceList.adapter as VersionChooseComposeCarAdapter
            adapter.setOnButtonStateListener(this)

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
