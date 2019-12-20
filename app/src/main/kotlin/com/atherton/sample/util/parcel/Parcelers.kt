package com.atherton.sample.util.parcel

import android.os.Parcel
import kotlinx.android.parcel.Parceler
import java.io.IOException

object IOExceptionParceler : Parceler<IOException> {

    override fun create(parcel: Parcel) = IOException(parcel.readString())

    override fun IOException.write(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
    }
}
