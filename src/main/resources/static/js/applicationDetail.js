document.addEventListener('DOMContentLoaded', function () {
                
    const consultationNumber = document.getElementById("consultationNumber").value;
  
    let modal = document.getElementById("consultationModal");
    let scheduleBtn = document.getElementById("scheduleBtn");
    let closeBtn = modal.querySelector(".close-button");
    let cancelBtn = document.getElementById("cancelConsultationBtn");
    let confirmBtn = document.getElementById("confirmConsultationBtn");
    let deleteBtn = document.getElementById("deleteBtn");
    let dateInput = document.getElementById("consultationDate");
    let timeInput = document.getElementById("consultationTime");
    let applicationName = document.getElementById("name");
    let displayText = null;
    const token = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const header = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    function openModal() {
        displayText = document
            .getElementById('consultationDisplay')
            .textContent
            .trim();
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        const todayFormatted = `${year}-${month}-${day}`;
        dateInput.setAttribute('min', todayFormatted);
        if (displayText !== '미정') {
            const [date, time] = displayText.split(' ');
            dateInput.value = date;
            timeInput.value = time;
        } else {
            dateInput.value = '';
            timeInput.value = '';
        }
        modal.style.display = "flex";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    if (scheduleBtn) {
        scheduleBtn.onclick = openModal;
    }

    if (closeBtn) {
        closeBtn.onclick = closeModal;
    }

    if (cancelBtn) {
        cancelBtn.onclick = closeModal;
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            closeModal();
        }
    }

    if (confirmBtn) {
        confirmBtn.onclick = function () {
            let selectedDate = dateInput.value;
            let selectedTime = timeInput.value;
            let appName = applicationName.textContent;

            if (!selectedDate || !selectedTime) {
                alert("상담 날짜와 시간을 모두 선택해주세요.");
                return;
            }

            const newDateTime = `${selectedDate} ${selectedTime}`;
            if (displayText === newDateTime) {
                alert("변경된 상담 날짜와 시간이 이전과 같습니다.");
                return;
            }

            let consultationDateTimeString = selectedDate + "T" + selectedTime;

            console.log("보낼 데이터:", {
                name: appName,
                consultationDateTime: consultationDateTimeString
            });

            fetch('/admin/consultation', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    [header]: token
                },
                body: JSON.stringify({
                    number: document
                        .getElementById('number')
                        .value,
                    name: appName,
                    consultationDateTime: consultationDateTimeString
                })
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('네트워크 응답이 올바르지 않습니다.');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('성공:', data);
                    if (consultationNumber) 
                        alert(
                            `상담이 성공적으로 변경되었습니다.\n이름: ${appName}\n날짜: ${selectedDate}\n시간: ${selectedTime}`
                        );
                    else 
                        alert(
                            `상담이 성공적으로 예약되었습니다.\n이름: ${appName}\n날짜: ${selectedDate}\n시간: ${selectedTime}`
                        );
                    window
                        .location
                        .reload();
                    const displayElement = document.getElementById('consultationDisplay');
                    if (displayElement && data.consultationDateTimeStr) {
                        displayElement.textContent = data.consultationDateTimeStr;
                    } else {
                        displayElement.textContent = '미정';
                    }
                    const scheduleButton = document.getElementById('scheduleBtn');
                    if (scheduleButton) {
                        scheduleButton.textContent = '상담 시간 수정';
                    }
                })
                .catch((error) => {
                    console.error('오류:', error);
                    alert('상담 예약 중 오류가 발생했습니다.');
                });
        }
    }

    if (deleteBtn) {
        deleteBtn.onclick = () => {
            if (consultationNumber) {
                if (confirm("정말로 상담 예약을 취소하시겠습니까?")) {
                    
                    fetch(`/admin/consultation/${encodeURIComponent(consultationNumber)}`, {
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
                        .then(() => {
                            alert("예약이 취소되었습니다.");
                            window
                                .location
                                .reload();
                        })
                        .catch((error) => {
                            alert('예약 취소 중 오류가 발생했습니다.');
                        });
                }
            }
        }
    }
});