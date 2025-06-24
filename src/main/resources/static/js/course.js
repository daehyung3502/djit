
const courseDetails = /*[[${courseDetails}]]*/ [];
const selectedCourseId = /*[[${selectedCourseId}]]*/ null;

console.log('courseDetails:', courseDetails);
console.log('selectedCourseId:', selectedCourseId);

document.addEventListener('DOMContentLoaded', function() {
    const tabs = document.querySelectorAll('.subject-tab');
    const contents = document.querySelectorAll('.subject-info');
    
    tabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const targetId = this.getAttribute('data-subject');
            
            // 모든 탭에서 active 클래스 제거
            tabs.forEach(t => t.classList.remove('active'));
            contents.forEach(c => c.classList.remove('active'));
            
            // 클릭된 탭과 해당 콘텐츠에 active 클래스 추가
            this.classList.add('active');
            document.getElementById(targetId).classList.add('active');
            
            // URL 업데이트
            const courseId = targetId.replace('course', '');
            const newUrl = window.location.pathname + '?courseId=' + courseId;
            history.replaceState(null, '', newUrl);
        });
    });
});
