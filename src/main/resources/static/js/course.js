$(document).ready(function () {
       
             
       
               $(".toggle-menu").click(function () {
                   $(this).toggleClass("active");
                   let $nav = $("nav");
       
                   if ($nav.is(":hidden")) {
                       $nav
                           .stop()
                           .slideDown(400);
                       $("body").css("overflow", "hidden");
                   } else {
                       $nav
                           .stop()
                           .slideUp(400, function () {
                               $(this).css('display', 'none');
                               $("body").css("overflow", "auto");
                           });
                   }
                   $("#toggle-1").prop("checked", !$("#toggle-1").prop("checked"));
               });
       
               if ($(window).width() <= 1070 && $("nav").is(":visible")) {
                   $("body").css("overflow", "hidden");
               }
       
       
       
               // URL에서 파라미터 가져오기 (예: ?tab=bigdata)
               function getParameterByName(name, url = window.location.href) {
                   name = name.replace(/[\[\]]/g, '\\$&');
                   var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
                       results = regex.exec(url);
                   if (!results) return null;
                   if (!results[2]) return '';
                   return decodeURIComponent(results[2].replace(/\+/g, ' '));
               }
       
               // 활성화할 탭 가져오기
               let activeTab = getParameterByName('tab');
       
               // 탭과 내용 요소 선택
               const subjectTabs = document.querySelectorAll('.subject-tab');
               const subjectInfos = document.querySelectorAll('.subject-info');
       
               // 탭 클릭 이벤트 핸들러 (기존 로직 유지)
               subjectTabs.forEach(tab => {
                   tab.addEventListener('click', () => {
                       subjectTabs.forEach(t => t.classList.remove('active'));
                       subjectInfos.forEach(s => s.classList.remove('active'));
                       tab.classList.add('active');
                       const subjectId = tab.dataset.subject;
                       document.getElementById(subjectId).classList.add('active');
                   });
               });
       
       
               // URL 파라미터에 따라 탭 활성화, 파라미터가 없으면 첫번째 탭 활성화.
               if (activeTab) {
                   subjectTabs.forEach(tab => {
                       if (tab.dataset.subject === activeTab) {
                           tab.classList.add('active');
                           document.getElementById(activeTab).classList.add('active');
                       } else {
                           tab.classList.remove('active');
                           if (document.getElementById(tab.dataset.subject)) { // 해당 id를 가진 요소가 존재하는지 확인
                             document.getElementById(tab.dataset.subject).classList.remove('active');
                           }
                       }
                   });
               } else {
                   // 파라미터가 없을경우, 첫번째 탭 활성화
                   if(subjectTabs.length > 0) { //0보다 클때만 실행
                       subjectTabs[0].classList.add('active');
                       subjectInfos[0].classList.add('active');
                   }
       
               }
           });