package dev.stukalo.asteroids.util

import android.os.Parcel
import android.os.Parcelable
import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.concurrent.TimeUnit

class RangeDateValidator(
    private val allowedRange: Int,
) : DateValidator {
    private var datePicker: MaterialDatePicker<Pair<Long?, Long?>>? = null

    constructor(parcel: Parcel) : this(parcel.readInt())

    fun setDatePicker(datePicker: MaterialDatePicker<Pair<Long?, Long?>>) {
        this.datePicker = datePicker
    }

    override fun isValid(date: Long): Boolean {
        val selection = datePicker?.selection
        selection?.first?.let { startDate ->
            val days: Long = allowedRange * TimeUnit.DAYS.toMillis(1)
            if (date > startDate + days) {
                return false
            }
            if (date < startDate) {
                return false
            }
        }
        return true
    }

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int,
    ) {
        parcel.writeInt(allowedRange)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RangeDateValidator> {
        override fun createFromParcel(parcel: Parcel): RangeDateValidator {
            return RangeDateValidator(parcel)
        }

        override fun newArray(size: Int): Array<RangeDateValidator?> {
            return arrayOfNulls(size)
        }
    }
}
