package com.akzhey.contacts.presentation.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.akzhey.contacts.R

class NumberAdapter(
    private val context: Context,
    private val typeList: List<String>,
    private val selectedTypeList: MutableSet<Int>
) :
    BaseAdapter() {
    override fun getCount(): Int {
        return typeList.size - 1
    }

    override fun getItem(p0: Int): Any {
        return typeList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_spinner, p2, false)
        val item = getItem(p0) as String
        view.findViewById<TextView>(R.id.tvSpinner).text = item
        //Setting background color of disabled item
        val color = if (selectedTypeList.contains(p0)) {
            Color.GRAY
        } else {
            Color.TRANSPARENT
        }
        view.findViewById<ConstraintLayout>(R.id.clSpinnerBackground).setBackgroundColor(color)
        return view
    }

    /**
     * Disable clicking of item, when already selected
     */
    override fun isEnabled(position: Int): Boolean {
        return !selectedTypeList.contains(position)
    }
}