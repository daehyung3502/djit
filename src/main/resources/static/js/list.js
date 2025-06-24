document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('tr[data-href]');
    rows.forEach(row => {
        row.addEventListener('click', function() {
            window.location.href = this.dataset.href;
        });
    });
    const socket = new SockJS('/ws');
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log('WebSocket 연결됨: ' + frame);

        stompClient.subscribe('/topic/notifications', function(notification) {
            const data = JSON.parse(notification.body);
            showNotification(data);
            showRefreshButton();
        });
    }, function(error) {
        console.error('WebSocket 연결 오류:', error);
    });

    function showNotification(data) {
        const toastContainer = document.getElementById('toastContainer');
        
        const toast = document.createElement('div');
        toast.className = 'toast';
        toast.innerHTML = `
            <div class="toast-header">
                <i class="fas fa-bell toast-icon"></i>
                <span class="toast-title">새로운 지원서</span>
                <button class="toast-refresh" onclick="window.location.reload()" title="새로고침">
                    <i class="fas fa-sync-alt"></i>
                </button>
                <button class="toast-close" onclick="closeToast(this)" title="닫기">
                    <i class="fas fa-times"></i>
                </button>
            </div>
            <div class="toast-body">
                ${data.applicantName}님의 지원서가 접수되었습니다.
            </div>
        `;
        
        toastContainer.appendChild(toast);

        setTimeout(() => {
            toast.classList.add('show');
        }, 100);

        setTimeout(() => {
            closeToast(toast.querySelector('.toast-close'));
        }, 8000);
    }

    window.closeToast = function(button) {
        const toast = button.closest('.toast');
        toast.classList.remove('show');
        setTimeout(() => {
            toast.remove();
        }, 300);
    }
});