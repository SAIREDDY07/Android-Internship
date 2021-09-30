package com.example.googlenews.DataModel

import android.util.Log

class DataModel(
    var titles: String,
    var details: String,
    var image: String,
    var links: String,
    var dateandtime: String
) {
    companion object {
        var titleAscendingComparator =
            java.util.Comparator<DataModel> { o1, o2 -> o1.titles.compareTo(o2.titles) }
        var titleDescendingComparator =
            java.util.Comparator<DataModel> { o1, o2 -> o2.titles.compareTo(o1.titles) }
        var dateAscendingComparator =
            java.util.Comparator<DataModel> { o1, o2 -> o1.dateandtime.compareTo(o2.dateandtime) }
        var dateDescendingComparator =
            java.util.Comparator<DataModel> { o1, o2 -> o2.dateandtime.compareTo(o1.dateandtime) }
    }
}
