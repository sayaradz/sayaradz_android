package com.sayaradz.views.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sayaradz.R
import kotlinx.android.synthetic.main.fragment_compare_dialog.view.*
import okhttp3.internal.Version


class CompareDialogFragment : DialogFragment() {

    // Use this instance of the interface to deliver action events
    private lateinit var listener: OrderDialogListener
    private lateinit var confirmChoice: Button
    private lateinit var choiceList1: Spinner
    private lateinit var choiceList2: Spinner
    internal lateinit var title: TextView


    interface OrderDialogListener {
        fun onConfirmClick(dialog: DialogFragment,version1:Int,version2:Int )
        fun onFillSpinner(spinner: Spinner)
    }

    // Override the Fragment.onAttach() method to instantiate the OrderDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the OrderDialogListener so we can send events to the host
            listener = context as OrderDialogListener

        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement OrderDialogListener")
            )
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val container: ViewGroup = ConstraintLayout(it)
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_compare_dialog, container)

            confirmChoice = view.confirm_button
            confirmChoice.setOnClickListener {
                listener.onConfirmClick(
                    this,
                    choiceList1.selectedItemPosition,
                    choiceList2.selectedItemPosition
                )
            }

            choiceList1 = view.choiceCar1
            choiceList2 = view.choiceCar2
            listener.onFillSpinner(choiceList1)
            listener.onFillSpinner(choiceList2)

            builder.setView(view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
