package hr.ferit.mlenic.eland.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import hr.ferit.mlenic.eland.R
import hr.ferit.mlenic.eland.listeners.OnMarkerLocationChangedListener

class MapsFragment(
    markerLocationChangedListener: OnMarkerLocationChangedListener,
    position: LatLng?,
    isEditing: Boolean
) : Fragment() {
    private var marker: Marker? = null

    private val callback = OnMapReadyCallback { googleMap ->
        if (position != null) {
            marker =
                googleMap.addMarker(position.let {
                    MarkerOptions().position(it).title("Here it is")
                })
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        }
        googleMap.setOnMapLongClickListener {
            if (!isEditing) {
                marker?.remove()
                marker = googleMap.addMarker(MarkerOptions().position(it).title("Here it is"))
                markerLocationChangedListener.onMarkerChangedLocationChanged(it)
            }
            else {
                Toast.makeText(this.context, "Location already entered.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}