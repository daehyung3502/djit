$(document).ready(function () {
    // 네비게이션 메뉴 위치 설정 함수
    function setNavTop() {
        let navMenuHeight = $('.navMenu').outerHeight();
        $('nav').css('top', navMenuHeight + 'px');
    }
    
    // 페이지 로드 시 네비게이션 위치 설정
    setNavTop();
    
    // 초기 애니메이션 요소들 설정
    $(".highlight, .award, .itButtons").css(
        {'transform': 'translateY(0)', 'opacity': '1', 'transition': 'transform 1s ease, opacity 1s ease'}
    );
    
    // 창 크기 변경 시 반응형 설정
    $(window).resize(function () {
        // 네비게이션 메뉴 위치 재설정
        setNavTop();
        
        // 화면 크기가 1070px보다 클 때 네비게이션 메뉴 표시 설정
        if ($(window).width() > 1070) {
            $("nav")
                .show()
                .removeAttr("style");
            $("body").css("overflow", "auto");
            $(".toggle-menu").removeClass("active");
            $("#toggle-1").prop("checked", false);
        }
    });

    // 화면 크기가 1070px 이하이고 네비게이션이 표시되는 경우 본문 스크롤 비활성화
    if ($(window).width() <= 1070 && $("nav").is(":visible")) {
        $("body").css("overflow", "hidden");
    }

    // 카드 그룹 초기 스타일 설정
    $(".card-group .card").css({'transform': 'translateY(50px)', 'opacity': '0'});
});

// ==================== 네비게이션 메뉴 토글 처리 ====================
$(document).ready(function () {
    // 토글 메뉴 버튼 클릭 처리
    $(".toggle-menu").click(function () {
        $(this).toggleClass("active");
        let $nav = $("nav");

        // 네비게이션 메뉴가 숨겨져 있을 때
        if ($nav.is(":hidden")) {
            $nav
                .stop()
                .slideDown(400);
            $("body").css("overflow", "hidden"); // 스크롤 비활성화
        } else {
            // 네비게이션 메뉴가 표시되어 있을 때
            $nav
                .stop()
                .slideUp(400, function () {
                    $(this).css('display', 'none');
                    $("body").css("overflow", "auto"); // 스크롤 활성화
                });
        }
        // 토글 체크박스 상태 변경
        $("#toggle-1").prop("checked", !$("#toggle-1").prop("checked"));
    });
});