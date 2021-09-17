package hr.ferit.mlenic.eland.listeners

import hr.ferit.mlenic.eland.model.Land

interface OnLandDeletedListener {
    fun onLandDeletedListener(land: Land)
}