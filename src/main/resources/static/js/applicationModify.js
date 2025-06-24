document.addEventListener('DOMContentLoaded', function () {
    const currentPageNumber = document.getElementById('currentPageNumber').value;
    const form = document.getElementById('editForm');
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    form.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const number = document.getElementById('number').value;
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const phoneNumber = document.getElementById('phoneNumber').value;
        
        if (!name || !email || !phoneNumber) {
            alert('필수 항목을 모두 입력해주세요.');
            return;
        }
        
       
        const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailPattern.test(email)) {
            alert('유효한 이메일 주소를 입력해주세요.');
            return;
        }
        
        
        const formData = new FormData(form);
        const data = {};
        formData.forEach((value, key) => {
            data[key] = value;
        });
        
        fetch(`/admin/applicationModify/${number}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('네트워크 응답이 올바르지 않습니다.');
            }
            return response.json();
        })
        .then(data => {
            alert('지원서가 성공적으로 수정되었습니다.');
            window.location.href = `/admin/applicationDetail/${data.number}?page=${currentPageNumber}`;
        })
        .catch(error => {
            console.error('오류:', error);
            alert('지원서 수정 중 오류가 발생했습니다.');
        });
    });

    const cancelBtn = document.getElementById('cancleBtn');
    cancelBtn.addEventListener('click', function(event) {
        if (!confirm('변경 사항을 취소하시겠습니까?')) {
            event.preventDefault();
        }
    });
    const deleteBtn = document.getElementById('deleteBtn');
    deleteBtn.addEventListener('click', function(event) {
        if (confirm('정말로 지원서를 삭제하시겠습니까?')) {
            if(confirm('삭제된 지원서는 복구할 수 없습니다. 계속하시겠습니까?')) {
                const number = document.getElementById('number').value;
            fetch(`/admin/applicationModify/${number}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                }
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('네트워크 응답이 올바르지 않습니다.');
                }
                return response;
            })
            .then(data => {
                alert('지원서가 성공적으로 삭제되었습니다.');
                window.location.href = `/admin/list`;
            })
            .catch(error => {
                console.error('오류:', error);
                alert('지원서 삭제 중 오류가 발생했습니다.');
            });
            } else {
                return;
            }
        }
    });
});