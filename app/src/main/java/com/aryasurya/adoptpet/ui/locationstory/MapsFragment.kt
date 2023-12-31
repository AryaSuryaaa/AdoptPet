package com.aryasurya.adoptpet.ui.locationstory

import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.aryasurya.adoptpet.R
import com.aryasurya.adoptpet.data.Result
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.ui.ViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            getMyLocation()
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }


        getMyLocation()
        setMapStyle()
    }

    override fun onCreateView(
        inflater: LayoutInflater ,
        container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps , container , false)
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        viewModel.getListMap().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    if (result.data.isNotEmpty()) {
                        result.data.forEach{ data ->
                            if (data.lat != null && data.lon != null) {
                                setStoryMarker(data)
                            }
                        }
                        Log.d("Maps" , "${result.data}")

                    }
                }
                is Result.Error -> {}
                else -> {}
            }
        }
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun getMyLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    val lat = it.latitude
                    val lon = it.longitude

                    val myLocation = LatLng(lat, lon)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15f))
                }
            }

        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
//
    private fun setStoryMarker(data: ListStoryItem) {
        if (data.lat != null && data.lon != null) {
            val latLng = LatLng(data.lat.toDouble() , data.lon.toDouble())
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(data.name)
                    .snippet(data.description)
            )
        }
    }

    companion object {
        private const val TAG = "MapsFragment"
    }
}
