package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.SAN;
import com.woodev.noticerti.repository.SANRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SANServiceImplTest {

    @InjectMocks
    private SANServiceImpl sanService;

    @Mock
    private SANRepository sanRepository;

    @Test
    void findAllByCertificate() {
        // given
        Long certificateId = 1L;
        List<SAN> sans = List.of(
                new SAN(1L, "test1.com", Certificate.builder().id(1L).build(), false),
                new SAN(1L, "dev.test1.com", Certificate.builder().id(1L).build(), false)
        );

        when(sanRepository.findAllByCertificateId(any(Long.class))).thenReturn(sans);

        // when
        sanService.findAllByCertificate(certificateId);

        // then
        assertThat(sans).isNotNull();
        assertThat(sans.size()).isEqualTo(2);
        verify(sanRepository, times(1)).findAllByCertificateId(certificateId);
    }
}