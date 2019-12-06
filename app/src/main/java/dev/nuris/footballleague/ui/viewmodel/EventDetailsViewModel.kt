package dev.nuris.footballleague.ui.viewmodel

import androidx.lifecycle.ViewModel
import dev.nuris.footballleague.repository.EventDetailsRepository

class EventDetailsViewModel(private val eventDetailsRepository: EventDetailsRepository) : ViewModel() {

    val eventDetailResponse by lazy {
        eventDetailsRepository.getEventResponse
    }

    fun getEventDetail(id: String) {
        eventDetailsRepository.getEventDetail(id)
    }
}