package com.sejong.newsletterservice.application.email;

import com.sejong.newsletterservice.core.sentlog.SentLog;
import com.sejong.newsletterservice.core.sentlog.SentLogRepository;
import com.sejong.newsletterservice.fixture.SentLogFixture;
import com.sejong.newsletterservice.infrastructure.redis.SentLogCacheService;
import com.sejong.newsletterservice.infrastructure.redis.dto.SentLogCacheDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class SentLogFlusherTest {

    public SentLogFlusher sentLogFlusher;
    @Mock
    private SentLogRepository sentLogRepository;
    @Mock
    private SentLogCacheService sentLogCacheService;

    @BeforeEach
    void setUp() {
        sentLogFlusher = new SentLogFlusher(sentLogRepository, sentLogCacheService);
    }


    @Test
    void 메모리에_저장된_로그를_데이터베이스_저장한다() {
        // given
        SentLogCacheDto dto1 = SentLogFixture.캐시된_로그_생성("user1@example.com",1L);
        SentLogCacheDto dto2 = SentLogFixture.캐시된_로그_생성("user2@example.com",2L);
        List<SentLogCacheDto> mockCacheDtos = List.of(dto1, dto2);
        when(sentLogCacheService.getAllSuccessLogsAndDelete()).thenReturn(mockCacheDtos);

        // when
        sentLogFlusher.flushSuccessLogsToDatabase();

        // then

        ArgumentCaptor<List<SentLog>> captor = ArgumentCaptor.forClass(List.class);
        verify(sentLogRepository).saveAll(captor.capture());

        List<SentLog> savedLogs = captor.getValue();
        assertThat(savedLogs).hasSize(2);
        assertThat(savedLogs.get(0).getEmail()).isEqualTo("user1@example.com");
        assertThat(savedLogs.get(1).getCsKnowledgeId()).isEqualTo(2L);
    }
}
