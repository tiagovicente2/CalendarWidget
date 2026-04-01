package com.calendar.widget.parser

import com.calendar.widget.data.remote.dto.IcalEventDto
import net.fortuna.ical4j.data.CalendarBuilder
import net.fortuna.ical4j.model.Property
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.model.property.DtStart
import net.fortuna.ical4j.model.property.DtEnd
import java.io.StringReader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Parser for iCal (.ics) format calendar data.
 * Uses iCal4j library to parse VCALENDAR and VEVENT components.
 */
@Singleton
class IcalParser @Inject constructor() {

    /**
     * Parses iCal data string into a list of event DTOs.
     * 
     * @param icalData Raw iCal data string
     * @return List of parsed events
     */
    fun parse(icalData: String): List<IcalEventDto> {
        val builder = CalendarBuilder()
        val calendar = builder.build(StringReader(icalData))
        
        return calendar.components
            .filterIsInstance<VEvent>()
            .mapNotNull { parseEvent(it) }
    }

    private fun parseEvent(vevent: VEvent): IcalEventDto? {
        val uid = vevent.getProperty<Property>(Property.UID)?.value ?: return null
        val summary = vevent.getProperty<Property>(Property.SUMMARY)?.value ?: "Untitled"
        val location = vevent.getProperty<Property>(Property.LOCATION)?.value
        val description = vevent.getProperty<Property>(Property.DESCRIPTION)?.value

        val startDate = vevent.getProperty<DtStart>(Property.DTSTART)
        val endDate = vevent.getProperty<DtEnd>(Property.DTEND)

        val isAllDay = startDate?.isUtc == false && startDate.date is net.fortuna.ical4j.model.Date

        val startTime = startDate?.date?.time ?: return null

        val endTime = if (isAllDay) {
            endDate?.date?.time ?: (startTime + 86400000) // Next day
        } else {
            endDate?.date?.time ?: (startTime + 3600000) // Default 1 hour
        }

        return IcalEventDto(
            uid = uid,
            summary = summary,
            location = location,
            description = description,
            startTime = startTime,
            endTime = endTime,
            isAllDay = isAllDay
        )
    }
}
