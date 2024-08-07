document.addEventListener('DOMContentLoaded', function () {
    const courseSelect = document.getElementById('getCourse');
    const branchSelect = document.getElementById('getBranch');
    const semesterSelect = document.getElementById('getSemester');

    const branchesByCourse = {
        'Bachelor of Technology': ['Computer Science Engineering', 'Electrical Engineering', 'Electronics and Communication Engineering','Mechanical Engineering','Civil Engineering'],
        'Bachelor of Laws or Bachelor of Legislative Laws': ['Nill'],
        'Bachelor of Business Administration': ['Nill'],
        'Bachelor of Arts':['Economic(Hons.)','English(Hons.)','Political Science(Hons.)','Psychology(Hons.)'],
        'Bachelor of Science':['Mathematics(Hons.)','Physics(Hons.)','Food Technology(Hons.)','Agricultural Science(Hons.)','Hospitality and Hotel Administration','Hotel Management and Catering Technology']
    };

    const semestersByCourse = {
        'Bachelor of Laws or Bachelor of Legislative Laws': [1, 2, 3, 4, 5, 6, 7, 8,9,10],
        'Bachelor of Business Administration': [1, 2, 3, 4, 5, 6],
        'Bachelor of Arts': [1, 2, 3, 4, 5, 6],
        'Bachelor of Science': [1, 2, 3, 4, 5, 6],
        'Bachelor of Technology': [1, 2, 3, 4, 5, 6, 7, 8]
        // Add more courses and semesters as needed
    };

    courseSelect.addEventListener('change', function () {
        const selectedCourse = courseSelect.value;

        // Update branches
        branchSelect.innerHTML = '<option value="" disabled selected>Select your branch</option>';
        if (branchesByCourse[selectedCourse]) {
            branchesByCourse[selectedCourse].forEach(function (branch) {
                const option = document.createElement('option');
                option.value = branch;
                option.textContent = branch;
                branchSelect.appendChild(option);
            });
        }

        // Update semesters
        semesterSelect.innerHTML = '<option value="" disabled selected>Select your semester</option>';
        if (semestersByCourse[selectedCourse]) {
            semestersByCourse[selectedCourse].forEach(function (semester) {
                const option = document.createElement('option');
                option.value = semester;
                option.textContent = `Semester ${semester}`;
                semesterSelect.appendChild(option);
            });
        }
    });
});
function goBack() {
    window.history.back();
}