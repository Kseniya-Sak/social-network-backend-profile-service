package kata.academy.eurekaprofileservice.feign.fallback;

import kata.academy.eurekaprofileservice.exception.FeignRequestException;
import kata.academy.eurekaprofileservice.feign.NotificationServiceFeignClient;

import java.util.Map;

record NotificationServiceFallback(Throwable cause) implements NotificationServiceFeignClient {

    @Override
    public void addNotificationsMap(Map<Long, String> notificationMap) {
        throw new FeignRequestException("Сервис временно недоступен. Причина -> %s"
                .formatted(cause.getMessage()), cause);
    }
}
