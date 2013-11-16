package net.opihackday.agileniagara.service

import org.joda.time.LocalDate
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * User: danielwoods
 * Date: 11/16/13
 */
@Service
class RabbitService {

  public static final String USER_EXCHANGE = "users";
  public static final String BOOKING_EXCHANGE = "booking";

  @Autowired
  RabbitTemplate rabbitTemplate

    Map getUserByEmail(String email) {
        (Map)rabbitTemplate.convertSendAndReceive(USER_EXCHANGE, "lookup", email)
    }

    Map getSeasons() {
        (Map)rabbitTemplate.convertSendAndReceive(BOOKING_EXCHANGE, "season.list", [:])
    }

    Map getLocations() {
        (Map)rabbitTemplate.convertSendAndReceive(BOOKING_EXCHANGE, "location.list", [:])
    }

    Map getBookings(String locationId, String seasonId) {
        (Map)rabbitTemplate.convertSendAndReceive(BOOKING_EXCHANGE, "booking.list", [locationId: locationId, seasonId: seasonId]);
    }

    Map createBooking(String email, String locationId, String startDate, String endDate) {
        def request = [username: email, locationId: locationId, startDate: startDate, endDate: endDate]
        (Map)rabbitTemplate.convertSendAndReceive(BOOKING_EXCHANGE, "booking.request", request)
    }
}
