package hr.ferit.mlenic.eland.activities

import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hr.ferit.mlenic.eland.R
import hr.ferit.mlenic.eland.adapters.LandAdapter
import hr.ferit.mlenic.eland.listeners.OnLandDeletedListener
import hr.ferit.mlenic.eland.listeners.OnLandSelectedListener
import hr.ferit.mlenic.eland.model.Land
import hr.ferit.mlenic.eland.model.LandViewModel
import kotlin.collections.HashMap
import kotlin.collections.set

class MainActivity : AppCompatActivity(), OnLandSelectedListener, OnLandDeletedListener {

    lateinit var viewModel: LandViewModel
    lateinit var landRV: RecyclerView
    lateinit var fabAdd: FloatingActionButton

    private lateinit var soundPool: SoundPool
    private var soundPoolIsLoaded = false
    private val soundMap: HashMap<Int, Int> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        landRV = findViewById(R.id.rvLands)
        fabAdd = findViewById(R.id.fabAdd)
        landRV.layoutManager = LinearLayoutManager(this)
        val landAdapter = LandAdapter(this, this, this)
        landRV.adapter = landAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(LandViewModel::class.java)

        viewModel.allLand.observe(this, Observer { list ->
            list?.let {
                landAdapter.updateList(it)
            }
        }
        )

        fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, NewLandActivity::class.java)
            startActivity(intent)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.soundPool = SoundPool.Builder().setMaxStreams(1).build()
        } else {
            this.soundPool = SoundPool(1, AudioManager.STREAM_MUSIC, 0)
        }
        this.soundPool.setOnLoadCompleteListener { soundPool, i, i2 -> soundPoolIsLoaded = true }
        this.soundMap[R.raw.sound] =
            this.soundPool.load(this.applicationContext, R.raw.sound, 1)
    }

    override fun onLandSelected(land: Land) {
        val intent = Intent(this@MainActivity, NewLandActivity::class.java)
        intent.putExtra("landType", "Edit")
        intent.putExtra("landName", land.landName)
        intent.putExtra("landCulture", land.landCulture)
        intent.putExtra("landArea", land.landArea)
        intent.putExtra("refNum", land.refNum)
        intent.putExtra("timeStamp", land.timeStamp)
        intent.putExtra("landComment", land.landComment)
        intent.putExtra("landID", land.id)
        intent.putExtra("latitude", land.landLatitude)
        intent.putExtra("longitude", land.landLongitude)

        startActivity(intent)
    }

    override fun onLandDeletedListener(land: Land) {
        AlertDialog.Builder(this).setTitle("Warning")
            .setMessage("Are you sure you want to delete ${land.landName}?")
            .setPositiveButton(
                "Yes"
            ) { dialogInterface, i ->
                viewModel.deleteLand(land)
                if (soundPoolIsLoaded) {
                    this.soundPool.play(this.soundMap[R.raw.sound]!!, 1f, 1f, 1, 0, 1f)
                }
                Toast.makeText(this, "${land.landName} deleted.", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }.create().show()

    }
}