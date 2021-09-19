package hr.ferit.mlenic.eland.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import hr.ferit.mlenic.eland.R
import hr.ferit.mlenic.eland.model.Land
import hr.ferit.mlenic.eland.model.LandViewModel
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.addTextChangedListener
import java.text.SimpleDateFormat
import java.util.*
import android.text.TextWatcher
import com.google.android.gms.maps.model.LatLng
import hr.ferit.mlenic.eland.fragments.MapsFragment
import hr.ferit.mlenic.eland.listeners.OnMarkerLocationChangedListener

class NewLandActivity : AppCompatActivity(), OnMarkerLocationChangedListener {

    lateinit var landNameEdit: TextInputEditText
    lateinit var landCultureEdit: TextInputEditText
    lateinit var landAreaEdit: TextInputEditText
    lateinit var landRefNum: TextInputEditText
    lateinit var landCommentEdit: TextInputEditText
    lateinit var updateBtn: Button

    lateinit var viewModel: LandViewModel
    var landID = -1;
    private var position : LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_land)

        actionBar?.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LandViewModel::class.java)

        landNameEdit = findViewById(R.id.tietName)
        landRefNum = findViewById(R.id.tietRefNum)
        landCultureEdit = findViewById(R.id.tietCulture)
        landAreaEdit = findViewById(R.id.tietArea)
        landCommentEdit = findViewById(R.id.tietComm)
        updateBtn = findViewById(R.id.bUpdate)

        val landType = intent.getStringExtra("landType")
        if (landType.equals("Edit")) {
            val landName = intent.getStringExtra("landName")
            val landRef = intent.getIntExtra("refNum", 0)
            val landCulture = intent.getStringExtra("landCulture")
            val landArea = intent.getDoubleExtra("landArea", 0.0)
            val landComment = intent.getStringExtra("landComment")
            position = LatLng(intent.getDoubleExtra("latitude", 0.0), intent.getDoubleExtra("longitude", 0.0))

            landID = intent.getIntExtra("landID", -1)

            updateBtn.setText(getString(R.string.updateLand))
            landNameEdit.setText(landName)
            landRefNum.setText(landRef.toString())
            landCultureEdit.setText(landCulture)
            landAreaEdit.setText(landArea.toString())
            landCommentEdit.setText(landComment)

            supportFragmentManager.beginTransaction().add(R.id.map, MapsFragment(this, position, true))
                .commit()

        } else {
            updateBtn.text = getString(R.string.addLand)
            supportFragmentManager.beginTransaction().add(R.id.map, MapsFragment(this, null, false))
                .commit()
        }

        updateBtn.setOnClickListener {
            val landName = landNameEdit.text.toString()
            val landCulture = landCultureEdit.text.toString()
            val landComment = landCommentEdit.text.toString()

            if (landType.equals("Edit")) {
                if (landName.isNotEmpty() && landRefNum.text.toString()
                        .isNotEmpty() && landCulture.isNotEmpty() && landAreaEdit.text.toString()
                        .isNotEmpty() && landComment.isNotEmpty() && position != null
                ) {
                    val landRef = landRefNum.text.toString().toInt()
                    val landArea = landAreaEdit.text.toString().toDouble()

                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                    val currDateAndTime: String = dateFormat.format(Date())
                    val existingPosition = LatLng(intent.getDoubleExtra("latitude", 0.0), intent.getDoubleExtra("longitude", 0.0))
                    val updatedLand = Land(
                        landRef,
                        landName,
                        currDateAndTime,
                        landCulture,
                        landArea,
                        existingPosition.latitude,
                        existingPosition.longitude,
                        landComment
                    )
                    updatedLand.id = landID
                    viewModel.updateLand(updatedLand)
                    Toast.makeText(this, "$landName updated.", Toast.LENGTH_LONG).show()
                    this.finish()
                } else {
                    Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (landName.isNotEmpty() && landRefNum.text.toString()
                        .isNotEmpty() && landCulture.isNotEmpty() && landAreaEdit.text.toString()
                        .isNotEmpty() && landComment.isNotEmpty() && position != null
                ) {
                    val landRef = landRefNum.text.toString().toInt()
                    val landArea = landAreaEdit.text.toString().toDouble()

                    val dateFormat = SimpleDateFormat("dd MMM, yyyy - HH:mm", Locale.getDefault())
                    val currDateAndTime: String = dateFormat.format(Date())
                    viewModel.addLand(
                        Land(
                            landRef,
                            landName,
                            currDateAndTime,
                            landCulture,
                            landArea,
                            position!!.latitude,
                            position!!.longitude,
                            landComment
                        )
                    )
                    Toast.makeText(this, "$landName added.", Toast.LENGTH_LONG).show()
                    this.finish()
                } else {
                    Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onMarkerChangedLocationChanged(latLng: LatLng) {
        this.position = latLng
        Toast.makeText(this, latLng.toString(), Toast.LENGTH_SHORT).show()
    }
}
