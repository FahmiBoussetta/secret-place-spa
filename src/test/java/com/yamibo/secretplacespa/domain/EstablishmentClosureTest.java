package com.yamibo.secretplacespa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamibo.secretplacespa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstablishmentClosureTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstablishmentClosure.class);
        EstablishmentClosure establishmentClosure1 = new EstablishmentClosure();
        establishmentClosure1.setId(1L);
        EstablishmentClosure establishmentClosure2 = new EstablishmentClosure();
        establishmentClosure2.setId(establishmentClosure1.getId());
        assertThat(establishmentClosure1).isEqualTo(establishmentClosure2);
        establishmentClosure2.setId(2L);
        assertThat(establishmentClosure1).isNotEqualTo(establishmentClosure2);
        establishmentClosure1.setId(null);
        assertThat(establishmentClosure1).isNotEqualTo(establishmentClosure2);
    }
}
