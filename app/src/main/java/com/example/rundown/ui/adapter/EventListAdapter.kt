package com.example.rundown.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rundown.R
import com.example.rundown.model.Event
import com.squareup.picasso.Picasso

class EventListAdapter: RecyclerView.Adapter<EventListAdapter.EventViewHolder>() {

    val badgeMap: HashMap<String, String> = hashMapOf(
        "Arizona Cardinals" to "https://www.thesportsdb.com/images/media/team/badge/xvuwtw1420646838.png",
        "Atlanta Falcons" to "https://www.thesportsdb.com/images/media/team/badge/rrpvpr1420658174.png",
        "Baltimore Ravens" to "https://www.thesportsdb.com/images/media/team/badge/einz3p1546172463.png",
        "Buffalo Bills" to "https://www.thesportsdb.com/images/media/team/badge/6pb37b1515849026.png",
        "Carolina Panthers" to "https://www.thesportsdb.com/images/media/team/badge/xxyvvy1420940478.png",
        "Chicago Bears" to "https://www.thesportsdb.com/images/media/team/badge/uwtwtv1420941123.png",
        "Cincinnati Bengals" to "https://www.thesportsdb.com/images/media/team/badge/qqtwwv1420941670.png",
        "Cleveland Browns" to "https://www.thesportsdb.com/images/media/team/badge/squvxy1420942389.png",
        "Dallas Cowboys" to "https://www.thesportsdb.com/images/media/team/badge/wrxssu1450018209.png",
        "Denver Broncos" to "https://www.thesportsdb.com/images/media/team/badge/upsspx1421635647.png",
        "Detroit Lions" to "https://www.thesportsdb.com/images/media/team/badge/lgsgkr1546168257.png",
        "Green Bay Packers" to "https://www.thesportsdb.com/images/media/team/badge/rqpwtr1421434717.png",
        "Houston Texans" to "https://www.thesportsdb.com/images/media/team/badge/wqyryy1421436627.png",
        "Indianapolis Colts" to "https://www.thesportsdb.com/images/media/team/badge/wqqvpx1421434058.png",
        "Jacksonville Jaguars" to "https://www.thesportsdb.com/images/media/team/badge/0mrsd41546427902.png",
        "Kansas City Chiefs" to "https://www.thesportsdb.com/images/media/team/badge/936t161515847222.png",
        "Las Vegas Raiders" to "https://www.thesportsdb.com/images/media/team/badge/xqusqy1421724291.png",
        "Los Angeles Chargers" to "https://www.thesportsdb.com/images/media/team/badge/wbhu3a1548320628.png",
        "Los Angeles Rams" to "https://www.thesportsdb.com/images/media/team/badge/8e8v4i1599764614.png",
        "Miami Dolphins" to "https://www.thesportsdb.com/images/media/team/badge/trtusv1421435081.png",
        "Minnesota Vikings" to "https://www.thesportsdb.com/images/media/team/badge/qstqqr1421609163.png",
        "New England Patriots" to "https://www.thesportsdb.com/images/media/team/badge/xtwxyt1421431860.png",
        "New Orleans Saints" to "https://www.thesportsdb.com/images/media/team/badge/nd46c71537821337.png",
        "New York Giants" to "https://www.thesportsdb.com/images/media/team/badge/vxppup1423669459.png",
        "New York Jets" to "https://www.thesportsdb.com/images/media/team/badge/hz92od1607953467.png",
        "Philadelphia Eagles" to "https://www.thesportsdb.com/images/media/team/badge/pnpybf1515852421.png",
        "Pittsburgh Steelers" to "https://www.thesportsdb.com/images/media/team/badge/2975411515853129.png",
        "San Francisco 49ers" to "https://www.thesportsdb.com/images/media/team/badge/bqbtg61539537328.png",
        "Seattle Seahawks" to "https://www.thesportsdb.com/images/media/team/badge/wwuqyr1421434817.png",
        "Tampa Bay Buccaneers" to "https://www.thesportsdb.com/images/media/team/badge/2dfpdl1537820969.png",
        "Tennessee Titans" to "https://www.thesportsdb.com/images/media/team/badge/m48yia1515847376.png",
        "Washington" to "https://www.thesportsdb.com/images/media/team/badge/1m3mzp1595609069.png"
    )

    private var myList = emptyList<Event>()

    inner class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sports_event, parent, false))
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        // names
        val awayTeam = myList[position].strAwayTeam
        val homeTeam = myList[position].strHomeTeam
        holder.itemView.findViewById<TextView>(R.id.textAwayTeam).text = awayTeam
        holder.itemView.findViewById<TextView>(R.id.textHomeTeam).text = homeTeam

        // scores
        holder.itemView.findViewById<TextView>(R.id.textScoreAway).text =
            myList[position].intAwayScore.toString()
        holder.itemView.findViewById<TextView>(R.id.textScoreHome).text =
            myList[position].intHomeScore.toString()

        // date
        holder.itemView.findViewById<TextView>(R.id.textDate).text =
            myList[position].dateEvent

        // team badges
        val picasso = Picasso.get()
        val imgAway = holder.itemView.findViewById<ImageView>(R.id.imageAwayTeam)
        val imgHome = holder.itemView.findViewById<ImageView>(R.id.imageHomeTeam)
        picasso.load(badgeMap[awayTeam]).into(imgAway)
        picasso.load(badgeMap[homeTeam]).into(imgHome)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(newList: List<Event>) {
        myList = newList
        notifyDataSetChanged()
    }

}