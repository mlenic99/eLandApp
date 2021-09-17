package hr.ferit.mlenic.eland.listeners

import hr.ferit.mlenic.eland.model.Land

interface OnLandSelectedListener {
    fun onLandSelected(land: Land)
}