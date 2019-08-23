package com.sayaradz.views.fragments.dialog_fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import com.sayaradz.R
import kotlinx.android.synthetic.main.fragment_order_dialog.view.*


class OrderDialogFragment : DialogFragment() {

    // Use this instance of the interface to deliver action events
    private lateinit var listener: OrderDialogListener
    private lateinit var normalOrder: Button
    private lateinit var acceleratedOrder: Button
    private lateinit var title: TextView


    interface OrderDialogListener {
        fun onDialogNormalOrderClick(dialog: DialogFragment)
        fun onDialogAcceleratedOrderClick(dialog: DialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the ComposeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        /*try {
            // Instantiate the ComposeDialogListener so we can send events to the host
            listener = context as OrderDialogListener

        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException(
                (context.toString() +
                        " must implement ComposeDialogListener")
            )
        }*/
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val container: ViewGroup = ConstraintLayout(it)
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_order_dialog, container)

            normalOrder = view.findViewById(R.id.dialog_normal_button)
            acceleratedOrder = view.findViewById(R.id.confirm_button)
            title = view.dialog_title

            title.setText(R.string.order_dialog_title)

            normalOrder.setOnClickListener {
                listener.onDialogNormalOrderClick(
                    this
                )
            }

            acceleratedOrder.setOnClickListener {
                listener.onDialogAcceleratedOrderClick(
                    this
                )
            }

            builder.setView(view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}
