///*
// * Copyright (C) 2014 Francesco Azzola
// *  Surviving with Android (http://www.survivingwithandroid.com)
// *
// *  Licensed under the Apache License, Version 2.0 (the "License");
// *  you may not use this file except in compliance with the License.
// *  You may obtain a copy of the License at
// *
// *       http://www.apache.org/licenses/LICENSE-2.0
// *
// *  Unless required by applicable law or agreed to in writing, software
// *  distributed under the License is distributed on an "AS IS" BASIS,
// *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *  See the License for the specific language governing permissions and
// *  limitations under the License.
// */
//package com.resistthedevil5947.bible;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.List;
//
//public class VerseAdapterJava extends RecyclerView.Adapter<VerseAdapterJava.ContactViewHolder> {
//
//    private List<VerseInfo> verseList;
//
//    public VerseAdapterJava(List<VerseInfo> verseList) {
//        this.verseList = verseList;
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return verseList.size();
//    }
//
//    @Override
//    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
//        VerseInfo vi = verseList.get(i);
//        String reference = vi.book + vi.chapter + vi.verse;
//        contactViewHolder.reference.setText(reference);
//        contactViewHolder.word.setText(vi.word);
//
//    }
//
//    @Override
//    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//            View itemView = LayoutInflater.
//                    from(viewGroup.getContext()).
//                    inflate(R.layout.card_layout, viewGroup, false);
//
//            return new ContactViewHolder(itemView);
//    }
//
//    public static class ContactViewHolder extends RecyclerView.ViewHolder {
//
//        protected TextView reference;
//        protected TextView word;
//
//
//        public ContactViewHolder(View v) {
//            super(v);
//            reference =  (TextView) v.findViewById(R.id.reference);
//            word = (TextView)  v.findViewById(R.id.word);
//
//        }
//    }
//}
