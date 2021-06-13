package com.yamibo.secretplacespa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamibo.secretplacespa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HousingTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HousingTemplate.class);
        HousingTemplate housingTemplate1 = new HousingTemplate();
        housingTemplate1.setId(1L);
        HousingTemplate housingTemplate2 = new HousingTemplate();
        housingTemplate2.setId(housingTemplate1.getId());
        assertThat(housingTemplate1).isEqualTo(housingTemplate2);
        housingTemplate2.setId(2L);
        assertThat(housingTemplate1).isNotEqualTo(housingTemplate2);
        housingTemplate1.setId(null);
        assertThat(housingTemplate1).isNotEqualTo(housingTemplate2);
    }
}
