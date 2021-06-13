package com.yamibo.secretplacespa.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yamibo.secretplacespa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Manage.class);
        Manage manage1 = new Manage();
        manage1.setId(1L);
        Manage manage2 = new Manage();
        manage2.setId(manage1.getId());
        assertThat(manage1).isEqualTo(manage2);
        manage2.setId(2L);
        assertThat(manage1).isNotEqualTo(manage2);
        manage1.setId(null);
        assertThat(manage1).isNotEqualTo(manage2);
    }
}
