function showEditForm(id) {
    const editForm = document.getElementById('editForm' + id);
    if (editForm.style.display === 'block') {
        editForm.style.display = 'none';
    } else {
        editForm.style.display = 'block';
    }
}

function hideEditForm(id) {
    document.getElementById('editForm' + id).style.display = 'none';
}

// 이미지 미리보기
function previewImage(input, id) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('imagePreview' + id).src = e.target.result;
        };
        reader.readAsDataURL(input.files[0]);
    }
}

// 폼 제출 처리
document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('[id^="portfolioEditForm"]').forEach(form => {
        form.addEventListener('submit', function(e) {
            e.preventDefault();
            
            const id = this.id.replace('portfolioEditForm', '');
            const formData = new FormData(this);
            
            const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
            const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
            
            fetch(`/admin/portfolio/${id}`, {
                method: 'POST',
                headers: {
                    [header]: token
                },
                body: formData
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('포트폴리오가 성공적으로 수정되었습니다!');
                    location.reload();
                } else {
                    alert('수정 중 오류가 발생했습니다: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('수정 중 오류가 발생했습니다.');
            });
        });
    });
});