package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.SubjectAlternativeName;
import com.woodev.noticerti.repository.SubjectAlternativeNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectAlternativeNameServiceImplTest {

    @InjectMocks
    private SubjectAlternativeNameServiceImpl subjectAlternativeNameService;

    @Mock
    private SubjectAlternativeNameRepository sanRepository;

    @Test
    void findAllByCertificate() {
        // given
        Long certificateId = 1L;
        List<SubjectAlternativeName> sans = List.of(
                new SubjectAlternativeName(1L, "test1.com", Certificate.builder().id(1L).build(), false),
                new SubjectAlternativeName(1L, "dev.test1.com", Certificate.builder().id(1L).build(), false)
        );

        when(sanRepository.findAllByCertificateId(any(Long.class))).thenReturn(sans);

        // when
        subjectAlternativeNameService.findAllByCertificate(certificateId);

        // then
        assertThat(sans).isNotNull();
        assertThat(sans.size()).isEqualTo(2);
        verify(sanRepository, times(1)).findAllByCertificateId(certificateId);
    }
}