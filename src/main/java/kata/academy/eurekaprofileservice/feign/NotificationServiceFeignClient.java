package kata.academy.eurekaprofileservice.feign;

import kata.academy.eurekaprofileservice.feign.fallback.NotificationServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "eureka-notification-service", fallbackFactory = NotificationServiceFallbackFactory.class)
public interface NotificationServiceFeignClient {

    @PostMapping("/api/internal/v1/notifications/map")
    void addNotificationsMap(@RequestBody Map<Long, String> notificationMap);
}
