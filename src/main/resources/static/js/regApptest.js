
const applicationForm = document.getElementById('applicationForm');
if (applicationForm) {
    applicationForm.addEventListener('submit', function(event) {
        
        event.preventDefault();

       
        const formData = new FormData(event.target); 

        
        fetch('/application', { 
            method: 'POST',      
            body: formData      
           
        })
        .then(response => {
          
            if (!response.ok) {
              
                return response.json().then(errData => {
                    
                    throw new Error(errData.message || `HTTP error! status: ${response.status}`);
                }).catch(() => {
                   
                    throw new Error(`HTTP error! status: ${response.status}`);
                });
            }
            
            return response.json(); 
        })
        .then(data => {
         
            alert(data.message); 
            window.location.href = data.redirectUrl;
        })
        .catch(error => {
           
            console.error('Error submitting application:', error);
            alert('지원서 제출 중 오류가 발생했습니다: ' + error.message); 
        });
    });
} else {
    console.error("Form with ID 'applicationForm' not found.");
}