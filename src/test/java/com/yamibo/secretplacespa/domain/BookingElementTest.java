package com.yamibo.secretplacespa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamibo.secretplacespa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookingElementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookingElement.class);
        BookingElement bookingElement1 = new BookingElement();
        bookingElement1.setId(1L);
        BookingElement bookingElement2 = new BookingElement();
        bookingElement2.setId(bookingElement1.getId());
        assertThat(bookingElement1).isEqualTo(bookingElement2);
        bookingElement2.setId(2L);
        assertThat(bookingElement1).isNotEqualTo(bookingElement2);
        bookingElement1.setId(null);
        assertThat(bookingElement1).isNotEqualTo(bookingElement2);
    }
}
