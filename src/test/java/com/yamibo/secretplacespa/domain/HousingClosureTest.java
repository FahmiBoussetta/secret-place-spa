package com.yamibo.secretplacespa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamibo.secretplacespa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HousingClosureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HousingClosure.class);
        HousingClosure housingClosure1 = new HousingClosure();
        housingClosure1.setId(1L);
        HousingClosure housingClosure2 = new HousingClosure();
        housingClosure2.setId(housingClosure1.getId());
        assertThat(housingClosure1).isEqualTo(housingClosure2);
        housingClosure2.setId(2L);
        assertThat(housingClosure1).isNotEqualTo(housingClosure2);
        housingClosure1.setId(null);
        assertThat(housingClosure1).isNotEqualTo(housingClosure2);
    }
}
