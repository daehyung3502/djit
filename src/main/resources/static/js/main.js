$(window).scroll(function () {
    let scrollPosition = $(window).scrollTop();
    
    // ===== highlight, award, itButtons 섹션 애니메이션 =====
    // highlight 섹션 애니메이션
    let highlightPosition = $(".highlight").offset().top;
    let triggerPointHighlight = highlightPosition - $(window).height() + ($(".highlight").height() / 2);
    let hidePointHighlight = highlightPosition + $(".highlight").height();

    if (scrollPosition > triggerPointHighlight && scrollPosition < hidePointHighlight) {
        $(".highlight").css(
            {'transform': 'translateY(0)', 'opacity': '1', 'transition': 'transform 1s ease, opacity 1s ease'}
        );
    } else {
        $(".highlight").css(
            {'transform': 'translateY(50px)', 'opacity': '0', 'transition': 'transform 1s ease, opacity 1s ease'}
        );
    }

    // award 섹션 애니메이션
    let awardPosition = $(".award").offset().top;
    let triggerPointAward = awardPosition - $(window).height() + ($(".award").height() / 2);
    let hidePointAward = awardPosition + $(".award").height();

    if (scrollPosition > triggerPointAward && scrollPosition < hidePointAward) {
        $(".award").css(
            {'transform': 'translateY(0)', 'opacity': '1', 'transition': 'transform 1s ease, opacity 1s ease'}
        );
    } else {
        $(".award").css(
            {'transform': 'translateY(50px)', 'opacity': '0', 'transition': 'transform 1s ease, opacity 1s ease'}
        );
    }

    // itButtons 섹션 애니메이션
    let itButtonsPosition = $(".itButtons").offset().top;
    let triggerPointItButtons = itButtonsPosition - $(window).height() + ($(".itButtons").height() / 2);
    let hidePointItButtons = itButtonsPosition + $(".itButtons").height();

    if (scrollPosition > triggerPointItButtons && scrollPosition < hidePointItButtons) {
        $(".itButtons").css(
            {'transform': 'translateY(0)', 'opacity': '1', 'transition': 'transform 1s ease, opacity 1s ease'}
        );
    } else {
        $(".itButtons").css(
            {'transform': 'translateY(50px)', 'opacity': '0', 'transition': 'transform 1s ease, opacity 1s ease'}
        );
    }

    // ===== itIntroTop 섹션 애니메이션 =====
    let introTopPosition = $(".itIntroTop").offset().top;
    let triggerPointTop = introTopPosition - $(window).height() + ($(".itIntroTop").height() / 2);
    let hidePointTop = introTopPosition + $(".itIntroTop").height();

    if (scrollPosition > triggerPointTop && scrollPosition < hidePointTop) {
        $(".itIntroTopRightText1").addClass("active");
        $(".itIntroTopRightText2").addClass("active");
    } else {
        $(".itIntroTopRightText1").removeClass("active");
        $(".itIntroTopRightText2").removeClass("active");
    }

    // ===== itIntroBottom 섹션 애니메이션 =====
    let introBottomPosition = $(".itIntroBottom").offset().top;
    let triggerPointBottom = introBottomPosition - $(window).height() + ($(".itIntroBottom").height() / 2);
    let hidePointBottom = introBottomPosition + $(".itIntroBottom").height();

    if (scrollPosition > triggerPointBottom && scrollPosition < hidePointBottom) {
        $(".itIntroBottomText").each(function (index) {
            $(this).addClass("active");
        });
    } else {
        $(".itIntroBottomText").removeClass("active");
    }

    // ===== 카드 그룹 애니메이션 =====
    let cardGroupPosition = $(".card-group").offset().top;
    let triggerPointCardGroup;

    // 반응형 트리거 포인트 조정
    if ($(window).width() <= 768) {
        triggerPointCardGroup = cardGroupPosition - $(window).height() + 200;
    } else {
        triggerPointCardGroup = cardGroupPosition - $(window).height() + ($(".card-group").height() / 4);
    }

    let hidePointCardGroup = cardGroupPosition + $(".card-group").height();

    if (scrollPosition > triggerPointCardGroup && scrollPosition < hidePointCardGroup) {
        $(".card-group .card").each(function (index) {
            $(this).css({
                'transform': 'translateY(0)',
                'opacity': '1',
                'transition': `transform 0.6s ease ${index * 0.2}s, opacity 0.6s ease ${index * 0.2}s`
            });
        });
    } else {
        $(".card-group .card").css(
            {'transform': 'translateY(50px)', 'opacity': '0', 'transition': 'transform 0.6s ease, opacity 0.6s ease'}
        );
    }
});

// ==================== 카드 호버 효과 ====================
$(document).ready(function () {
    // 카드 호버 효과 설정
    $(".card-group .card").hover(function () {
        $(this).css({'transition': 'transform 0.3s ease, box-shadow 0.3s ease'});
    }, function () {});
});

// ==================== 스크롤 버튼 클릭 처리 ====================
$(document).ready(function () {
    // 스크롤 버튼 클릭 시 itIntroSection으로 부드럽게 스크롤
    $(".scroll-button").click(function () {
        $("html, body").animate({
            scrollTop: $("#itIntroSection")
                .offset()
                .top - $(".navMenu").outerHeight() + 1
        }, 800);
    });
});

// ==================== 코인 플립 애니메이션 ====================
$(document).ready(function () {
    // 코인 플립 애니메이션 함수
    function animateCoinFlip() {
        let rotation = 0;

        function flip() {
            rotation = (rotation === 0) ? 360 : 0;
            $(".itIntroTopLeftMark").css({'perspective': '800px'});

            $(".itIntroTopLeftMark img").css(
                {'transform': `rotateY(${rotation}deg)`, 'transition': 'transform 1.5s ease-in-out'}
            );

            // 6초마다 플립 애니메이션 반복
            setTimeout(flip, 6000);
        }
        flip();
    }

    // 코인 플립 애니메이션 시작
    animateCoinFlip();
});

// ==================== Swiper 슬라이더 초기화 ====================
// Swiper 슬라이더 설정
let myswiper = new Swiper(".mySwiper", {
    effect: "coverflow", // 커버플로우 효과 적용
    centeredSlides: true, // 가운데 슬라이드 중심으로 정렬
    slidesPerView: 'auto', // 자동 슬라이드 개수 조정

    // 커버플로우 효과 세부 설정
    coverflowEffect: {
        rotate: 0, // 회전 각도
        stretch: 0, // 슬라이드 간 간격
        depth: 100, // 깊이감
        modifier: 1.2, // 효과 강도
        slideShadows: true // 슬라이드 그림자 표시
    },
    
    // 페이지네이션 설정
    pagination: {
        el: ".swiper-pagination", // 페이지네이션 요소
        clickable: true // 클릭 가능
    },
    
    // 자동 재생 설정
    autoplay: {
        delay: 2500, // 슬라이드 전환 시간 (2.5초)
        disableOnInteraction: false // 사용자 상호작용 후에도 자동 재생 유지
    },
    
    loop: true // 무한 루프 활성화
});

// ==================== 탭 링크 이동 처리 ====================
$(document).ready(function () {
    // 상세 링크 클릭 처리 (djCourse.html의 data-tab 속성 활용)
    $('.detail-link').click(function(e) {
        e.preventDefault(); // 기본 동작(a 태그의 페이지 이동) 막기
        let targetTab = $(this).data('tab'); // 클릭된 요소의 data-tab 속성 값 (bigdata, cloud, sw) 가져오기
        let targetUrl = 'course.html?tab=' + targetTab; // 이동할 URL 생성 (djitCourse.html?tab=bigdata)
        window.location.href = targetUrl; // 페이지 이동
    });
});