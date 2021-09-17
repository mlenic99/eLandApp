package hr.ferit.mlenic.eland.listeners

import com.google.android.gms.maps.model.LatLng

interface OnMarkerLocationChangedListener {
    fun onMarkerChangedLocationChanged(latLng: LatLng)
}