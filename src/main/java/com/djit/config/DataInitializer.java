package com.djit.config;

import com.djit.entity.Admin;
import com.djit.entity.Course;
import com.djit.entity.Portfolio;
import com.djit.repository.application.AdminRepository;
import com.djit.repository.application.CourseRepository;
import com.djit.repository.application.PortfolioRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Value("${app.admin.username}")
    private String adminUsername;
    
    @Value("${app.admin.password}")
    private String adminPassword;

    

    @Bean
    public CommandLineRunner initData(CourseRepository courseRepository, PortfolioRepository portfolioRepository ,  AdminRepository adminRepository,
    PasswordEncoder passwordEncoder) {
        return args -> {

            if (adminRepository.findByUsername(adminUsername).isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole("ADMIN");
                adminRepository.save(admin);
                System.out.println("관리자 계정 생성 완료: " + adminUsername);
            }

            if (courseRepository.count() == 0) {
                Course courseA = new Course();
                Course courseB = new Course();
                Course courseC = new Course();
                
         
                courseA.setCourseName("빅데이터융합 스프링부트 (자바&파이썬) 응용 SW 개발자");
                courseA.setCourseTagline("스프링 부트로 강력한 풀스텍 웹을 만들어가는 학습의 즐거움을 경험하세요!");
                courseA.setCourseDescription("자바와 파이썬 프로그래밍 언어를 기반으로 응용 프로그램 개발 요구사항을 분석하여 화면설계 및 DBMS 설치 및 운영 조작하고 데이터베이스 프로시저를 작성하고 빅데이터 수집 및 처리 기술을 활용하여 데이터를 분석하고 Vue.js와 React.js를 활용하고 스프링 부트을 통한 풀스텍 웹을 구현 할 수 있있도록 학습한다.");
                
                courseB.setCourseName("클라우드기반 풀스텍 개발과 빅데이터 분석 서비스 구현을 위한 전문가 양성과정");
                courseB.setCourseTagline("스프링 클라우드를 기반으로 하는 혁신적인 응용 소프트웨어 개발!");
                courseB.setCourseDescription("웹 개발 기초 기술과 Spring, Spring Boot 프레임워크를 익혀 고급 웹 애플리케이션을 개발하며, AWS, Azure, Google Cloud 등 주요 클라우드 플랫폼의 이해와 인프라 관리 능력을 기릅니다. 클라우드 기반 데이터베이스 설계 및 운영, Numpy, Pandas, Matplotlib, Seaborn을 활용한 데이터 분석 및 시각화 능력을 배양하고, 클라우드에서 머신러닝과 딥러닝 모델 개발 능력을 키웁니다. 마지막으로, 실제 문제 해결을 위한 실무 프로젝트를 통해 종합적인 실무 능력을 강화하고, 최신 기술과 경험을 바탕으로 취업 경쟁력을 높일 수 있다.");
               
                courseC.setCourseName("풀 스텍 SW 엔지니어링&디지털 트랜스포메이션 과정");
                courseC.setCourseTagline("미래를 위한 기술과 창의력을 키우는 여정에 함께하세요!");
                courseC.setCourseDescription("소프트웨어 산업은 새로운 기술 동향은 여러분야에서 클라우드 네이티브 및 서버리스 아키텍처, 인공지능 및 기계학습, 컨테이너 기술과 쿠버네티스의 확산, 사이버 보안 강화, 개발자 경험의 개선, 언어 및 프레임워크의 다양성, 저 코드 및 노 코드 트렌드, 그리고 분산 원장 기술의 도입, 소프트웨어 개발 방법론 등이 주요 동향으로 나타나고 있으며 기업의 요구사항도 다양하게 요구되고 있습니다. 현재 시급하게 요구되는 기술들을 우선하여 교육과정을 설계하고 우수한 인력을 양성하고자 한다.");

                courseA.setStartDate(LocalDate.of(2025, 5, 12));
                courseA.setDuration("6개월 900시간(1일 8교시)");

                courseB.setStartDate(LocalDate.of(2025, 3, 18));
                courseB.setDuration("6개월 960시간(1일 8교시)");

                courseC.setStartDate(LocalDate.of(2025, 3, 18));
                courseC.setDuration("6개월 960시간(1일 8교시)");
   
                
              
                courseA.setSubjects(Arrays.asList(
                    "JAVA", 
                    "Python", 
                    "Web language", 
                    "JSP&Servlet", 
                    "Spring Framework", 
                    "Spring Boot", 
                    "파이썬 활용 크롤링", 
                    "빅데이터 수집", 
                    "빅데이터 처리", 
                    "빅데이터 분석", 
                    "빅데이터 시각화", 
                    "실무프로젝트"
                ));

                courseB.setSubjects(Arrays.asList(
                    "JAVA", 
                    "Python", 
                    "Web Front", 
                    "Oracle DB", 
                    "JSP&Servlet", 
                    "Spring Framework", 
                    "Spring Boot 빅데이터 분석도구 활용", 
                    "머신러닝+딥러닝", 
                    "AWS 클라우드 웹서비스",
                    "실무프로젝트"
                ));

                courseC.setSubjects(Arrays.asList(
                    "JAVA", 
                    "Python", 
                    "Web Front", 
                    "JSP&Servlet", 
                    "Spring Framework", 
                    "SpringBoot", 
                    "파이썬 활용 크롤링", 
                    "빅데이터 분석 및 시각화", 
                    "Oracle DB",
                    "클라우드 컴퓨팅",
                    "머신러닝&딥러닝",
                    "애자일&데브옵스",
                    "실무프로젝트"
                ));
                
                
           
                courseA.setRoadmapMonth1(Arrays.asList("Java", "Web Front", "데이터베이스"));
                courseA.setRoadmapMonth2(Arrays.asList("Spring", "Linux Server", "Vue.js"));
                courseA.setRoadmapMonth3(Arrays.asList("Spring", "Python", "Web Crawling"));
                courseA.setRoadmapMonth4(Arrays.asList("React.js", "빅데이터 처리, 분석"));
                courseA.setRoadmapMonth5(Arrays.asList("SpringBoot", "빅데이터 시각화"));
                courseA.setRoadmapMonth6(Arrays.asList("실무 프로젝트"));

                courseB.setRoadmapMonth1(Arrays.asList("Java", "Web Front"));
                courseB.setRoadmapMonth2(Arrays.asList("Oracle DB", "JSP & Servlet"));
                courseB.setRoadmapMonth3(Arrays.asList("Spring Framework"));
                courseB.setRoadmapMonth4(Arrays.asList("머신러닝 & 딥러닝", "Spring Boot"));
                courseB.setRoadmapMonth5(Arrays.asList("클라우드 서비스", "실무 프로젝트"));
                courseB.setRoadmapMonth6(Arrays.asList("실무 프로젝트"));

                courseC.setRoadmapMonth1(Arrays.asList("Java", "JSP&Servlet", "데이터베이스"));
                courseC.setRoadmapMonth2(Arrays.asList("Spring Framework", "MSA 컴포넌트"));
                courseC.setRoadmapMonth3(Arrays.asList("Python", "Web Scraping"));
                courseC.setRoadmapMonth4(Arrays.asList("SpringBoot", "React", "빅데이터"));
                courseC.setRoadmapMonth5(Arrays.asList("리눅스", "하둡"));
                courseC.setRoadmapMonth6(Arrays.asList("실무 프로젝트"));
                
              
                courseA.setEducationalGoals(Arrays.asList(
                    "자바와 파이썬을 기반으로 빅데이터를 수집 및 처리, 분석하여 Vue.js와 React.js를 활용하고 스프링 부트를 통한 풀스텍 웹 개발할 수 있는 개발자를 양성하는데 목표로 한다.",
                    "풀스텍 개발자 양성을 위한 프로그래밍 언어, 프레임워크, 웹 표준 언어를 통한 구현과 서버관리를 할 수 있다.",
                    "응용 프로그램 개발 요구사항을 분석하고 화면설계와 동적 웹 구현을 할 수 있다.",
                    "서버프로그램 구현, 통합구현 및 애플리케이션 배포와 패키징을 할 수 있다.",
                    "DBMS 설치 및 운영과 조작하고 데이터베이스 프로시저를 작성할 수 있다.",
                    "빅데이터 수집 및 처리 기술을 활용하여 데이터를 분석하고 웹에 적용할 수 있다.",
                    "학습내용을 기반으로 실무에서 요구되는 프로젝트 수행 능력을 함양하기 위한 프로젝트 요구사항 분석 및 설계, 프로젝트 구현, 테스트 및 배포 과정을 팀 프로젝트로 수행한다."
                ));

                courseB.setEducationalGoals(Arrays.asList(
                    "자바와 파이썬의 기초와 심화 개념을 학습하여 클라우드 환경에서의 다양한 프로젝트에 활용할 수 있는 능력을 배양한다.",
                    "웹 개발의 기초 기술을 익히고, Spring과 Spring Boot 프레임워크를 활용하여 클라우드 환경에 적합한 고급 웹 애플리케이션 개발 능력을 향상시킨다.",
                    "AWS, Azure, Google Cloud 등 주요 클라우드 플랫폼의 개념과 기술을 이해하고, 클라우드 인프라를 구축하고 관리하는 능력을 기른다.",
                    "클라우드 기반 데이터베이스의 설계, 구축, 운영에 대한 실무적인 능력을 배양하여 다양한 데이터 관리 요구에 대응할 수 있다.",
                    "Numpy, Pandas, Matplotlib, Seaborn 등 다양한 빅데이터 분석 도구를 활용하여 클라우드 환경에서 데이터를 수집, 분석, 시각화하는 능력을 함양한다.",
                    "클라우드 기반 머신러닝과 딥러닝의 이론과 실습을 통해 AI 기반의 데이터 분석 및 예측 모델을 개발할 수 있는 능력을 기른다.",
                    "클라우드 환경에서 실제 현업의 문제를 해결하는 실무 프로젝트를 수행함으로써 학습한 기술들을 종합적으로 활용하고, 실무 능력을 강화한다.",
                    "최신 클라우드 기술과 실무 프로젝트 경험을 바탕으로 취업 시장에서의 경쟁력을 강화하고, 기업이 요구하는 클라우드 기반의 실무형 인재로 성장한다."
                ));

                courseC.setEducationalGoals(Arrays.asList(
                    "다양한 프로그래밍 언어를 사용하여 기본적인 프로그래밍 개념을 이해하고 간단한 알고리즘과 데이터 구조를 구현할 수 있다.",
                    "모던 프론트엔드 및 백엔드 프레임워크를 사용하여 웹 어플리케이션을 설계, 구축 및 유지보수할 수 있다.",
                    "대표적인 클라우드 플랫폼을 사용하여 어플리케이션을 배포하고 관리하는 능력을 개발할 수 있다.",
                    "효과적인 데이터베이스 설계와 운영을 수행할 수 있다.",
                    "주요 빅데이터 분석 도구를 사용하여 데이터를 처리하고 분석할 수 있다.",
                    "인공지능 및 기계학습의 기본 개념을 이해하고 간단한 모델을 만들 수 있다.",
                    "주요 머신러닝 라이브러리를 사용하여 모델을 개발하고 평가할 수 있다.",
                    "효과적인 테스트 전략을 수립하고 애플리케이션을 효과적으로 관리할 수 있다.",
                    "애자일과 데브옵스을 통해 기업이나 조직이 소프트웨어 개발 및 배포 과정에서 민첩성과 효율성을 향상시키고, 빠르게 변화하는 비즈니스 환경에 대응할 수 있도록 할 수 있다.",
                    "팀 프로젝트를 통해 실제 업무 환경에서의 문제 해결과 협업 능력을 개발할 수 있다."
                ));
                
              
                courseRepository.save(courseA);
                courseRepository.save(courseB);
                courseRepository.save(courseC);
                
                
            }
            if (portfolioRepository.count() == 0) {
            Portfolio portfolio1 = new Portfolio();
            portfolio1.setTitle("서강대학교 LINC사업단 홈페이지 구축");
            portfolio1.setGithubUrl("https://github.com/SilverCastle123/project");
            portfolio1.setImageUrl("/images/github1.png");
            portfolio1.setCreatedAt(LocalDateTime.now());
            portfolio1.setUpdatedAt(LocalDateTime.now());
            
            Portfolio portfolio2 = new Portfolio();
            portfolio2.setTitle("광주 남구 통합도서관 홈페이지 개편");
            portfolio2.setGithubUrl("https://github.com/ParkCheolSun/Library_project");
            portfolio2.setImageUrl("/images/github2.png");
            portfolio2.setCreatedAt(LocalDateTime.now());
            portfolio2.setUpdatedAt(LocalDateTime.now());
            
            Portfolio portfolio3 = new Portfolio();
            portfolio3.setTitle("청년 맞춤형 온라인 취업지원사업");
            portfolio3.setGithubUrl("https://github.com/EODOHA/youth");
            portfolio3.setImageUrl("/images/github3.png");
            portfolio3.setCreatedAt(LocalDateTime.now());
            portfolio3.setUpdatedAt(LocalDateTime.now());
            
            Portfolio portfolio4 = new Portfolio();
            portfolio4.setTitle("축제 통합 홍보 페이지 프로젝트");
            portfolio4.setGithubUrl("https://github.com/gicheol1/Project_Git");
            portfolio4.setImageUrl("/images/github4.png");
            portfolio4.setCreatedAt(LocalDateTime.now());
            portfolio4.setUpdatedAt(LocalDateTime.now());
            
            Portfolio portfolio5 = new Portfolio();
            portfolio5.setTitle("부산 오페라하우스 콘서트홀 통합운영시스템");
            portfolio5.setGithubUrl("https://github.com/Younglan/cantata");
            portfolio5.setImageUrl("/images/github5.png");
            portfolio5.setCreatedAt(LocalDateTime.now());
            portfolio5.setUpdatedAt(LocalDateTime.now());
            
            portfolioRepository.save(portfolio1);
            portfolioRepository.save(portfolio2);
            portfolioRepository.save(portfolio3);
            portfolioRepository.save(portfolio4);
            portfolioRepository.save(portfolio5);
        }
    
        };
    }
}