// 전역 변수
let courseDetail = {};
let subjects = [];
let roadmap = {
    month1: [],
    month2: [],
    month3: [],
    month4: [],
    month5: [],
    month6: []
};
let goals = [];

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', function() {
    // 서버에서 전달받은 데이터가 있는지 확인하고 초기화
    if (window.serverCourseDetail) {
        setCourseData(window.serverCourseDetail);
    }
    
    // 초기화
    initializeAll();
    
    // 폼 제출 이벤트
    setupFormSubmission();
    
    // 취소 버튼 이벤트
    setupCancelButton();
});

// 서버 데이터 설정 함수
function setCourseData(data) {
    courseDetail = data;
    subjects = data.subjects || [];
    roadmap = {
        month1: data.roadmapMonth1 || [],
        month2: data.roadmapMonth2 || [],
        month3: data.roadmapMonth3 || [],
        month4: data.roadmapMonth4 || [],
        month5: data.roadmapMonth5 || [],
        month6: data.roadmapMonth6 || []
    };
    goals = data.educationalGoals || [];
}

// 모든 데이터 초기화 및 이벤트 설정
function initializeAll() {
    // 교과목 초기화
    renderSubjects();
    setupAddButtonEvents('Subject', 'subject');
    
    // 로드맵 초기화
    for (let i = 1; i <= 6; i++) {
        renderRoadmapMonth(i);
        setupAddButtonEvents(`Month${i}`, `month${i}`);
    }
    
    // 교육 목표 초기화
    renderGoals();
    setupAddButtonEvents('Goal', 'goal');
}

// 버튼 이벤트 설정 함수
function setupAddButtonEvents(itemName, itemPrefix) {
    // 추가 버튼 클릭 이벤트
    const addButton = document.getElementById(`add${itemName}Button`);
    if (addButton) {
        addButton.addEventListener('click', function() {
            const container = document.getElementById(`${itemPrefix}InputContainer`);
            const input = document.getElementById(`new${itemName}`);
            if (container && input) {
                container.style.display = 'flex';
                input.focus();
            }
        });
    }
    
    // 확인 버튼 클릭 이벤트
    const confirmButton = document.getElementById(`confirmAdd${itemName}`);
    if (confirmButton) {
        confirmButton.addEventListener('click', function() {
            addNewItem(itemName, itemPrefix);
        });
    }
    
    // 입력 필드에서 Enter 키 처리
    const input = document.getElementById(`new${itemName}`);
    if (input) {
        input.addEventListener('keyup', function(event) {
            if (event.key === 'Enter') {
                addNewItem(itemName, itemPrefix);
            }
        });
    }
}

// 새 항목 추가 함수
function addNewItem(itemName, itemPrefix) {
    const newItemInput = document.getElementById(`new${itemName}`);
    if (!newItemInput) return;
    
    const newItem = newItemInput.value.trim();
    
    if (newItem) {
        // 항목 추가
        if (itemPrefix === 'subject') {
            if (!subjects.includes(newItem)) {
                subjects.push(newItem);
                renderSubjects();
            } else {
                alert('이미 존재하는 항목입니다.');
            }
        } else if (itemPrefix === 'goal') {
            if (!goals.includes(newItem)) {
                goals.push(newItem);
                renderGoals();
            } else {
                alert('이미 존재하는 항목입니다.');
            }
        } else if (itemPrefix.startsWith('month')) {
            const month = itemPrefix.replace('month', '');
            if (!roadmap[`month${month}`].includes(newItem)) {
                roadmap[`month${month}`].push(newItem);
                renderRoadmapMonth(month);
            } else {
                alert('이미 존재하는 항목입니다.');
            }
        }
        
        newItemInput.value = '';
    }
    
    // 입력 폼 숨기기
    const container = document.getElementById(`${itemPrefix}InputContainer`);
    if (container) {
        container.style.display = 'none';
    }
}

// 항목 제거 함수
function removeItem(itemType, index, month = null) {
    if (itemType === 'subject') {
        subjects.splice(index, 1);
        renderSubjects();
    } else if (itemType === 'goal') {
        goals.splice(index, 1);
        renderGoals();
    } else if (itemType === 'roadmap') {
        roadmap[`month${month}`].splice(index, 1);
        renderRoadmapMonth(month);
    }
}

// 교과목 목록 렌더링 함수
function renderSubjects() {
    renderListItems('subject', subjects);
    const hiddenField = document.getElementById('subjectsJson');
    if (hiddenField) {
        hiddenField.value = JSON.stringify(subjects);
    }
}

// 교육 목표 렌더링 함수
function renderGoals() {
    renderListItems('goal', goals);
    const hiddenField = document.getElementById('goalsJson');
    if (hiddenField) {
        hiddenField.value = JSON.stringify(goals);
    }
}

// 로드맵 월별 렌더링 함수
function renderRoadmapMonth(month) {
    renderListItems(`month${month}`, roadmap[`month${month}`], month);
    const hiddenField = document.getElementById(`month${month}Json`);
    if (hiddenField) {
        hiddenField.value = JSON.stringify(roadmap[`month${month}`]);
    }
}

// 리스트 항목 렌더링 공통 함수
function renderListItems(itemType, items, month = null) {
    const listId = itemType === 'subject' ? 'subjectList' : 
                  itemType === 'goal' ? 'goalList' : 
                  `${itemType}List`;
    
    const list = document.getElementById(listId);
    if (!list) return;
    
    list.innerHTML = '';
    
    items.forEach((item, index) => {
        const li = document.createElement('li');
        li.className = 'list-item';
        
        const span = document.createElement('span');
        span.textContent = item;
        
        const actions = document.createElement('div');
        actions.className = 'item-actions';
        
        const removeBtn = document.createElement('button');
        removeBtn.className = 'btn-remove';
        removeBtn.innerHTML = '&times;';
        removeBtn.setAttribute('type', 'button');
        removeBtn.onclick = function() {
            if (itemType === 'subject') {
                removeItem('subject', index);
            } else if (itemType === 'goal') {
                removeItem('goal', index);
            } else if (itemType.startsWith('month')) {
                removeItem('roadmap', index, month);
            }
        };
        
        actions.appendChild(removeBtn);
        li.appendChild(span);
        li.appendChild(actions);
        list.appendChild(li);
    });
}

// 모든 hidden 필드 업데이트
function updateAllHiddenFields() {
    // 교과목 JSON
    const subjectsField = document.getElementById('subjectsJson');
    if (subjectsField) {
        subjectsField.value = JSON.stringify(subjects);
    }
    
    // 교육 목표 JSON
    const goalsField = document.getElementById('goalsJson');
    if (goalsField) {
        goalsField.value = JSON.stringify(goals);
    }
    
    // 로드맵 JSON
    for (let i = 1; i <= 6; i++) {
        const monthField = document.getElementById(`month${i}Json`);
        if (monthField) {
            monthField.value = JSON.stringify(roadmap[`month${i}`]);
        }
    }
}

// 폼 제출 이벤트 설정
function setupFormSubmission() {
    const form = document.getElementById('courseEditForm');
    if (!form) return;
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // 모든 데이터를 hidden 필드에 저장
        updateAllHiddenFields();
        
        // 폼 데이터 수집
        const formData = {
            id: courseDetail.id,
            courseName: document.getElementById('courseName')?.value || '',
            courseTagline: document.getElementById('courseTagline')?.value || '',
            courseDescription: document.getElementById('courseDescription')?.value || '',
            startDate: document.getElementById('startDate')?.value || '',
            duration: document.getElementById('duration')?.value || '',
            subjects: subjects,
            educationalGoals: goals,
            roadmapMonth1: roadmap.month1,
            roadmapMonth2: roadmap.month2,
            roadmapMonth3: roadmap.month3,
            roadmapMonth4: roadmap.month4,
            roadmapMonth5: roadmap.month5,
            roadmapMonth6: roadmap.month6
        };
        
        const token = document.querySelector("meta[name='_csrf']")?.getAttribute("content") || '';
        const header = document.querySelector("meta[name='_csrf_header']")?.getAttribute("content") || '';
        
        fetch(`/admin/courseModifyDetail/${courseDetail.id}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('코스 정보가 성공적으로 저장되었습니다!');
                window.location.href = '/admin/courseModify';
            } else {
                alert('저장 중 오류가 발생했습니다: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('저장 중 오류가 발생했습니다.');
        });
    });
}

// 취소 버튼 이벤트 설정
function setupCancelButton() {
    const cancelButton = document.querySelector('.btn-secondary');
    if (cancelButton) {
        cancelButton.addEventListener('click', function() {
            if(confirm('변경 사항이 저장되지 않습니다. 정말 취소하시겠습니까?')) {
                window.history.back();
            }
        });
    }
}