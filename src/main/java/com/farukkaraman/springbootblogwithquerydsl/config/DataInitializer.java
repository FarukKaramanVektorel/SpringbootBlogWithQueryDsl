package com.farukkaraman.springbootblogwithquerydsl.config;

import com.farukkaraman.springbootblogwithquerydsl.entity.AuthProvider;
import com.farukkaraman.springbootblogwithquerydsl.entity.Post;
import com.farukkaraman.springbootblogwithquerydsl.entity.Role;
import com.farukkaraman.springbootblogwithquerydsl.entity.User;
import com.farukkaraman.springbootblogwithquerydsl.repository.PostRepository;
import com.farukkaraman.springbootblogwithquerydsl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin1 = User.builder()
                    .email("faruk@test.com")
                    .name("Faruk Karaman")
                    .password("1234")
                    .role(Role.ADMIN)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User admin2 = User.builder()
                    .email("ayse.admin@test.com")
                    .name("Ayşe Yılmaz")
                    .password("pass123")
                    .role(Role.ADMIN)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User admin3 = User.builder()
                    .email("mehmet.sistem@test.com")
                    .name("Mehmet Demir")
                    .password("root456")
                    .role(Role.ADMIN)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user1 = User.builder()
                    .email("ahmet.yazilim@test.com")
                    .name("Ahmet Kaya")
                    .password("ahmet123")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user2 = User.builder()
                    .email("selin.tech@test.com")
                    .name("Selin Deniz")
                    .password("selin789")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user3 = User.builder()
                    .email("can.dev@test.com")
                    .name("Can Özkan")
                    .password("can000")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user4 = User.builder()
                    .email("elif.blog@test.com")
                    .name("Elif Yıldız")
                    .password("elif555")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user5 = User.builder()
                    .email("murat.code@test.com")
                    .name("Murat Aydın")
                    .password("murat22")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user6 = User.builder()
                    .email("zeynep.data@test.com")
                    .name("Zeynep Ak")
                    .password("zeynep99")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            User user7 = User.builder()
                    .email("burak.cloud@test.com")
                    .name("Burak Güneş")
                    .password("burak11")
                    .role(Role.USER)
                    .provider(AuthProvider.LOCAL)
                    .build();

            userRepository.saveAll(List.of(
                    admin1, admin2, admin3,
                    user1, user2, user3, user4, user5, user6, user7
            ));


            Post p1 = Post.builder()
                    .title("Mikroservis Mimarilerinde Resilience")
                    .content("Mikroservis sistemlerde bir servisin çökmesi tüm sistemi etkileyebilir. Bu noktada Circuit Breaker, Retry ve Rate Limiter gibi desenlerin kullanımı hayati önem taşır. Resilience4j kütüphanesi ile sisteminizi nasıl daha dayanıklı hale getirebileceğinizi bu yazıda detaylandırıyoruz.")
                    .user(admin1)
                    .published(true)
                    .build();

            Post p2 = Post.builder()
                    .title("Docker ve Konteynırlaştırma Stratejileri")
                    .content("Docker kullanarak uygulamalarınızı izole etmek, 'benim makinemde çalışıyordu' sorununu ortadan kaldırır. Multi-stage build kullanarak Docker imaj boyutlarını nasıl küçültebileceğinizi ve CI/CD süreçlerine nasıl entegre edebileceğinizi adım adım inceledik.")
                    .user(admin3)
                    .published(false)
                    .build();

            Post p3 = Post.builder()
                    .title("Kafka ile Event-Driven Design")
                    .content("Apache Kafka, yüksek trafikli sistemlerde veriyi asenkron olarak işlemek için mükemmel bir araçtır. Topic yapısı, partition mantığı ve consumer group yönetimi ile veri akışını nasıl ölçeklendirebileceğinizi, gerçek zamanlı veri işleme örnekleriyle anlatıyoruz.")
                    .user(admin2)
                    .published(true)
                    .build();

            Post p4 = Post.builder()
                    .title("PostgreSQL Indexleme Teknikleri")
                    .content("Veritabanı performansı sadece sorgu yazmakla ilgili değildir. B-Tree, GIN ve GiST index türlerinin hangi durumlarda kullanılması gerektiğini, sorgu planlayıcısının (Explain Analyze) nasıl okunması gerektiğini derinlemesine ele alıyoruz.")
                    .user(admin1)
                    .published(true)
                    .build();

            Post p5 = Post.builder()
                    .title("Clean Architecture ve Katmanlı Yapı")
                    .content("Yazılım projeleri büyüdükçe yönetilmesi zorlaşır. Robert C. Martin'in Clean Architecture prensipleriyle bağımlılıkları nasıl tersine çevirebileceğimizi (Dependency Inversion) ve iş mantığını (Business Logic) dış dünyadan nasıl soyutlayacağımızı tartışıyoruz.")
                    .user(admin1)
                    .published(true)
                    .build();

            Post p6 = Post.builder()
                    .title("React ve Server Component Yapısı")
                    .content("Modern frontend dünyasında React 18 ile gelen Server Components, sayfa yükleme hızlarını optimize etmek için yeni bir soluk getirdi. Client-side rendering ve Server-side rendering arasındaki farkları, Next.js ekosistemi üzerinden pratik örneklerle inceliyoruz.")
                    .user(admin2)
                    .published(true)
                    .build();

            Post p7 = Post.builder()
                    .title("Redis ile Dağıtık Cache Yönetimi")
                    .content("Uygulama performansını artırmak için sık kullanılan verileri bellekte tutmak gerekir. Redis kullanarak 'Cache Aside', 'Write Through' gibi stratejilerin nasıl uygulanacağını ve TTL (Time To Live) ayarlarının önemini bu makalede bulabilirsiniz.")
                    .user(admin2)
                    .published(true)
                    .build();

            Post p8 = Post.builder()
                    .title("Kubernetes Üzerinde Pod Yönetimi")
                    .content("Orkestrasyon araçları arasında lider olan Kubernetes, binlerce konteynırı yönetmemizi sağlar. Deployment, Service, ConfigMap ve Secret gibi temel objelerin nasıl konfigüre edildiğini ve kendi kendine iyileşme (Self-healing) mekanizmalarını keşfedin.")
                    .user(admin2)
                    .published(false)
                    .build();

            Post p9 = Post.builder()
                    .title("Elasticsearch ile Full-Text Search")
                    .content("Milyonlarca veri içerisinde saniyeler altında arama yapmak için QueryDSL'den daha fazlasına ihtiyaç duyduğunuzda devreye Elasticsearch girer. Inverted Index yapısı ve fuzzy search (yaklaşık arama) özelliklerini nasıl projenize dahil edebilirsiniz?")
                    .user(admin3)
                    .published(true)
                    .build();

            Post p10 = Post.builder()
                    .title("Unit Test Yazma Sanatı: JUnit 5 ve Mockito")
                    .content("Kaliteli kod, test edilebilir koddur. Mockito ile bağımlılıkları taklit ederek birim testlerin nasıl izole edileceğini, TDD (Test Driven Development) yaklaşımının geliştirme hızına ve kod kalitesine olan etkilerini gerçek bir case study üzerinden görüyoruz.")
                    .user(admin2)
                    .published(true)
                    .build();

            postRepository.saveAll(List.of(
                    p1, p2, p3, p4, p5, p6, p7, p8, p9, p10
            ));
            postRepository.save(p2);
            postRepository.save(p3);

            System.out.println("--- TEST VERİLERİ YÜKLENDİ ---");
        }
    }
}
