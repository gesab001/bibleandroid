/*
 * Copyright (C) 2014 Francesco Azzola
 *  Surviving with Android (http://www.survivingwithandroid.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.resistthedevil5947.bible

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.ArrayList

class VerseAdapter(private val verseList: List<VerseInfo>) : RecyclerView.Adapter<VerseAdapter.ContactViewHolder>() {


    override fun getItemCount(): Int {
        return verseList.size
    }

    override fun onBindViewHolder(contactViewHolder: ContactViewHolder, i: Int) {
        val vi = verseList[i]
        val reference = vi.book + vi.chapter + vi.verse
        contactViewHolder.reference.text = reference
        contactViewHolder.word.text = vi.word

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ContactViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)

        return ContactViewHolder(itemView)
    }

    class ContactViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        var reference: TextView
        var word: TextView


        init {
            reference = v.findViewById(R.id.reference) as TextView
            word = v.findViewById(R.id.word) as TextView
            v.setOnClickListener {

                val intent = Intent(v.context, ChapterActivity::class.java)
                val bundle = Bundle()
                bundle.putCharSequence("reference", reference.text)
                intent.putExtras(bundle)
                v.context.startActivity(intent)
            }

        }

    }
}
