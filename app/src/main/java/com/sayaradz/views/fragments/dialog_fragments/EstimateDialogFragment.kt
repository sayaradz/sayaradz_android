package com.sayaradz.views.fragments.dialog_fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sayaradz.R
import kotlinx.android.synthetic.main.fragment_estimate_dialog.*
import kotlinx.android.synthetic.main.fragment_estimate_dialog.view.*
import kotlinx.android.synthetic.main.fragment_order_dialog.view.confirm_button
import kotlinx.android.synthetic.main.fragment_order_dialog.view.dialog_title


class EstimateDialogFragment : DialogFragment() {

    // Use this instance of the interface to deliver action events
    private lateinit var listener: OrderDialogListener
    private lateinit var normalOrder: Button
    private lateinit var title: TextView

    private lateinit var noInternetTextView: TextView
    private lateinit var content: ConstraintLayout
    private lateinit var progressBar: ProgressBar


    interface OrderDialogListener {
        fun onOkClick(dialog: DialogFragment)
        fun onEstimate(
            dialog: DialogFragment,
            progressBar: ProgressBar,
            noInternet: TextView,
            content: ConstraintLayout,
            price: TextView
        )
    }

    fun setOnItemClickListener(mItemClickListener: OrderDialogListener) {
        this.listener = mItemClickListener
    }

    // Override the Fragment.onAttach() method to instantiate the ComposeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ComposeDialogListener so we can send events to the host
            listener = context as OrderDialogListener

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
            val view = inflater.inflate(R.layout.fragment_estimate_dialog, container)

            normalOrder = view.confirm_button
            title = view.dialog_title

            progressBar = view.progressBarDialog_estimate
            noInternetTextView = view.no_internet_dialog_estimate
            content = content_view_estimate

            this.listener.onEstimate(
                this,
                progressBar,
                noInternetTextView,
                content,
                title
            )

            normalOrder.setOnClickListener {
                listener.onOkClick(
                    this
                )
            }


            builder.setView(view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
